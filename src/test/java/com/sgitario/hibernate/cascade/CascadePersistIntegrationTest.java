package com.sgitario.hibernate.cascade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgitario.hibernate.cascade.config.AppConfig;
import com.sgitario.hibernate.cascade.entities.Role;
import com.sgitario.hibernate.cascade.entities.UserWithPersistCascade;
import com.sgitario.hibernate.cascade.repositories.UserWithPersistCascadeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CascadePersistIntegrationTest extends IntegrationTestBase<UserWithPersistCascade> {

	@Autowired
	private UserWithPersistCascadeRepository userRepository;

	@Override
	protected CrudRepository<UserWithPersistCascade, Integer> getUserRepository() {
		return userRepository;
	}

	/**
	 * Cascade: None Output: 4 queries
	 */
	@Test
	public void scenarioTest() {
		UserWithPersistCascade user = new UserWithPersistCascade("Dave", "Matthews");
		Role role = new Role("Manager");
		user.setRole(role);
		user = saveUser(user);

		user.setFirstname("New Name 1");
		role.setRoleName("Employer 1");
		user = saveUser(user);
		role = saveRole(role);

		user.setFirstname("New Name 2");
		role.setRoleName("Employer 2");
		user = saveUser(user);
		role = saveRole(role);

		deleteUser(user);
		deleteRole(role);
	}
	/*
@formatter:off
*****: Saving User
Hibernate: call next value for hibernate_sequence
Hibernate: call next value for hibernate_sequence
Hibernate: insert into Role (C_ROLE_NAME, id) values (?, ?)
Hibernate: insert into UserWithPersistCascade (C_FIRSTNAME, C_LASTNAME, role, id) values (?, ?, ?, ?)
*****: Saving User
Hibernate: select userwithpe0_.id as id1_4_0_, userwithpe0_.C_FIRSTNAME as C_FIRSTN2_4_0_, userwithpe0_.C_LASTNAME as C_LASTNA3_4_0_, userwithpe0_.role as role4_4_0_ from UserWithPersistCascade userwithpe0_ where userwithpe0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update UserWithPersistCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving Role
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
*****: Saving User
Hibernate: select userwithpe0_.id as id1_4_0_, userwithpe0_.C_FIRSTNAME as C_FIRSTN2_4_0_, userwithpe0_.C_LASTNAME as C_LASTNA3_4_0_, userwithpe0_.role as role4_4_0_ from UserWithPersistCascade userwithpe0_ where userwithpe0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update UserWithPersistCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving Role
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
*****: Removing User
Hibernate: select userwithpe0_.id as id1_4_0_, userwithpe0_.C_FIRSTNAME as C_FIRSTN2_4_0_, userwithpe0_.C_LASTNAME as C_LASTNA3_4_0_, userwithpe0_.role as role4_4_0_ from UserWithPersistCascade userwithpe0_ where userwithpe0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: delete from UserWithPersistCascade where id=?
*****: Removing Role
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: delete from Role where id=?
@formatter:on
	 */
}
