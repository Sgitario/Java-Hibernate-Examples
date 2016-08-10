package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.UserWithRemoveCascade;

public interface UserWithRemoveCascadeRepository extends CrudRepository<UserWithRemoveCascade, Integer> {

}
