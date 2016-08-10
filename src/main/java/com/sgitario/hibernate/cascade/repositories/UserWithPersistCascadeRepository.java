package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.UserWithPersistCascade;

public interface UserWithPersistCascadeRepository extends CrudRepository<UserWithPersistCascade, Integer> {

}
