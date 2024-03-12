package com.project.dotori;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringRandomGenerator {

    private static final int SMALL_A = 97;
    private static final int SMALL_Z = 122;

    public static String generate(int length) {
        var random = new Random();
        return random.ints(SMALL_A, SMALL_Z + 1)
                    .limit(length)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
    }
}