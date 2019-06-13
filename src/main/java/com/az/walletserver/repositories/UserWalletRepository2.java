package com.az.walletserver.repositories;

import com.az.walletserver.entities.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserWalletRepository for UserWalletEntity CRUD operations.
 */

@Repository
public interface UserWalletRepository2 extends JpaRepository<UserWalletEntity,Integer> {

    /*
        =>  It will return UserWalletList with respect to userId
    */
    @Async
    CompletableFuture<List<UserWalletEntity>> findByUserEntity_UserId(Integer userId);

    /*
        =>  It will return UserWalletList with respect to userId and currencyCode
    */
    CompletableFuture<Optional<UserWalletEntity>> findByUserEntity_UserIdAndCurrencyCode(Integer userId, String currencyCode);
}
