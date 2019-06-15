package com.az.walletserver.services.grpc;

import com.az.walletserver.entities.UserEntity;
import com.az.walletserver.entities.UserWalletEntity;
import com.az.wallet.server.proto.*;
import com.az.walletserver.services.UserService;
import com.az.walletserver.services.UserWalletService;
import com.az.walletserver.util.CurrencyUtils;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.*;



/**
 * @author - Azhar Mobeen
 *
 * Description:
 *  =>  We need WalletServerGRpcService to extend WalletServerServiceGrpc.WalletServerServiceImplBase
 *      so than we can use GRPC framework
 */

@Slf4j
@GRpcService
public class WalletServerGRpcServiceImpl extends WalletServerServiceGrpc.WalletServerServiceImplBase {

    private final UserWalletService userWalletService;
    private final UserService userService;

    public WalletServerGRpcServiceImpl(UserWalletService userWalletService,UserService userService){
        this.userWalletService = userWalletService;
        this.userService = userService;
    }

    /*
        =>  In this method Server receiving walletRequest for Deposit and sending response to client.
    */
    @Override
    public void deposit(WalletRequest walletRequest, StreamObserver<WalletResponse> responseObserver) {
        log.info("Server Receive Deposit request!");
        validateAndProcessRequest(true,walletRequest, responseObserver);
    }

    /*
        =>  In this method Server receiving walletRequest for Withdraw and sending response to client.
    */
    @Override
    public void withdraw(WalletRequest walletRequest, StreamObserver<WalletResponse> responseObserver) {
        log.info("Server received request for Withdraw!");
        validateAndProcessRequest(false,walletRequest, responseObserver);
    }

