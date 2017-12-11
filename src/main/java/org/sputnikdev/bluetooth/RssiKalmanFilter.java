package org.sputnikdev.bluetooth;

/**
 * A simple implementation of the Kalman filter to smooth RSSI readings.
 */
public class RssiKalmanFilter implements Filter<Short> {

    private static final double RSSI_PROCESS_NOISE = 0.125;
    private static final double RSSI_MEASUREMENT_NOISE = 30;

    private double processNoise;
    private double measurementNoise;
    private double currentRssi;
    private double errorCovariance;

    /**
     * Creates an instance of the filter with some default settings (factors):
     * <br/>Process noise: 0.125
     * <br/>Measurement noise: 30
     */
    public RssiKalmanFilter() {
        processNoise = RSSI_PROCESS_NOISE;
        measurementNoise = RSSI_MEASUREMENT_NOISE;
    }

    /**
     * Creates an instance of the filter with some provided settings (factors).
     * @param processNoise process noise factor
     * @param measurementNoise measurement noise factor
     */
    public RssiKalmanFilter(double processNoise, double measurementNoise) {
        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;
    }

    @Override
    public Short next(Short next) {
        double interimRssi;
        double interimCovariance;
        if (currentRssi == 0) {
            interimRssi = next;
            interimCovariance = 1;
        } else {
            interimRssi = currentRssi;
            interimCovariance = errorCovariance + processNoise;
        }
        double kalmanGain = interimCovariance / (interimCovariance + measurementNoise);
        currentRssi = interimRssi + kalmanGain * (next - interimRssi);
        errorCovariance = (1 - kalmanGain) * interimCovariance;
        return (short) currentRssi;
    }

    @Override
    public Short current() {
        return (short) currentRssi;
    }

    /**
     * Returns the process noise factor.
     * @return process noise factor
     */
    public double getProcessNoise() {
        return processNoise;
    }

    /**
     * Sets the process noise factor.
     * @param processNoise process noise factor
     */
    public void setProcessNoise(double processNoise) {
        this.processNoise = processNoise;
    }

    /**
     * Returns the measurement noise factor.
     * @return measurement noise factor
     */
    public double getMeasurementNoise() {
        return measurementNoise;
    }

    /**
     * Sets the measurement noise factor.
     * @param measurementNoise measurement noise factor
     */
    public void setMeasurementNoise(double measurementNoise) {
        this.measurementNoise = measurementNoise;
    }
}
