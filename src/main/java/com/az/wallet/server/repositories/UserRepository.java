package com.az.wallet.server.repositories;

import com.az.wallet.server.entities.UserEntity;
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
