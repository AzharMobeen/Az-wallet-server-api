package com.az.walletserver.util;

import com.az.wallet.server.proto.WalletCurrency;
import lombok.extern.slf4j.Slf4j;

import java.util.Currency;

/**
 * @author - Azhar Mobeen
 */

/*
    CurrencyUtils also part of my important choices.
    java.util.currency provide lot of functions for  currency related
*/

@Slf4j
public final class CurrencyUtils {
    private CurrencyUtils() {
    }

    /*
     * this method will check Currency is valid by using java.util.Currency
     * if it's valid by java.util.currency then we an other method isSupportedBySystem will call.
     * */
    public static boolean isValidateCurrencyCode(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            log.info("Currency Code is valid by java.util.Currency, Currency Symbol {} {} ",currencyCode,currency.getSymbol());
            if(currency.getCurrencyCode().equals(currencyCode))
            return isSupportedBySystem(currencyCode);
        } catch (Exception e) {
            log.error("Cannot parse the Currency Code, Validation Failed ",e);
        }
        return false;
    }

    /*
     * this method will check Currency is supported by our system
     * */
    private static boolean isSupportedBySystem(String currencyCode) {
        try {

            if(WalletCurrency.EUR.toString().equals(currencyCode) ||
                    WalletCurrency.USD.toString().equals(currencyCode) ||
                    WalletCurrency.GBP.toString().equals(currencyCode))
            return true;
        } catch (Exception e) {
            log.error("Cannot compare the Currency Code ",e);
        }
        return false;
    }

    /*
     * This method will return currency symbol by using java.util.Currency
     * */
    public static String getCurrencySymbol(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol();
        } catch (Exception e) {
            log.warn("Cannot parse the Currency Code, Validation Failed: ", e);
        }
        return "";
    }
}
