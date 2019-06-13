package com.az.walletserver;


import com.az.wallet.server.proto.*;
import com.az.walletserver.services.grpc.WalletServerGRpcServiceImpl;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.hamcrest.Matchers;
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



@RunWith(SpringRunner.class)
@SpringBootTest
public class AzWalletServerApplicationTests {

    private Logger log = LoggerFactory.getLogger(AzWalletServerApplicationTests.class);

    @Autowired
    WalletServerGRpcServiceImpl walletServerGRpcService;


    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    private WalletServerServiceGrpc.WalletServerServiceFutureStub walletServerServiceFutureStub;

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
       =>   User Wallet balance with respect to Wallet Currency.
       =>   userId = 1
   */
    @Test
    @Order(3)
    public void testCase3(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).build();
        getUserWalletBalances(walletRequest,100,WalletCurrency.USD);
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
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(100).setWalletCurrency(WalletCurrency.EUR).build();
        this.deposit(walletRequest);
    }

    /*
       =>   Check that all balances are correct.
       =>   User Wallet balance with respect to Wallet Currency.
       =>   userId = 1 , EUR = 100, USD = 100, GBP = 0 at this stage
    */
    @Test
    @Order(6)
    public void testCase6(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).build();
        getUserWalletBalances(walletRequest,100,WalletCurrency.USD);
        getUserWalletBalances(walletRequest,100,WalletCurrency.EUR);
    }

    /*
        =>  Make a withdrawal of USD 200 for user with id 1. Must return insufficient_funds&quot;.
    */
    @Test
    @Order(7)
    public void testCase7(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(200).setWalletCurrency(WalletCurrency.USD).build();
        this.withdraw(walletRequest);
    }

    /*
       =>  Make a deposit of USD 100 to user with id 1.
    */
    @Test
    @Order(8)
    public void testCase8(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(100).setWalletCurrency(WalletCurrency.USD).build();
        this.deposit(walletRequest);
    }

    /*
       =>   Check that all balances are correct.
       =>   User Wallet balance with respect to Wallet Currency.
       =>   userId = 1 , EUR = 100, USD = 200, GBP = 0 at this stage
    */
    @Test
    @Order(9)
    public void testCase9(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).build();
        getUserWalletBalances(walletRequest,200,WalletCurrency.USD);
        getUserWalletBalances(walletRequest,100,WalletCurrency.EUR);
    }

    /*
        =>  Make a withdrawal of USD 200 for user with id 1. Must return 'ok'.
    */
    @Test
    @Order(10)
    public void testCase10(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(200).setWalletCurrency(WalletCurrency.USD).build();
        this.withdraw(walletRequest);
    }

    /*
       =>   Check that all balances are correct.
       =>   User Wallet balance with respect to Wallet Currency.
       =>   userId = 1 , EUR = 100, USD = 0, GBP=0 at this stage
    */
    @Test
    @Order(11)
    public void testCase11(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).build();
        getUserWalletBalances(walletRequest,100,WalletCurrency.EUR);
    }

    /*
        =>  Make a withdrawal of USD 200 for user with id 1. Must return insufficient_funds&quot;.
    */
    @Test
    @Order(12)
    public void testCase12(){
        WalletRequest walletRequest = WalletRequest.newBuilder().setUserId(1).setAmount(200).setWalletCurrency(WalletCurrency.USD).build();
        this.withdraw(walletRequest);
    }

    private void getUserWalletBalances(WalletRequest walletRequest,long amount, WalletCurrency walletCurrency){
        log.info("Sending request to server ");
        ListenableFuture<WalletResponse> walletResponse = walletServerServiceFutureStub.balance(walletRequest);

        Futures.addCallback(walletResponse, new FutureCallback<WalletResponse>() {
            @Override
            public void onSuccess(@NullableDecl WalletResponse result) {
                log.info("Received response from server");
                checkAllTheBalances(result,amount,walletCurrency);
            }

            @Override
            public void onFailure(Throwable t) {
                StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                log.error("message {} status {}", statusRuntimeException.getMessage(), statusRuntimeException.getStatus().getDescription());
            }
        },taskExecutor);

        try {
            walletResponse.get();
        }catch (ExecutionException e){
            log.error("ExecutionException occurs",e);

        } catch (InterruptedException e) {
            log.error("InterruptedException ",e);
        }

    }

    /*
        =>  After getting BalanceResponse from server I'm comparing balances here
    */
    private void checkAllTheBalances(WalletResponse walletResponse,long amount, WalletCurrency walletCurrency){

        long balanceEUR = getSpecificCurrencyBalance(WalletCurrency.EUR,walletResponse.getWalletBalanceList());
        long balanceUSD = getSpecificCurrencyBalance(WalletCurrency.USD,walletResponse.getWalletBalanceList());
        long balanceGBP = getSpecificCurrencyBalance(WalletCurrency.GBP,walletResponse.getWalletBalanceList());

        switch (walletCurrency){
            case EUR:
                Assert.assertThat(balanceEUR,Matchers.greaterThanOrEqualTo(amount));
                break;
            case GBP:
                Assert.assertThat(balanceGBP,Matchers.greaterThanOrEqualTo(amount));
                break;
            case USD:
                Assert.assertThat(balanceUSD,Matchers.greaterThanOrEqualTo(amount));
                break;
        }
    }

    private long getSpecificCurrencyBalance(WalletCurrency walletCurrency, List<WalletBalance> walletBalanceList){
            return walletBalanceList.stream().filter(walletBalance -> walletBalance.getWalletCurrency().equals(walletCurrency)).
                    mapToLong(WalletBalance::getBalance).sum();
    }

    /*
        =>  Actual Deposit method will be called here
        =>  It's called from all the deposit test cases.
    */
    private void deposit(WalletRequest walletRequest) {

        ListenableFuture<WalletResponse> walletResponse = null;
        log.info("Sending request to server ");
            walletResponse = walletServerServiceFutureStub.deposit(walletRequest);

            Futures.addCallback(walletResponse, new FutureCallback<WalletResponse>() {
                @Override
                public void onSuccess(@NullableDecl WalletResponse result) {
                    log.info("Wallet Response {}", result);
                    Assert.assertTrue("It should successfully deposit", result.getResponseMessage().equals(Status.OK.toString()));
                }

                @Override
                public void onFailure(Throwable t) {
                    log.error("Exception",t);
                    StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                    log.error("message {} status {}",statusRuntimeException.getMessage(),statusRuntimeException.getStatus().getDescription());
                }
            },taskExecutor);
        try {
            walletResponse.get();
        }catch (ExecutionException e){
            log.error("InterruptedException ",e);
        } catch (InterruptedException e) {
            log.error("InterruptedException ",e);
        }
    }

    /*
        =>  In this method I'm doing test for withdraw functionality
        =>  If User wallet have insufficient balance then it will Assert 'insufficient funds'
        =>  If User wallet balance have enough balance then it wil Assert 'Ok'
    */
    private void withdraw(WalletRequest walletRequest) {

        log.info("Sending request to server ");
        ListenableFuture<WalletResponse> walletResponse = walletServerServiceFutureStub.withdraw(walletRequest);

        Futures.addCallback(walletResponse, new FutureCallback<WalletResponse>() {
            @Override
            public void onSuccess(@NullableDecl WalletResponse result) {
                log.info("Wallet Response {}", result.getResponseStatus());
                Assert.assertTrue("Should be Ok",result.getResponseMessage().equalsIgnoreCase(Status.OK.toString()));
            }

            @Override
            public void onFailure(Throwable t) {
                StatusRuntimeException statusRuntimeException = (StatusRuntimeException) t;
                Assert.assertTrue("It should return Insufficient_funds",statusRuntimeException.getMessage().contains(ResponseStatus.INSUFFICIENT_FUNDS.name()));
            }
        },taskExecutor);
        try {
            walletResponse.get();
        }catch (ExecutionException e){
            Assert.assertTrue("It should return Insufficient_funds",e.getMessage().contains(ResponseStatus.INSUFFICIENT_FUNDS.name()));

        } catch (InterruptedException e) {
            log.error("ExecutionException ",e);
        }

    }
}
