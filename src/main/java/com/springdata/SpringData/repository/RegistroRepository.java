package com.springdata.SpringData.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springdata.SpringData.orm.Registro;

@Repository
public interface RegistroRepository extends CrudRepository<Registro, Integer>{	
}
