package com.az.walletserver.services;

import com.az.walletserver.entities.UserWalletEntity;
import com.az.walletserver.repositories.UserWalletRepository;
import com.az.walletserver.repositories.UserWalletRepository2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserWalletService to deal with User balance
 */
@Slf4j
@Transactional
@Service
public class UserWalletService2 {

    private final UserWalletRepository2 userWalletRepository;

    public UserWalletService2(UserWalletRepository2 userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    /*
        =>  It will return UserWalletList with respect to userId
    */
    @Transactional(readOnly = true)
    public List<UserWalletEntity> getUserWalletListByUserId(Integer userId){
        List<UserWalletEntity> userWalletEntityList = Collections.emptyList();
        try {
            CompletableFuture<List<UserWalletEntity>> result = CompletableFuture.completedFuture(userWalletRepository.findByUserEntity_UserId(userId)).get();
            userWalletEntityList = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return userWalletEntityList;
    }

    /*
        =>  It will return UserWallet with respect to userId and currencyCode
    */
    @Transactional(readOnly = true)
    public Optional<UserWalletEntity> getUserWalletListByUserIdAndCurrencyCode(Integer userId, String currencyCode){
        log.info("Get UserWalletEntity from DB with parameter userId, currencyCode {} {}",userId,currencyCode);

        Optional<UserWalletEntity> actuallResult = null;
        try {
            CompletableFuture<Optional<UserWalletEntity>> result = CompletableFuture.completedFuture(userWalletRepository.findByUserEntity_UserIdAndCurrencyCode(userId,currencyCode)).get();
            actuallResult = result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return actuallResult;
    }

    /*
        =>  It will save UserWalletEntity or update if already exist
    */
    public UserWalletEntity saveUserWalletEntity(UserWalletEntity userWalletEntity) {
        log.info("Save or update UserWalletEntity in DB {}",userWalletEntity);
        return userWalletRepository.save(userWalletEntity);
    }
}
