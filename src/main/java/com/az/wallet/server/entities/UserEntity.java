package com.az.wallet.server.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 * =>   We need UserEntity to save our User detail into DB
 */

@Data
@Entity
@Table(name = "USER")
public class UserEntity {

    public UserEntity(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Integer userId;

    @Column(name="full_name")
    private String fullName;

    @OneToMany(mappedBy = "userEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserWalletEntity> userWalletEntityList;

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}