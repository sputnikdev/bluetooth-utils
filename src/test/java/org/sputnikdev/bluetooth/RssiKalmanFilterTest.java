package org.sputnikdev.bluetooth;

import org.junit.Test;

import static org.junit.Assert.*;

public class RssiKalmanFilterTest {

    private static final short[] READINGS = {-40, -40, -41, -40, -41, -56, -41, -40, -40};

    private RssiKalmanFilter filter = new RssiKalmanFilter();

    @Test
    public void testNextAndCurrent() throws Exception {
        assertEquals(0, (int) filter.current());
        for (short rssi : READINGS) {
            filter.next(rssi);
        }
        assertEquals(-40, (short) filter.current());
    }

    @Test
    public void testGetSetProcessNoise() throws Exception {
        filter.setProcessNoise(0.5);
        assertEquals(0.5, filter.getProcessNoise(), 0.0);
    }

    @Test
    public void testGetSetMeasurementNoise() throws Exception {
        filter.setMeasurementNoise(0.6);
        assertEquals(0.6, filter.getMeasurementNoise(), 0.0);
    }

    @Test
    public void testInitConstructor() {
        filter = new RssiKalmanFilter(0.025, 30);
        assertEquals(0.025, filter.getProcessNoise(), 0.0);
        assertEquals(30.0, filter.getMeasurementNoise(), 0.0);
    }

}