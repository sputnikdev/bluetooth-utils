package org.sputnikdev.bluetooth;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataConversionUtilsTest {
    @Test
    public void testConvertByteArray() throws Exception {
        byte[] data = {0x54, 0x3d, 0x32, 0x37, 0x2e, 0x36, 0x20, 0x48, 0x3d, 0x39, 0x32, 0xe, 0x36, 0x00};
        assertEquals("[54, 3d, 32, 37, 2e, 36, 20, 48, 3d, 39, 32, 0e, 36, 00]", DataConversionUtils.convert(data, 16));
    }

}