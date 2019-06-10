package com.az.wallet.server.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author - Azhar Mobeen
 *
 * Description:
 *  =>  We need UserWalletEntity to save user balance into DB with respect to currency.
 */

@Data
@Entity
@Table(name = "USER_WALLET")
public class UserWalletEntity {

    public UserWalletEntity(){ }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WALLET_ID")
    private Integer walletId;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @Column(name = "currency_code")
    private String currencyCode;
}
