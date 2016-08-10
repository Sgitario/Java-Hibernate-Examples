package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.UserWithDetachCascade;

public interface UserWithDetachCascadeRepository extends CrudRepository<UserWithDetachCascade, Integer> {

}
