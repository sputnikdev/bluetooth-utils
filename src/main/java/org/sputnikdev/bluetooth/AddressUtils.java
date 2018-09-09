package org.sputnikdev.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Set;

/**
 * Utility class to work with Bluetooth addresses. Mainly is used to identify Bluetooth device address type.
 * See {@link AddressType} for more info.
 */
public class AddressUtils {

    private static volatile Set ouiRegistry;

    /**
     * Checks whether the provided address is a Organizational Unique Identifier.
     * This method uses internal registry of OUI that is built/updated every release cycle (see maven build).
     * @param address Bluetooth address
     * @return true if the provided address is a OUI, false otherwise
     */
    public static boolean isOui(String address) {
        if (ouiRegistry == null) {
            synchronized (AddressUtils.class) {
                if (ouiRegistry == null) {
                    ouiRegistry = loadOuiRegistry();
                }
            }
        }
        return ouiRegistry.contains(Integer.valueOf(address.replace(":", "").substring(0, 6), 16));
    }

    /**
     * Guesses address type of the provided Bluetooth address. There is not any easy way to identify definitively
     * address type by using Bluetooth address only, hence the following logic is used to the best make a guess.
     * @param url device URL
     * @return guessed address type
     */
    public static AddressType guessDeviceAddressType(URL url) {
        if (url.getDeviceAddress() == null) {
            return AddressType.COMPOSITE;
        }
        return guessAddressType(url.getDeviceAddress());
    }

    /**
     * Guesses address type of the provided Bluetooth address. There is not any easy way to identify definitively
     * address type by using Bluetooth address only, hence the following logic is used to the best make a guess.
     * @param address device address
     * @return guessed address type
     */
    public static AddressType guessAddressType(String address) {
        // backward compatibility to support XX:XX:XX:XX:XX:XX addresses
        if (address.equals("XX:XX:XX:XX:XX:XX")) {
            return AddressType.COMPOSITE;
        }

        if (isOui(address)) {
            return AddressType.PUBLIC;
        }

        Integer msb = Integer.parseUnsignedInt(address.substring(0, 2), 16) >> 6;
        switch (msb) {
            case 0b11: return AddressType.STATIC;
            case 0b01: return AddressType.NON_RESOLVABLE;
            case 0b00: return AddressType.RESOLVABLE;
            default: return AddressType.PUBLIC;
        }
    }

    private static Set loadOuiRegistry() {
        try (InputStream fileIn = AddressUtils.class.getResourceAsStream("/oui_registry.ser")) {
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return (Set) objectIn.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
