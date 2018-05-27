package com.daimler.service.generic;

import com.daimler.service.exception.CarNotFoundException;

import java.util.List;

public interface GenericService<T> {
    T lookup(final String id) throws CarNotFoundException;

    List<T> findAll();
}
