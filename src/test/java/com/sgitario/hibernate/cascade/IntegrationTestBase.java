package com.sgitario.hibernate.cascade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.sgitario.hibernate.cascade.entities.Role;
import com.sgitario.hibernate.cascade.repositories.RoleRepository;

public abstract class IntegrationTestBase<T> {
	@Autowired
	private RoleRepository roleRepository;

	protected abstract CrudRepository<T, Integer> getUserRepository();

	public Role saveRole(Role role) {
		System.out.println("*****: Saving Role");
		return roleRepository.save(role);
	}

	public void deleteRole(Role role) {
		System.out.println("*****: Removing Role");
		roleRepository.delete(role);
	}

	public T saveUser(T entity) {
		System.out.println("*****: Saving User");
		return getUserRepository().save(entity);
	}

	public void deleteUser(T entity) {
		System.out.println("*****: Removing User");
		getUserRepository().delete(entity);
	}
}
