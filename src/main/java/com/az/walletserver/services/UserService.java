package com.az.walletserver.services;

import com.az.walletserver.entities.UserEntity;
import com.az.walletserver.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserService class to deal all the operation to related to userEntity
 * =>   For our application we need some user record into DB.
 *
 */

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        =>  it will return Optional<UserEntity> with respect to userId.
    */
    public Optional<UserEntity> findByUserId(Integer userId){
        return userRepository.findById(userId);
    }

}
