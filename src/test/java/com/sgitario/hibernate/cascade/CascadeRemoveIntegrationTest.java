package com.sgitario.hibernate.cascade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgitario.hibernate.cascade.config.AppConfig;
import com.sgitario.hibernate.cascade.entities.Role;
import com.sgitario.hibernate.cascade.entities.UserWithRemoveCascade;
import com.sgitario.hibernate.cascade.repositories.UserWithRemoveCascadeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CascadeRemoveIntegrationTest extends IntegrationTestBase<UserWithRemoveCascade> {

	@Autowired
	private UserWithRemoveCascadeRepository userRepository;

	@Override
	protected CrudRepository<UserWithRemoveCascade, Integer> getUserRepository() {
		return userRepository;
	}

	/**
	 * Cascade: None Output: 4 queries
	 */
	@Test
	public void scenarioTest() {
		UserWithRemoveCascade user = new UserWithRemoveCascade("Dave", "Matthews");
		Role role = new Role("Manager");
		role = saveRole(role);
		user.setRole(role);
		user = saveUser(user);

		user.setFirstname("New Name 1");
		role.setRoleName("Employer 1");
		user = saveUser(user);

		user.setFirstname("New Name 2");
		role.setRoleName("Employer 2");
		user = saveUser(user);

		deleteUser(user);
	}
	/*
@formatter:off
*****: Saving Role
Hibernate: call next value for hibernate_sequence
Hibernate: insert into Role (C_ROLE_NAME, id) values (?, ?)
*****: Saving User
Hibernate: call next value for hibernate_sequence
Hibernate: insert into UserWithRemoveCascade (C_FIRSTNAME, C_LASTNAME, role, id) values (?, ?, ?, ?)
*****: Saving User
Hibernate: select userwithre0_.id as id1_4_0_, userwithre0_.C_FIRSTNAME as C_FIRSTN2_4_0_, userwithre0_.C_LASTNAME as C_LASTNA3_4_0_, userwithre0_.role as role4_4_0_ from UserWithRemoveCascade userwithre0_ where userwithre0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update UserWithRemoveCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving User
Hibernate: select userwithre0_.id as id1_4_0_, userwithre0_.C_FIRSTNAME as C_FIRSTN2_4_0_, userwithre0_.C_LASTNAME as C_LASTNA3_4_0_, userwithre0_.role as role4_4_0_ from UserWithRemoveCascade userwithre0_ where userwithre0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update UserWithRemoveCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Removing User
Hibernate: select userwithre0_.id as id1_4_0_, userwithre0_.C_FIRSTNAME as C_FIRSTN2_4_0_, userwithre0_.C_LASTNAME as C_LASTNA3_4_0_, userwithre0_.role as role4_4_0_ from UserWithRemoveCascade userwithre0_ where userwithre0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: delete from UserWithRemoveCascade where id=?
Hibernate: delete from Role where id=?
@formatter:on
	 */
}
