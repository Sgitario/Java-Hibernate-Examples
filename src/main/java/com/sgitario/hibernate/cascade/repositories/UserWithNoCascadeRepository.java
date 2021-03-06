package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.UserWithNoCascade;

public interface UserWithNoCascadeRepository extends CrudRepository<UserWithNoCascade, Integer> {

}
