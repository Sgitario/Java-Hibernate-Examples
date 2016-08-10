package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.UserWithRefreshCascade;

public interface UserWithRefreshCascadeRepository extends CrudRepository<UserWithRefreshCascade, Integer> {

}
