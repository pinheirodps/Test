package com.daimler.repository;

import java.util.List;

public interface Repository<T>  {

   T findById(final String id);

    List<T> findAll();
}
