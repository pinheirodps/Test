package com.daimler.repository;


import com.daimler.model.Car;

import java.util.List;

public interface Repository<T>  {

   Car findById(final String id);

    List<T> findAll();
}
