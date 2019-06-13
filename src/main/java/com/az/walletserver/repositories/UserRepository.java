package com.az.walletserver.repositories;

import com.az.walletserver.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserRepository for UserEntity CRUD operations
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Integer> {

}
