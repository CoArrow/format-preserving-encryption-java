package com.idealista.fpe.component.functions.prf;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DefaultPseudarandomFunction implements PseudorandomFunction {

    public byte[] apply(byte[] plain, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            byte[] initializationVector = new byte[16];
            for (int i = 0; i < initializationVector.length; i++) {
                initializationVector[i] = (byte) 0x00;
            }
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(initializationVector));
            byte[] result = cipher.doFinal(plain);
            return Arrays.copyOfRange(result, result.length - initializationVector.length, result.length);
        } catch (GeneralSecurityException e) {
            throw new SecurityException(e);
        }

    }
}