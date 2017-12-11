package org.sputnikdev.bluetooth;

public interface Filter<T> {

    T current();

    T next(T reading);

}
