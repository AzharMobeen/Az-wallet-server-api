package com.az.walletserver.services;

import com.az.walletserver.entities.UserWalletEntity;
import com.az.walletserver.repositories.UserWalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserWalletService to deal with User balance
 */
@Slf4j
@Transactional
@Service
public class UserWalletService {

    private final UserWalletRepository userWalletRepository;

    public UserWalletService(UserWalletRepository userWalletRepository) {
        this.userWalletRepository = userWalletRepository;
    }

    /*
        =>  It will return UserWalletList with respect to userId
    */
    public List<UserWalletEntity> getUserWalletListByUserId(Integer userId){
        return userWalletRepository.findByUserEntity_UserId(userId);
    }

    /*
        =>  It will return UserWallet with respect to userId and currencyCode
    */
    public Optional<UserWalletEntity> getUserWalletListByUserIdAndCurrencyCode(Integer userId, String currencyCode){
        log.info("Get UserWalletEntity from DB with parameter userId, currencyCode {} {}",userId,currencyCode);
        return userWalletRepository.findByUserEntity_UserIdAndCurrencyCode(userId,currencyCode);
    }

    /*
        =>  It will save UserWalletEntity or update if already exist
    */
    public UserWalletEntity saveUserWalletEntity(UserWalletEntity userWalletEntity) {
        log.info("Save or update UserWalletEntity in DB {}",userWalletEntity);
        return userWalletRepository.save(userWalletEntity);
    }
}
