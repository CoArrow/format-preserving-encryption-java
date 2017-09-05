package com.idealista.fpe.ff1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.idealista.fpe.component.functions.prf.DefaultPseudarandomFunction;

@RunWith(Parameterized.class)
public class FF1AlgorithmWithRadix45NoEmptyDataKey256Should {

    @Parameterized.Parameters(name = "{index}: plain text is {0}")
    public static Iterable<int[]> data() {
        return Arrays.asList(
                new int[] {18, 27, 13, 34, 44, 6, 37, 28, 10, 9, 16, 3, 2},
                new int[] {4, 4, 5, 4, 7, 0, 9, 4, 3, 2},
                new int[] {11, 33, 44},
                new int[] {37, 25, 16}
        );
    }

    @Parameterized.Parameter
    public int[] input;

    Integer radix = 45;
    byte[] key = {
            (byte) 0x2B, (byte) 0x7E, (byte) 0x15, (byte) 0x16, (byte) 0x28, (byte) 0xAE, (byte) 0xD2,
            (byte) 0xF7, (byte) 0x15, (byte) 0x88, (byte) 0x09, (byte) 0xCF, (byte) 0x4F, (byte) 0xAB,
            (byte) 0x3C, (byte) 0x2B, (byte) 0x7E, (byte) 0x15, (byte) 0x16, (byte) 0x28, (byte) 0xAE,
            (byte) 0xD2, (byte) 0xF7, (byte) 0x15, (byte) 0x88, (byte) 0x09, (byte) 0xCF, (byte) 0x4F,
            (byte) 0xAB, (byte) 0x3C, (byte) 0x3C, (byte) 0x3C
    };

    byte[] tweak = {
            (byte) 0x39, (byte) 0x38, (byte) 0x37, (byte) 0x36, (byte) 0x35, (byte) 0x34, (byte) 0x33, (byte) 0x32, (byte) 0x31, (byte) 0x30
    };


    @Test
    public void given_a_plain_text_return_the_cipher_text () throws Exception {
        int[] cipherText = FF1Algorithm.encrypt(input, radix, key, tweak, new DefaultPseudarandomFunction(key));
        assertThat(input.length, is(cipherText.length));
        assertThat(input, is(not(cipherText)));
        assertThat(input, is(FF1Algorithm.decrypt(cipherText, radix, key, tweak, new DefaultPseudarandomFunction(key))));

    }

    @Test
    public void given_a_cipher_text_return_the_plain_text () throws Exception {
        int[] plainText = FF1Algorithm.decrypt(input, radix, key, tweak, new DefaultPseudarandomFunction(key));
        assertThat(input.length, is(plainText.length));
        assertThat(input, is(not(plainText)));
        assertThat(input, is(FF1Algorithm.encrypt(plainText, radix, key, tweak, new DefaultPseudarandomFunction(key))));

    }

}