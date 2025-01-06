package com.alten.mehdilagdimi.product.trial.business.util;


import at.favre.lib.crypto.bcrypt.BCrypt;

public final class HashUtil {

    private HashUtil(){}

    public final static BCrypt.Hasher bCryptHasher;

    static {
        bCryptHasher = BCrypt.withDefaults();
    }

    public static String toEncryptedPassw(String password){
        return bCryptHasher.hashToString(BCrypt.MIN_COST, password.toCharArray());
    }
}
