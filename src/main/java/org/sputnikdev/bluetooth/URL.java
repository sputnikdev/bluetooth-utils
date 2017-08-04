package org.sputnikdev.bluetooth;

/*-
 * #%L
 * org.sputnikdev:bluetooth-utils
 * %%
 * Copyright (C) 2017 Sputnik Dev
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Class {@code URL} represents a Uniform Resource Locator for bluetooth resources,
 * e.g. bluetooth adapters, bluetooth devices, GATT services, GATT characteristics and GATT fields.
 * For example, if you have an adapter with MAC address B8:27:EB:60:0C:43,
 * a device with MAC address 54:60:09:95:86:01 is connected to the adapter,
 * the device has a GATT service with UUID 0000180f-0000-1000-8000-00805f9b34fb,
 * the service has a characteristic with UUID 00002a19-0000-1000-8000-00805f9b34fb
 * and the characteristic has a field called "Level", then a URL for the field can be:
 * /B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level
 * Or a URL for the service can be:
 * /B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb
 * Similarly, it is easy to define a URL for other components, e.g. adapters, devices and characteristics.
 *
 * @author Vlad Kolotov
 */
public class URL implements Comparable<URL> {

    public static final Pattern URL_PATTERN =
            Pattern.compile("^((?<protocol>\\w*):/)?/(?<adapter>(\\w\\w:){5}\\w\\w)?(/(?<device>(\\w\\w:){5}\\w\\w))?(/(?<service>[0-9a-f]{4,8}(-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})?))?(/(?<characteristic>[0-9a-f]{4,8}(-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})?))?(/(?<field>\\w+))?$");

    public static final URL ROOT = new URL("/");

    private final String protocol;
    private final String adapterAddress;
    private final String deviceAddress;
    private final String serviceUUID;
    private final String characteristicUUID;
    private final String fieldName;

