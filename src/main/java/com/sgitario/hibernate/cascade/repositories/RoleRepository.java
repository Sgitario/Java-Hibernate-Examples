package com.sgitario.hibernate.cascade.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.Role;

public interface RoleRepository extends CrudRepository<Role, String> {

}
