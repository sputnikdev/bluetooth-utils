package org.sputnikdev.bluetooth;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddressUtilsTest {

    @Test
    public void testDeviceAddressType() {
        // 4C:65:A8 is "IEEE Registration Authority" company
        assertAddressType(AddressType.PUBLIC, new URL("/XX:XX:XX:XX:XX:XX/4C:65:A8:D0:7A:EE"));
        assertAddressType(AddressType.NON_RESOLVABLE, new URL("/XX:XX:XX:XX:XX:XX/4C:65:00:D0:7A:EE"));

        assertAddressType(AddressType.STATIC, new URL("/XX:XX:XX:XX:XX:XX/C2:7C:8D:66:07:4B"));
        assertAddressType(AddressType.PUBLIC, new URL("/XX:XX:XX:XX:XX:XX/AC:7C:8D:66:07:4B"));

        assertAddressType(AddressType.RESOLVABLE, new URL("/XX:XX:XX:XX:XX:XX/09:7C:8D:66:07:4B"));
        assertAddressType(AddressType.COMPOSITE, new URL("/XX:XX:XX:XX:XX:XX/[name=Test]"));

        assertAddressType(AddressType.RESOLVABLE, new URL("tinyb://11:22:33:44:55:66"));
        assertAddressType(AddressType.COMPOSITE, new URL("tinyb://"));

        assertAddressType(AddressType.COMPOSITE, new URL("tinyb://XX:XX:XX:XX:XX:XX"));
    }

    private void assertAddressType(AddressType expected, URL address) {
        assertEquals(expected, AddressUtils.guessDeviceAddressType(address));
        if (address.getDeviceAddress() != null) {
            assertEquals(expected, AddressUtils.guessAddressType(address.getDeviceAddress()));
        }
    }

}
