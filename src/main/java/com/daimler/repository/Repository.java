package com.daimler.repository;

import com.daimler.service.exception.CarNotFoundException;

import java.util.List;

public interface Repository<T>  {

   T findById(final String id) ;

    List<T> findAll();
}
