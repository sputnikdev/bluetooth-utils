package org.sputnikdev.bluetooth;

/**
 * Represents Bluetooth address types according to BT specification with some extensions for
 * composite addresses support (e.g. wild card addressees etc).
 */
public enum AddressType {

    /**
     * Also known as MAC address. It does not change.
     * The public device address is divided into the following two fields:
     * <ul>
     *      <li>company_assigned field is contained in the 24 least significant bits</li>
     *      <li>company_id field is contained in the 24 most significant bits</li>
     * </ul>
     */
    PUBLIC,

    /**
     * Private static address. First two bits are 11. The rest MAY change when chip is rebooted, otherwise unchanging.
     */
    STATIC,

    /**
     * Private non-resolvable address. First two bits are 01, the rest is just a 46-bit random number that
     * changes every few minutes or so.
     */
    NON_RESOLVABLE,

    /**
     * Private resolvable address. First two bits are 00, the rest of the most significant half is a 22-bit
     * random number that changes every few minutes or so, the other half is a 24-bit hash of it by a 128-bit
     * Identity Resolving Key (IRK). The key is that peripheral's identity.
     */
    RESOLVABLE,

    /**
     * Composite address. Address is unknown or matches to all addresses (wildcard).
     */
    COMPOSITE

}