    /**
     * Constructor to build a URL object from its text representation.
     * E.g. / bluetooth adapter / bluetooth device / GATT service / GATT characteristic / Characteristic Field Name
     *
     * The following are some examples of a valid URL:
     *
     * URL pointing to a field:
     * /B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level
     *
     * URL pointing to a characteristic:
     * /B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb
     *
     * URL pointing to a service:
     * /B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb
     *
     * URL pointing to a bluetooth device:
     * /B8:27:EB:60:0C:43/54:60:09:95:86:01
     *
     * URL point to a bluetooth adapter:
     * /B8:27:EB:60:0C:43
     *
     * URL pointing to the "root":
     * /
     *
     * Services and characteristic UUIDs can be in a short form:
     * /B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f/00002a19/Level
     * /B8:27:EB:60:0C:43/54:60:09:95:86:01/180f/2a19/Level
     *
     *
     * @param url text representation of a URL
     */
    public URL(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
        if (matcher.find()) {
            this.protocol = matcher.group("protocol");
            this.adapterAddress = matcher.group("adapter");
            this.deviceAddress = matcher.group("device");
            this.serviceUUID = matcher.group("service");
            this.characteristicUUID = matcher.group("characteristic");
            this.fieldName = matcher.group("field");
            validate();
        } else {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
    }

    /**
     * Constructs the "root" URL object. All components of the such URL are nulls.
     * A text representation of the "root" URL is "/"
     */
    public URL() {
        this(null, null);
    }

    /**
     * Constructs a URL pointing to a bluetooth device.
     * @param adapterAddress bluetooth adapter MAC address
     * @param deviceAddress bluetooth device MAC address
     */
    public URL(String adapterAddress, String deviceAddress) {
        this(adapterAddress, deviceAddress, null, null, null);
    }

    /**
     * Constructs a URL pointing to a GATT service.
     * @param adapterAddress bluetooth adapter MAC address
     * @param deviceAddress bluetooth device MAC address
     * @param serviceUUID UUID of a GATT service
     */
    public URL(String adapterAddress, String deviceAddress, String serviceUUID) {
        this(adapterAddress, deviceAddress, serviceUUID, null, null);
    }

    /**
     * Constructs a URL pointing to a GATT characteristic.
     * @param adapterAddress bluetooth adapter MAC address
     * @param deviceAddress bluetooth device MAC address
     * @param serviceUUID UUID of a GATT service
     * @param characteristicUUID UUID of a GATT characteristic
     */
    public URL(String adapterAddress, String deviceAddress, String serviceUUID, String characteristicUUID) {
        this(adapterAddress, deviceAddress, serviceUUID, characteristicUUID, null);
    }

    /**
     * Constructs a URL pointing to a GATT field.
     * @param adapterAddress bluetooth adapter MAC address
     * @param deviceAddress bluetooth device MAC address
     * @param serviceUUID UUID of a GATT service
     * @param characteristicUUID UUID of a GATT characteristic
     * @param fieldName name of a field of the characteristic
     */
    public URL(String adapterAddress, String deviceAddress, String serviceUUID, String characteristicUUID,
            String fieldName) {
        this(null, adapterAddress, deviceAddress, serviceUUID, characteristicUUID, fieldName);
    }

    /**
     * Constructs a URL pointing to a GATT field.
     * @param protocol protocol name
     * @param adapterAddress bluetooth adapter MAC address
     * @param deviceAddress bluetooth device MAC address
     * @param serviceUUID UUID of a GATT service
     * @param characteristicUUID UUID of a GATT characteristic
     * @param fieldName name of a field of the characteristic
     */
    public URL(String protocol, String adapterAddress, String deviceAddress, String serviceUUID,
            String characteristicUUID, String fieldName) {
        this.protocol = protocol;
        this.adapterAddress = adapterAddress;
        this.deviceAddress = deviceAddress;
        this.serviceUUID = serviceUUID;
        this.characteristicUUID = characteristicUUID;
        this.fieldName = fieldName;
        validate();
    }

    /**
     * Makes a copy of a given URL with some additional components.
     * @param serviceUUID UUID of a GATT service
     * @param characteristicUUID UUID of a GATT characteristic
     * @param fieldName name of a field of the characteristic
     * @return a copy of a given URL with some additional components
     */
    public URL copyWith(String serviceUUID, String characteristicUUID, String fieldName) {
        return new URL(this.adapterAddress, this.deviceAddress, serviceUUID, characteristicUUID, fieldName);
    }

    /**
     * Makes a copy of a given URL with some additional components.
     * @param serviceUUID UUID of a GATT service
     * @param characteristicUUID UUID of a GATT characteristic
     * @return a copy of a given URL with some additional components
     */
    public URL copyWith(String serviceUUID, String characteristicUUID) {
        return new URL(this.adapterAddress, this.deviceAddress, serviceUUID, characteristicUUID);
    }

    /**
     * Makes a copy of a given URL with some additional components.
     * @param fieldName name of a field of the characteristic
     * @return a copy of a given URL with some additional components
     */
    public URL copyWith(String fieldName) {
        return new URL(this.adapterAddress, this.deviceAddress, this.serviceUUID, this.characteristicUUID, fieldName);
    }

    /**
     * Makes a copy of a given URL with some additional components.
     * @param protocol protocol name
     * @return a copy of a given URL with some additional components
     */
    public URL copyWithProtocol(String protocol) {
        return new URL(protocol, this.adapterAddress, this.deviceAddress, this.serviceUUID,
                this.characteristicUUID, fieldName);
    }

    /**
     * Returns a copy of a given URL truncated to its device component.
     * Service, characteristic and field components will be null.
     * @return a copy of a given URL truncated to the device component
     */
    public URL getDeviceURL() {
        return new URL(adapterAddress, deviceAddress);
    }

    /**
     * Returns a copy of a given URL truncated to its service component.
     * Characteristic and field components will be null.
     * @return a copy of a given URL truncated to the service component
     */
    public URL getServiceURL() {
        return new URL(adapterAddress, deviceAddress, serviceUUID);
    }

    /**
     * Returns a copy of a given URL truncated to its characteristic component.
     * The field component will be null.
     * @return a copy of a given URL truncated to the service component
     */
    public URL getCharacteristicURL() {
        return new URL(adapterAddress, deviceAddress, serviceUUID, characteristicUUID);
    }

    /**
     * Returns a copy of a given URL truncated to its adapter component.
     * Device, Service, characteristic and field components will be null.
     * @return a copy of a given URL truncated to the adapter component
     */
    public URL getAdapterURL() {
        return new URL(adapterAddress, null);
    }

    /**
     * Returns bluetooth protocol.
     * @return bluetooth protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Returns bluetooth adapter address (MAC address).
     * @return adapter address (MAC address)
     */
    public String getAdapterAddress() {
        return adapterAddress;
    }

    /**
     * Returns bluetooth device address.
     * @return bluetooth device address
     */
    public String getDeviceAddress() {
        return deviceAddress;
    }

    /**
     * Returns service UUID.
     * @return service UUID
     */
    public String getServiceUUID() {
        return serviceUUID;
    }

    /**
     * Return characteristic UUID.
     * @return characteristic UUID
     */
    public String getCharacteristicUUID() {
        return characteristicUUID;
    }

    /**
     * Returns field name.
     * @return field name
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Checks whether a given URL is the "root" URL object.
     * @return true if it is the "root" URL, false otherwise
     */
    public boolean isRoot() {
        return adapterAddress == null;
    }

    /**
     * Checks whether a given URL is pointing to a bluetooth adapter.
     * @return true if a given URL is pointing to a bluetooth adapter, false otherwise
     */
    public boolean isAdapter() {
        return adapterAddress != null && deviceAddress == null;
    }

    /**
     * Checks whether a given URL is pointing to a bluetooth device.
     * @return true if a given URL is pointing to a bluetooth device, false otherwise
     */
    public boolean isDevice() {
        return deviceAddress != null && serviceUUID == null;
    }

    /**
     * Checks whether a given URL is pointing to a GATT service.
     * @return true if a given URL is pointing to a GATT service, false otherwise
     */
    public boolean isService() {
        return serviceUUID != null && characteristicUUID == null;
    }

    /**
     * Checks whether a given URL is pointing to a GATT characteristic.
     * @return true if a given URL is pointing to a GATT characteristic, false otherwise
     */
    public boolean isCharacteristic() {
        return characteristicUUID != null && fieldName == null;
    }

    /**
     * Checks whether a given URL is pointing to a field of a characteristic.
     * @return true if a given URL is pointing to a field of a characteristic, false otherwise
     */
    public boolean isField() {
        return fieldName != null;
    }

    /**
     * Returns a new URL representing its "parent" component.
     * @return a new URL representing its "parent" component
     */
    public URL getParent() {
        if (isField()) {
            return getCharacteristicURL();
        } else if (isCharacteristic()) {
            return getServiceURL();
        } else if (isService()) {
            return getDeviceURL();
        } else if (isDevice()) {
            return getAdapterURL();
        } else {
            return null;
        }
    }

    /**
     * Checks whether a given URL is a descendant of a URL provided as an argument
     * @param url a bluetooth URL
     * @return true if a given URL is a descendant of a provided URL
     */
    public boolean isDescendant(URL url) {
        if (adapterAddress != null && (!adapterAddress.equals(url.adapterAddress))) {
            return false;
        }

        if (deviceAddress != null && url.deviceAddress == null) {
            return true;
        }
        if (deviceAddress != null && !deviceAddress.equals(url.deviceAddress)) {
            return false;
        }

        if (serviceUUID != null && url.serviceUUID == null) {
            return true;
        }
        if (serviceUUID != null ? !serviceUUID.equals(url.serviceUUID) : url.serviceUUID != null) {
            return false;
        }

        if (characteristicUUID != null && url.characteristicUUID == null) {
            return true;
        }
        if (characteristicUUID != null ?
                !characteristicUUID.equals(url.characteristicUUID) :
                url.characteristicUUID != null) {
            return false;
        }

        return fieldName != null && url.fieldName == null;
    }

    @Override
    public String toString() {
        LinkedList<String> fields = new LinkedList<>();
        fields.add(adapterAddress);
        fields.add(deviceAddress);
        fields.add(serviceUUID);
        fields.add(characteristicUUID);
        fields.add(fieldName);

        while (!fields.isEmpty() && fields.getLast() == null) {
            fields.removeLast();
        }

        return (protocol != null ? protocol + ":/" : "") + "/" + String.join("/", fields);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        URL url = (URL) o;

        if (protocol != null ? !protocol.equals(url.protocol) : url.protocol != null) {
            return false;
        }
        if (adapterAddress != null ? !adapterAddress.equals(url.adapterAddress) : url.adapterAddress != null) {
            return false;
        }
        if (deviceAddress != null ? !deviceAddress.equals(url.deviceAddress) : url.deviceAddress != null) {
            return false;
        }
        if (serviceUUID != null ? !serviceUUID.equals(url.serviceUUID) : url.serviceUUID != null) {
            return false;
        }
        if (characteristicUUID != null ?
                !characteristicUUID.equals(url.characteristicUUID) :
                url.characteristicUUID != null) {
            return false;
        }
        return fieldName != null ? fieldName.equals(url.fieldName) : url.fieldName == null;

    }

    @Override
    public int hashCode() {
        int result = protocol != null ? protocol.hashCode() : 0;
        result = 31 * result + (adapterAddress != null ? adapterAddress.hashCode() : 0);
        result = 31 * result + (deviceAddress != null ? deviceAddress.hashCode() : 0);
        result = 31 * result + (serviceUUID != null ? serviceUUID.hashCode() : 0);
        result = 31 * result + (characteristicUUID != null ? characteristicUUID.hashCode() : 0);
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(URL o) {
        int result = compareFields(adapterAddress, o.adapterAddress);
        if (result != 0) {
            return result;
        }
        result = compareFields(deviceAddress, o.deviceAddress);
        if (result != 0) {
            return result;
        }
        result = compareFields(serviceUUID, o.serviceUUID);
        if (result != 0) {
            return result;
        }
        result = compareFields(characteristicUUID, o.characteristicUUID);
        if (result != 0) {
            return result;
        }
        return compareFields(fieldName, o.fieldName);
    }

    private void validate() {
        if (fieldName != null && characteristicUUID == null ||
                characteristicUUID != null && serviceUUID == null ||
                serviceUUID != null && deviceAddress == null ||
                deviceAddress != null && adapterAddress == null) {
            throw new IllegalArgumentException("Invalid url: " + toString());
        }
    }

    private int compareFields(String field1, String field2) {
        if (field1 == null && field2 == null) {
            return 0;
        } else if (field2 == null) {
            return 1;
        } else if (field1 == null) {
            return -1;
        } else {
            return field1.compareTo(field2);
        }
    }
}
