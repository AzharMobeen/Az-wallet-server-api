package com.az.wallet.server;

import com.az.wallet.server.proto.*;
import com.az.wallet.server.services.grpc.WalletServerGRpcServiceImpl;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;



@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletServerApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(WalletServerApplicationTests.class);
    @Autowired
    WalletServerGRpcServiceImpl walletServerGRpcService;


    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    private WalletServerServiceGrpc.WalletServerServiceFutureStub walletServerServiceFutureStub;

    public WalletServerApplicationTests() {
    }

    @Before
    public void init(){
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
        walletServerServiceFutureStub = WalletServerServiceGrpc.newFutureStub(managedChannel);
    }

    /*
        =>  Make a withdrawal of USD 200 for user with id 1. Must return insufficient_funds&quot;.
    */
    @Test
    @Order(1)
    public void testCase1(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(200).setWalletCurrency(WalletCurrency.USD).build();
        this.withdraw(walletRequest);
    }

    /*
       =>  Make a deposit of USD 100 to user with id 1.
   */
    @Test
    @Order(2)
    public void testCase2(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(100).setWalletCurrency(WalletCurrency.USD).build();
        this.deposit(walletRequest);
    }

    /*
       =>   Check that all balances are correct.
       =>   At this order balances should be (0 EUR, 100 USD, 0 GBP)
       =>   userId = 1
   */
    @Test
    @Order(3)
    public void testCase3(){
        BalanceRequest balanceRequest = BalanceRequest.newBuilder().setUserId(1).build();
        getUserWalletBalances(balanceRequest,100,WalletCurrency.EUR);
    }

    /*
        =>  Make a withdrawal of USD 200 for user with id 1. Must return insufficient_funds&quot;.
    */
    @Test
    @Order(4)
    public void testCase4(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(200).setWalletCurrency(WalletCurrency.USD).build();
        this.withdraw(walletRequest);
    }

    /*
       =>  Make a deposit of EUR 100 to user with id 1.
    */
    @Test
    @Order(5)
    public void testCase5(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(100).setWalletCurrency(WalletCurrency.USD).build();
        this.deposit(walletRequest);
    }

    private void getUserWalletBalances(BalanceRequest balanceRequest,long amount, WalletCurrency walletCurrency){
        LOGGER.info("Sending request to server ");
        ListenableFuture<BalanceResponse> balanceResponse = walletServerServiceFutureStub.balance(balanceRequest);

        Futures.addCallback(balanceResponse, new FutureCallback<BalanceResponse>() {
            @Override
            public void onSuccess(@NullableDecl BalanceResponse result) {
                LOGGER.info("Received response from server");
                checkAllTheBalances(result,amount,walletCurrency);
            }

            @Override
            public void onFailure(Throwable t) {
                StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                LOGGER.error("message {} status {}", statusRuntimeException.getMessage(), statusRuntimeException.getStatus().getDescription());
            }
        },taskExecutor);

    }

    private void checkAllTheBalances(BalanceResponse balanceResponse,long amount, WalletCurrency walletCurrency){

        long balanceEUR = getSpecificCurrencyBalance(WalletCurrency.EUR,balanceResponse.getWalletBalanceList());
        long balanceUSD = getSpecificCurrencyBalance(WalletCurrency.USD,balanceResponse.getWalletBalanceList());
        long balanceGBP = getSpecificCurrencyBalance(WalletCurrency.GBP,balanceResponse.getWalletBalanceList());

        switch (walletCurrency){
            case EUR:
                Assert.assertTrue(balanceEUR>=amount);
                break;
            case GBP:
                Assert.assertTrue(balanceGBP>=amount);
                break;
            case USD:
                Assert.assertTrue(balanceUSD>=amount);
                break;
        }
    }

    private long getSpecificCurrencyBalance(WalletCurrency walletCurrency, List<WalletBalance> walletBalanceList){
            return walletBalanceList.stream().filter(walletBalance -> walletBalance.getWalletCurrency().equals(walletCurrency)).
                    mapToLong(WalletBalance::getBalance).sum();
    }
    /*private void withdrawInsufficientFunds(String message){
        message = message.substring(0,message.indexOf('\n'));
        assertEquals("Should return insufficient funds onError()",message, ErrorMessage.INSUFFICIENT_FUNDS.toString());
        //assertSame("Same message","A","B");
    }*/

    private void deposit(WalletRequest walletRequest) {

        ListenableFuture<WalletResponse> walletResponse = null;
        LOGGER.info("Sending request to server ");
            walletResponse = walletServerServiceFutureStub.deposit(walletRequest);

            Futures.addCallback(walletResponse, new FutureCallback<WalletResponse>() {
                @Override
                public void onSuccess(@NullableDecl WalletResponse result) {
                    LOGGER.info("Wallet Response {}", result);
                    depositTest(true);
                }

                @Override
                public void onFailure(Throwable t) {
                    LOGGER.error("Exception",t);
                    StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                    LOGGER.error("message {} status {}",statusRuntimeException.getMessage(),statusRuntimeException.getStatus().getDescription());
                }
            },taskExecutor);
        try {
            walletResponse.get();
        }catch (ExecutionException e){
            Assert.assertTrue("It should return Insufficient_funds",e.getMessage().contains(ResponseStatus.INSUFFICIENT_FUNDS.name()));

        } catch (InterruptedException e) {
            LOGGER.error("ExecutionException ",e);
        }
    }

    private void depositTest(boolean result){
        assertEquals("Should be true", true,result);
    }

    private void withdraw(WalletRequest walletRequest) {

        LOGGER.info("Sending request to server ");
        ListenableFuture<WalletResponse> walletResponse = walletServerServiceFutureStub.withdraw(walletRequest);

        Futures.addCallback(walletResponse, new FutureCallback<WalletResponse>() {
            @Override
            public void onSuccess(@NullableDecl WalletResponse result) {
                LOGGER.info("Wallet Response {}", result);
                Assert.assertTrue("Should be Ok",result.getResponseMessage().equalsIgnoreCase(Status.OK.toString()));
            }

            @Override
            public void onFailure(Throwable t) {
                LOGGER.error("Exception",t);
                StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                LOGGER.error("message {} status {}",statusRuntimeException.getMessage(),statusRuntimeException.getStatus().getDescription());
                Assert.assertTrue("It should return Insufficient_funds",statusRuntimeException.getMessage().contains(ResponseStatus.INSUFFICIENT_FUNDS.name()));
            }
        },taskExecutor);
        try {
            walletResponse.get();
        }catch (ExecutionException e){
            Assert.assertTrue("It should return Insufficient_funds",e.getMessage().contains(ResponseStatus.INSUFFICIENT_FUNDS.name()));

        } catch (InterruptedException e) {
            LOGGER.error("ExecutionException ",e);
        }

    }
}
