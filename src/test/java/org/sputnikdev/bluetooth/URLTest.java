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
    public void testRoot() {
        URL url = new URL("/");
        assertEquals(new URL(), url);

        assertNull(url.getAdapterAddress());
        assertNull(url.getDeviceAddress());
        assertNull(url.getServiceUUID());
        assertNull(url.getCharacteristicUUID());
        assertNull(url.getFieldName());
    }

    @Test
    public void testFullURL() {
        URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");

        assertEquals("54:60:09:95:86:01", url.getAdapterAddress());
        assertEquals("11:22:33:44:55:66", url.getDeviceAddress());
        assertEquals("0000180f-0000-1000-8000-00805f9b34fb", url.getServiceUUID());
        assertEquals("00002a19-0000-1000-8000-00805f9b34fb", url.getCharacteristicUUID());
        assertEquals("Level", url.getFieldName());
    }

    @Test
    public void testShortUUIDs() {
        URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f/00002a19/Level");
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

    @Test
    public void testDeviceAddress() {
        URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66");
        assertEquals("54:60:09:95:86:01", url.getAdapterAddress());
        assertEquals("11:22:33:44:55:66", url.getDeviceAddress());
        assertNull(url.getServiceUUID());
        assertNull(url.getCharacteristicUUID());
        assertNull(url.getFieldName());

        url = new URL("/11:22:33:44:55:66");
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

        URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
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

        assertFalse(field.isDescendant(field));
        assertTrue(field.isDescendant(characteristic));
        assertTrue(field.isDescendant(service));
        assertTrue(field.isDescendant(device));
        assertTrue(field.isDescendant(adapter));

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

    }

    @Test
    public void testEquals() {
        String raw = "/00:1A:7D:DA:71:04/CF:FC:9E:B2:0E:63/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb";
        assertTrue(new URL(raw).equals(new URL(raw)));
    }

    @Test
    public void testCompareToHappyCase() {
        URL url1 = new URL("2", "2", "2", "2", "2");
        URL url2 = new URL("2", "2", "2", "2", "2");
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
