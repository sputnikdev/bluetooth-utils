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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class URLTest {

    @Test
    public void testConstructors() {
        URL url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
        assertEquals(url, new URL("tinyb", "54:60:09:95:86:01", "11:22:33:44:55:66",
                "0000180f-0000-1000-8000-00805f9b34fb", "00002a19-0000-1000-8000-00805f9b34fb", "Level"));

        assertEquals("tinyb", new URL("tinyb://").getProtocol());

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66");
        assertEquals(url, new URL("54:60:09:95:86:01", "11:22:33:44:55:66"));

        url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66");
        assertEquals(url, new URL("tinyb", "54:60:09:95:86:01", "11:22:33:44:55:66"));

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb");
        assertEquals(url, new URL("54:60:09:95:86:01", "11:22:33:44:55:66", "0000180f-0000-1000-8000-00805f9b34fb",
                "00002a19-0000-1000-8000-00805f9b34fb"));

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
        assertEquals(url, new URL("54:60:09:95:86:01", "11:22:33:44:55:66", "0000180f-0000-1000-8000-00805f9b34fb",
                "00002a19-0000-1000-8000-00805f9b34fb", "Level"));
    }

    @Test
    public void testCopyWith() {
        URL url = new URL();
        assertEquals(new URL("/"), url);
        url = url.copyWithProtocol("tinyb");
        assertEquals(new URL("tinyb://"), url);
        url = url.copyWithAdapter("54:60:09:95:86:01");
        assertEquals(new URL("tinyb://54:60:09:95:86:01"), url);
        url = url.copyWithDevice("11:22:33:44:55:66");
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66"), url);
        url = url.copyWith("0000180f-0000-1000-8000-00805f9b34fb", "00002a19-0000-1000-8000-00805f9b34fb");
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb"), url);
        url = url.copyWith("0000180f-0000-1000-8000-00805f9b34fb", "00002a19-0000-1000-8000-00805f9b34fb", "Level");
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level"), url);
        url = url.copyWithField("Power");
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Power"), url);

    }

    @Test
    public void testGetURL() {
        URL url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
        assertEquals(new URL("tinyb://"), url.getProtocolURL());
        assertEquals(new URL("tinyb://54:60:09:95:86:01"), url.getAdapterURL());
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66"), url.getDeviceURL());
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb"), url.getServiceURL());
        assertEquals(new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb"),
                url.getCharacteristicURL());
    }

    @Test
    public void testProtocol() {
        URL url = new URL("tinyb://");
        assertEquals(new URL("tinyb://"), url);

        assertNull(url.getAdapterAddress());
        assertNull(url.getDeviceAddress());
        assertNull(url.getServiceUUID());
        assertNull(url.getCharacteristicUUID());
        assertNull(url.getFieldName());
    }

    @Test
    public void testRoot() {
        URL url = new URL("/");
        assertEquals(new URL(), url);
        assertTrue(url.isRoot());

        assertNull(url.getProtocol());
        assertNull(url.getAdapterAddress());
        assertNull(url.getDeviceAddress());
        assertNull(url.getServiceUUID());
        assertNull(url.getCharacteristicUUID());
        assertNull(url.getFieldName());
    }

    @Test
    public void testFullURL() {
        URL url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");

        assertEquals("tinyb", url.getProtocol());
        assertEquals("54:60:09:95:86:01", url.getAdapterAddress());
        assertEquals("11:22:33:44:55:66", url.getDeviceAddress());
        assertEquals("0000180f-0000-1000-8000-00805f9b34fb", url.getServiceUUID());
        assertEquals("00002a19-0000-1000-8000-00805f9b34fb", url.getCharacteristicUUID());
        assertEquals("Level", url.getFieldName());
    }

    @Test
    public void testShortUUIDs() {
        URL url = new URL("dbus://54:60:09:95:86:01/11:22:33:44:55:66/0000180f/00002a19/Level");
        assertEquals("dbus", url.getProtocol());
        assertEquals("54:60:09:95:86:01", url.getAdapterAddress());
        assertEquals("11:22:33:44:55:66", url.getDeviceAddress());
        assertEquals("0000180f", url.getServiceUUID());
        assertEquals("00002a19", url.getCharacteristicUUID());
        assertEquals("Level", url.getFieldName());

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/180f/2a19/Level");
        assertEquals("54:60:09:95:86:01", url.getAdapterAddress());
        assertEquals("11:22:33:44:55:66", url.getDeviceAddress());
        assertEquals("180f", url.getServiceUUID());
        assertEquals("2a19", url.getCharacteristicUUID());
        assertEquals("Level", url.getFieldName());
    }

    @Test
    public void testIsMethods() {
        URL url = new URL();
        assertTrue(url.isRoot());
        assertFalse(url.isProtocol());
        url = new URL("/");
        assertTrue(url.isRoot());
        assertFalse(url.isProtocol());
        url = new URL("tinyb://");
        assertTrue(url.isRoot());
        assertTrue(url.isProtocol());

        assertFalse(url.isAdapter());
        assertFalse(url.isDevice());
        assertFalse(url.isService());
        assertFalse(url.isCharacteristic());
        assertFalse(url.isField());

        url = new URL("tinyb://54:60:09:95:86:01");
        assertFalse(url.isRoot());
        assertFalse(url.isProtocol());
        assertTrue(url.isAdapter());
        assertFalse(url.isDevice());
        assertFalse(url.isService());
        assertFalse(url.isCharacteristic());
        assertFalse(url.isField());

        url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66");
        assertFalse(url.isRoot());
        assertFalse(url.isProtocol());
        assertFalse(url.isAdapter());
        assertTrue(url.isDevice());
        assertFalse(url.isService());
        assertFalse(url.isCharacteristic());
        assertFalse(url.isField());

        url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/180f");
        assertFalse(url.isRoot());
        assertFalse(url.isProtocol());
        assertFalse(url.isAdapter());
        assertFalse(url.isDevice());
        assertTrue(url.isService());
        assertFalse(url.isCharacteristic());
        assertFalse(url.isField());

        url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/180f/2a19");
        assertFalse(url.isRoot());
        assertFalse(url.isProtocol());
        assertFalse(url.isAdapter());
        assertFalse(url.isDevice());
        assertFalse(url.isService());
        assertTrue(url.isCharacteristic());
        assertFalse(url.isField());

        url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/180f/2a19/Level");
        assertFalse(url.isRoot());
        assertFalse(url.isProtocol());
        assertFalse(url.isAdapter());
        assertFalse(url.isDevice());
        assertFalse(url.isService());
        assertFalse(url.isCharacteristic());
        assertTrue(url.isField());
    }

    @Test
    public void testGetParent() {
        URL url = new URL("dbus://54:60:09:95:86:01/11:22:33:44:55:66/0000180f/00002a19/Level");
        assertTrue(url.isField());
        url = url.getParent();
        assertEquals(new URL("dbus://54:60:09:95:86:01/11:22:33:44:55:66/0000180f/00002a19"), url);
        url = url.getParent();
        assertEquals(new URL("dbus://54:60:09:95:86:01/11:22:33:44:55:66/0000180f"), url);
        url = url.getParent();
        assertEquals(new URL("dbus://54:60:09:95:86:01/11:22:33:44:55:66"), url);
        url = url.getParent();
        assertEquals(new URL("dbus://54:60:09:95:86:01"), url);
        url = url.getParent();
        assertEquals(new URL("dbus://"), url);
        url = url.getParent();
        assertNull(url);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFieldName() {
        new URL("/Level");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testServiceAndCharacteristicUUID() {
        new URL("/00002a19-0000-1000-8000-00805f9b34fb");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCharacteristicUUIDShort() {
        new URL("/180f/2a1/Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCharacteristicUUIDIncomplete() {
        new URL("/180f/00002a19-0000-10/Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCharacteristicUUIDLarge() {
        new URL("/180f/00002a190/Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidServiceUUIDShort() {
        new URL("/180/2a19/Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidServiceUUIDIncomplete() {
        new URL("/0000180f-3345/2a19/Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidServiceUUIDLarge() {
        new URL("/0000180f0/2a19/Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingAdapter() {
        new URL("tinyb", null, "11:22:33:44:55:66");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingDevice() {
        new URL("tinyb", "54:60:09:95:86:01", null, "0000180f0", "2a19", "Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingService() {
        new URL("tinyb", "54:60:09:95:86:01", "11:22:33:44:55:66", null, "2a19", "Level");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingCharacteristic() {
        new URL("tinyb", "54:60:09:95:86:01", "11:22:33:44:55:66", "0000180f0", null, "Level");
    }

    @Test
    public void testDeviceAddress() {
        URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66");
        assertEquals("54:60:09:95:86:01", url.getAdapterAddress());
        assertEquals("11:22:33:44:55:66", url.getDeviceAddress());
        assertNull(url.getServiceUUID());
        assertNull(url.getCharacteristicUUID());
        assertNull(url.getFieldName());

        url = new URL("bluegiga://11:22:33:44:55:66");
        assertEquals("bluegiga", url.getProtocol());
        assertNull(url.getDeviceAddress());
        assertEquals("11:22:33:44:55:66", url.getAdapterAddress());
        assertNull(url.getServiceUUID());
        assertNull(url.getCharacteristicUUID());
        assertNull(url.getFieldName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeviceAddressIncomplete() {
        new URL("/11:22:33:44:5");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDeviceAddressLarge() {
        new URL("/11:22:33:44:55:66:77");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAdapterAddressIncomplete() {
        new URL("/54:60:09:95:/11:22:33:44:55:66");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAdapterAddressLarge() {
        new URL("/54:60:09:95:86:01:02/11:22:33:44:55:66");
    }

    @Test
    public void testToString() {

        URL url = new URL("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
        assertEquals("tinyb://54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level",
                url.toString());

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
        assertEquals("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level",
                url.toString());

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb");
        assertEquals("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb",
                url.toString());

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb");
        assertEquals("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb",
                url.toString());

        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66");
        assertEquals("/54:60:09:95:86:01/11:22:33:44:55:66",
                url.toString());

        url = new URL("/54:60:09:95:86:01");
        assertEquals("/54:60:09:95:86:01",
                url.toString());

        url = new URL();
        assertEquals("/", url.toString());
    }

    @Test
    public void isDescendant() {
        URL field = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
        URL characteristic = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb");
        URL service = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb");
        URL device = new URL("/54:60:09:95:86:01/11:22:33:44:55:66");
        URL adapter = new URL("/54:60:09:95:86:01");
        URL protocol = new URL("tibyb://");

        assertFalse(field.isDescendant(field));
        assertTrue(field.isDescendant(characteristic));
        assertTrue(field.isDescendant(service));
        assertTrue(field.isDescendant(device));
        assertTrue(field.isDescendant(adapter));
        assertTrue(field.isDescendant(protocol));
        assertTrue(field.isDescendant(URL.ROOT));

        assertFalse(characteristic.isDescendant(field));
        assertFalse(characteristic.isDescendant(characteristic));
        assertTrue(characteristic.isDescendant(service));
        assertTrue(characteristic.isDescendant(device));
        assertTrue(characteristic.isDescendant(adapter));

        assertFalse(service.isDescendant(field));
        assertFalse(service.isDescendant(characteristic));
        assertFalse(service.isDescendant(service));
        assertTrue(service.isDescendant(device));
        assertTrue(service.isDescendant(adapter));

        assertFalse(device.isDescendant(field));
        assertFalse(device.isDescendant(characteristic));
        assertFalse(device.isDescendant(service));
        assertFalse(device.isDescendant(device));
        assertTrue(device.isDescendant(adapter));

        assertFalse(adapter.isDescendant(field));
        assertFalse(adapter.isDescendant(characteristic));
        assertFalse(adapter.isDescendant(service));
        assertFalse(adapter.isDescendant(device));
        assertFalse(adapter.isDescendant(adapter));

        URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fc");
        assertFalse(field.isDescendant(url));
        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fc/00002a19-0000-1000-8000-00805f9b34fb");
        assertFalse(field.isDescendant(url));
        url = new URL("/54:60:09:95:86:01/11:22:33:44:55:67/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb");
        assertFalse(field.isDescendant(url));
        url = new URL("/54:60:09:95:86:02/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb");
        assertFalse(field.isDescendant(url));

        url = new URL("dbus://54:60:09:95:86:02/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb");
        assertTrue(url.isDescendant(new URL("dbus://54:60:09:95:86:02")));
        assertFalse(url.isDescendant(new URL("tinyb://54:60:09:95:86:02")));

        assertTrue(url.isDescendant(new URL("/54:60:09:95:86:02")));
        assertTrue(url.isDescendant(URL.ROOT));
    }

    @Test
    public void testEqualsAndHashCode() {
        String raw = "tinyb://00:1A:7D:DA:71:04/CF:FC:9E:B2:0E:63/180f/2a19/Level";
        URL url = new URL(raw);

        assertTrue(url.equals(new URL(raw)));
        assertEquals(url.hashCode(), new URL(raw).hashCode());

        assertTrue(url.copyWithProtocol(null).equals(new URL(raw).copyWithProtocol(null)));
        assertEquals(url.copyWithProtocol(null).hashCode(), new URL(raw).copyWithProtocol(null).hashCode());

        assertFalse(url.equals(new URL(raw).copyWithProtocol(null)));
        assertFalse(url.hashCode() == new URL(raw).copyWithProtocol(null).hashCode());

        assertFalse(url.equals(new URL(raw).copyWithProtocol("dbus")));
        assertFalse(url.hashCode() == new URL(raw).copyWithProtocol("dbus").hashCode());

        assertFalse(url.equals(new URL(raw).copyWithAdapter("00:1A:7D:DA:71:05")));
        assertFalse(url.hashCode() == new URL(raw).copyWithAdapter("00:1A:7D:DA:71:05").hashCode());

        assertFalse(url.equals(new URL(raw).copyWithDevice("CF:FC:9E:B2:0E:64")));
        assertFalse(url.hashCode() == new URL(raw).copyWithDevice("CF:FC:9E:B2:0E:64").hashCode());

        assertFalse(url.equals(new URL(raw).copyWith("180a", "2a19")));
        assertFalse(url.hashCode() == new URL(raw).copyWith("180a", "2a19").hashCode());

        assertFalse(url.equals(new URL(raw).copyWith("180f", "2a18")));
        assertFalse(url.hashCode() == new URL(raw).copyWith("180f", "2a18").hashCode());

        assertFalse(url.equals(new URL(raw).copyWithField("Power")));
        assertFalse(url.hashCode() == new URL(raw).copyWithField("Power").hashCode());

        assertFalse(url.equals(new Object()));
        assertFalse(url.equals(null));

        assertTrue(url.equals(url));
        assertTrue(url.hashCode() == url.hashCode());

    }

    @Test
    public void testCompareToHappyCase() {
        URL url1 = new URL("2", "2", "2", "2", "2", "2");
        URL url2 = new URL("2", "2", "2", "2", "2", "2");
        assertEquals(0, url1.compareTo(url2));
        assertEquals(0, url2.compareTo(url1));

        url1 = new URL("2", "2", "2", "2", "2");
        url2 = new URL("2", "2", "2", "2", "2");
        assertEquals(0, url1.compareTo(url2));
        assertEquals(0, url2.compareTo(url1));

        // adapter
        url2 = new URL("3", "2", "2", "2", "2");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));
        url2 = new URL("1", "2", "2", "2", "2");
        assertEquals(1, url1.compareTo(url2));
        assertEquals(-1, url2.compareTo(url1));

        // device
        url2 = new URL("2", "3", "2", "2", "2");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));
        url2 = new URL("2", "1", "2", "2", "2");
        assertEquals(1, url1.compareTo(url2));
        assertEquals(-1, url2.compareTo(url1));

        // service
        url2 = new URL("2", "2", "3", "2", "2");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));
        url2 = new URL("2", "2", "1", "2", "2");
        assertEquals(1, url1.compareTo(url2));
        assertEquals(-1, url2.compareTo(url1));

        // characteristic
        url2 = new URL("2", "2", "2", "3", "2");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));
        url2 = new URL("2", "2", "2", "1", "2");
        assertEquals(1, url1.compareTo(url2));
        assertEquals(-1, url2.compareTo(url1));

        // field
        url2 = new URL("2", "2", "2", "2", "3");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));
        url2 = new URL("2", "2", "2", "2", "1");
        assertEquals(1, url1.compareTo(url2));
        assertEquals(-1, url2.compareTo(url1));
    }

    @Test
    public void testCompareToNulls() {
        URL url1 = URL.ROOT;
        URL url2 = URL.ROOT;
        assertEquals(0, url1.compareTo(url2));

        // adapter
        url2 = new URL("1", null, null, null, null);
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));

        // device
        url2 = new URL("1", "1", null, null, null);
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));

        // service
        url2 = new URL("1", "1", "1", null, null);
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));

        // characteristic
        url2 = new URL("1", "1", "1", "1", null);
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));

        // field
        url2 = new URL("1", "1", "1", "1", "1");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));

        // field
        url1 = new URL("1", "1", null, null, null);
        url2 = new URL("1", "1", "1", "1", "1");
        assertEquals(-1, url1.compareTo(url2));
        assertEquals(1, url2.compareTo(url1));
    }

}
