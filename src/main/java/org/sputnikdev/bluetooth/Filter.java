package org.sputnikdev.bluetooth;

/**
 * A generic interface for filters that are used to smooth some Bluetooth readings.
 * @param <T> the type of a reading
 */
public interface Filter<T> {

    /**
     * Returns current estimated reading (calculated on the previous step).
     * @return current estimated reading
     */
    T current();

    /**
     * Applies the filter to the given reading.
     * @param reading next reading
     * @return processed/estimated value
     */
    T next(T reading);

}
