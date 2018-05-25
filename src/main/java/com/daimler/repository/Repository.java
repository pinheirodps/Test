package com.daimler.repository;


import java.util.List;
import java.util.Optional;

public interface Repository<T>  {

   Optional<T>  findById(final String id);

    List<T> findAll();
}
