package org.sputnikdev.bluetooth;

import java.util.Arrays;

public final class DataConversionUtils {

    private DataConversionUtils() { }

    /**
     * Converts byte array to a text representation. Example: [01, 05, ab]
     * @param raw bytes array
     * @param radix the radix to use in the string representation
     * @return array text representation
     */
    public static String convert(byte[] raw, int radix) {
        String[] hexFormatted = new String[raw.length];
        int index = 0;
        for (byte b : raw) {
            String num = Integer.toUnsignedString(Byte.toUnsignedInt(b), radix);
            hexFormatted[index++] = ("00" + num).substring(num.length());
        }
        return Arrays.toString(hexFormatted);
    }

}
