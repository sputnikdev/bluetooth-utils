package org.sputnikdev.bluetooth;

public class RssiKalmanFilter implements Filter<Short> {

    private static final double RSSI_PROCESS_NOISE = 0.125;
    private static final double RSSI_MEASUREMENT_NOISE = 30;

    private double processNoise;
    private double measurementNoise;
    private double currentRssi;
    private double errorCovariance;

    public RssiKalmanFilter() {
        processNoise = RSSI_PROCESS_NOISE;
        measurementNoise = RSSI_MEASUREMENT_NOISE;
    }

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

    public double getProcessNoise() {
        return processNoise;
    }

    public void setProcessNoise(double processNoise) {
        this.processNoise = processNoise;
    }

    public double getMeasurementNoise() {
        return measurementNoise;
    }

    public void setMeasurementNoise(double measurementNoise) {
        this.measurementNoise = measurementNoise;
    }
}
