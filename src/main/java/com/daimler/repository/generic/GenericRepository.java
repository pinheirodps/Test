package com.daimler.repository.generic;

import java.util.List;

public interface GenericRepository<T>  {

   T lookup(final String id) ;

    List<T> findAll();
}