    /*
       =>  In this method Server receiving balanceRequest for Balance check and sending response to client.
    */
    @Override
    public void balance(WalletRequest walletRequest, StreamObserver<WalletResponse> responseObserver) {
        log.info("Server received request for Balance!");
        int userId = walletRequest.getUserId();
        if(userId>0) {
            // Now we will check and display user wallet balance for all supported currencies
            checkUserWalletBalance(userId,responseObserver);

        }else{
            // This means UserId is not valid
            log.info("User Id is not valid {}",userId);

            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(ResponseStatus.INVALID_USER_ID.name())
                    .augmentDescription("UserId is not valid: " + userId)
                    .asRuntimeException());
        }
    }

    /*
        =>  This method will be check user balance and then response to client
    */
    private void checkUserWalletBalance(int userId,StreamObserver<WalletResponse> responseObserver){
        List<UserWalletEntity> userWalletEntityList = userWalletService.getUserWalletListByUserId(userId);

        List<WalletBalance> walletBalanceList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(userWalletEntityList)) {
            long balanceEUR = getUserWalletBalanceFromList(WalletCurrency.EUR, userWalletEntityList);
            walletBalanceList.add(WalletBalance.newBuilder().setBalance(balanceEUR).setWalletCurrency(WalletCurrency.EUR).build());

            long balanceUSD = getUserWalletBalanceFromList(WalletCurrency.USD, userWalletEntityList);
            walletBalanceList.add(WalletBalance.newBuilder().setBalance(balanceUSD).setWalletCurrency(WalletCurrency.USD).build());

            long balanceGBP = getUserWalletBalanceFromList(WalletCurrency.GBP, userWalletEntityList);
            walletBalanceList.add(WalletBalance.newBuilder().setBalance(balanceGBP).setWalletCurrency(WalletCurrency.GBP).build());
            WalletResponse balanceResponse = WalletResponse.newBuilder().addAllWalletBalance(walletBalanceList).build();
            responseObserver.onNext(balanceResponse);
            responseObserver.onCompleted();
        }else{
            // This means UserId is authenticated
            log.info("User Id is not authenticated {}",userId);
            responseObserver.onError(Status.NOT_FOUND.withDescription(ResponseStatus.INVALID_USER_ID.name())
                    .augmentDescription("UserId is not authenticated : " + userId)
                    .asRuntimeException());
        }
    }

    /*
        =>  It will validate WalletRequest step by step and process
        =>  First it will process currency then userId and then amount
        =>  if all WalletRequest is valid then it will be process.
    */
    private void validateAndProcessRequest(Boolean depositFlag,WalletRequest walletRequest, StreamObserver<WalletResponse> responseObserver){
        log.info("validateAndProcessRequest method called!");
        String currencyCode = walletRequest.getWalletCurrency().name();
        if(validateCurrency(currencyCode)){
            //this means currency is valid.
            validateUserIdAndProcess(depositFlag,walletRequest,responseObserver);

        }else{
            // This means currency is not supported
            log.info("currency is valid or not supported by system!");
            responseObserver.onError(Status.UNKNOWN.withDescription(ResponseStatus.UNKNOWN_CURRENCY.name())
                    .augmentDescription("Currency allowed values are EUR, USD, GBP but requested currency is : "+currencyCode)
                    .asRuntimeException());
        }
    }

    /*
        =>  It will validate UserId
        =>  If it's not valid then error will be added to response else send to validateAmountAndProcess for process
    */
    private void validateUserIdAndProcess(Boolean depositFlag,WalletRequest walletRequest, StreamObserver<WalletResponse> responseObserver) {
        log.info("validateUserIdAndProcess method called!");
        Integer userId = walletRequest.getUserId();
        if(userId>0) {
            Optional<UserEntity> userEntity = userService.findByUserId(userId);
            if (userEntity.isPresent()) {
                validateAmountAndProcess(depositFlag,userEntity.get(),walletRequest,responseObserver);
            } else {
                // This means UserId is authenticated
                log.info("User Id is not authenticated {}",userId);
                responseObserver.onError(Status.NOT_FOUND.withDescription(ResponseStatus.INVALID_USER_ID.name())
                        .augmentDescription("UserId is not authenticated: " + userId)
                        .asRuntimeException());
            }
        }else{
            // This means UserId is not valid
            log.info("User Id is not valid {}",userId);
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(ResponseStatus.INVALID_USER_ID.name())
                    .augmentDescription("UserId is not valid: " + userId)
                    .asRuntimeException());
        }
    }

    /*
        =>  It will validate amount
        =>  If it's not valid then error will be added to response else process request
    */
    private void validateAmountAndProcess(boolean depositFlag, UserEntity userEntity,WalletRequest walletRequest, StreamObserver<WalletResponse> responseObserver) {
        log.info("validateAmountAndProcess method called!");
        long amount = walletRequest.getAmount();
        String currencyCode = walletRequest.getWalletCurrency().toString();
        if (amount>0){
            UserWalletEntity userWalletEntity = getUserWalletEntity(walletRequest.getUserId(),currencyCode);
            userWalletEntity.setCurrencyCode(currencyCode);
            userWalletEntity.setUserEntity(userEntity);
            if(userWalletEntity.getBalance()==null)
                userWalletEntity.setBalance(BigDecimal.ZERO);

            BigDecimal requestedAmount = BigDecimal.valueOf(amount);
            if(depositFlag){
                // This means it's deposit request
                processDepositRequest(requestedAmount,userWalletEntity,responseObserver);
            }else{
                // This means it's withdraw request
                processWithdrawRequest(requestedAmount,userWalletEntity,responseObserver);
            }

        }else{
            // This means amount is not valid
            responseObserver.onError(Status.FAILED_PRECONDITION.withDescription(ResponseStatus.INVALID_AMOUNT.name())
                    .augmentDescription("Deposit amount should be > 0 but requested amount : " + amount)
                    .asRuntimeException());
        }
    }


    /*
        =>  It will Process Withdraw request
    */
    private void processWithdrawRequest(BigDecimal requestedAmount, UserWalletEntity userWalletEntity,StreamObserver<WalletResponse> responseObserver) {
        log.info("processWithdrawRequest called after all the validations passed!");
        if(requestedAmount.compareTo(userWalletEntity.getBalance())<=0){

            BigDecimal updatedBalance = userWalletEntity.getBalance().subtract(requestedAmount);
            userWalletEntity.setBalance(updatedBalance);

            log.info("Withdrawing from User Wallet {}",userWalletEntity);
            /*try {*/
                userWalletEntity = userWalletService.saveUserWalletEntity(userWalletEntity);
                log.info("User Wallet successfully updated! {}",userWalletEntity);
                responseObserver.onNext(WalletResponse.newBuilder().setResponseStatus(ResponseStatus.WITHDRAW_SUCCESS).setResponseMessage(Status.OK.toString()).build());
                responseObserver.onCompleted();
            /*} catch (ExecutionException | InterruptedException e) {
                log.error("Exception occurs",e);
            }*/

        }else {
            // This means amount is not valid
            log.info("Requested amount should be <= user wallet balance requestAmount {}, userWalletBalance {} ",requestedAmount,userWalletEntity.getBalance());
            responseObserver.onError(Status.UNIMPLEMENTED.withDescription(ResponseStatus.INSUFFICIENT_FUNDS.name())
                    .augmentDescription("Insufficient funds, Requested amount should be <= user wallet balance")
                    .asRuntimeException());
        }
    }

    /*
        =>  It will Process Deposit request
    */
    private void processDepositRequest(BigDecimal amount,UserWalletEntity userWalletEntity,StreamObserver<WalletResponse> responseObserver){
        log.info("processDepositRequest called after all validations passed!");
        BigDecimal updateBalance = userWalletEntity.getBalance().add(amount);
        userWalletEntity.setBalance(updateBalance);
        log.info("Depositing into User Wallet {}",userWalletEntity);
        /*try {*/
            userWalletEntity = userWalletService.saveUserWalletEntity(userWalletEntity);
            log.info("User Wallet successfully updated! {}",userWalletEntity);
            responseObserver.onNext(WalletResponse.newBuilder().setResponseMessage(Status.OK.toString()).setResponseStatus(ResponseStatus.DEPOSIT_SUCCESS).build());
            responseObserver.onCompleted();
        /*} catch (ExecutionException | InterruptedException e) {
           log.error("Exception occurs",e);
        }*/

    }


    /*
        =>  In this method we will check requested currency is valid or not
        =>  And currency is supported by system or not.
    */
    private boolean validateCurrency(String walletCurrency){
        return CurrencyUtils.isValidateCurrencyCode(walletCurrency);
    }

    /*
        =>  It will return UserWallet with respect to userId and currencyCode
        =>  if not exist in DB then new Object will be sent.
    */
    private UserWalletEntity getUserWalletEntity(Integer userId,String currencyCode){
        log.info("getUserWalletEntity method called userId {} , currencyCode {}",userId,currencyCode);
        Optional<UserWalletEntity> userWalletEntity = userWalletService.getUserWalletListByUserIdAndCurrencyCode(userId,currencyCode);
        return userWalletEntity.orElseGet(UserWalletEntity::new);
    }

    /*
        =>  It will return UserWallet Balance from list of UserWalletEntities
    */
    private long getUserWalletBalanceFromList(WalletCurrency walletCurrency,List<UserWalletEntity> userWalletEntityList){
        return userWalletEntityList.stream().filter(
                userWalletEntity -> userWalletEntity.getCurrencyCode().equals(walletCurrency.toString()))
                .mapToLong(userWalletEntity->userWalletEntity.getBalance().longValue()).sum();
    }
}
