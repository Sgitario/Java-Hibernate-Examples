package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.UserWithMergeCascade;

public interface UserWithMergeCascadeRepository extends CrudRepository<UserWithMergeCascade, Integer> {

}
