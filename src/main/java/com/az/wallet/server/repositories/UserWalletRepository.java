package com.az.wallet.server.repositories;

import com.az.wallet.server.entities.UserWalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserWalletRepository for UserWalletEntity CRUD operations.
 */

@Repository
public interface UserWalletRepository extends JpaRepository<UserWalletEntity,Integer> {

    /*
        =>  It will return UserWalletList with respect to userId
    */
    List<UserWalletEntity> findByUserEntity_UserId(Integer userId);

    /*
        =>  It will return UserWalletList with respect to userId and currencyCode
    */
    Optional<UserWalletEntity> findByUserEntity_UserIdAndCurrencyCode(Integer userId, String currencyCode);
}
