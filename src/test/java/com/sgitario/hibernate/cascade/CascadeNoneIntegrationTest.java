package com.sgitario.hibernate.cascade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgitario.hibernate.cascade.config.AppConfig;
import com.sgitario.hibernate.cascade.entities.Role;
import com.sgitario.hibernate.cascade.entities.UserWithNoCascade;
import com.sgitario.hibernate.cascade.repositories.UserWithNoCascadeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CascadeNoneIntegrationTest extends IntegrationTestBase<UserWithNoCascade> {

	@Autowired
	private UserWithNoCascadeRepository userRepository;

	@Override
	protected CrudRepository<UserWithNoCascade, Integer> getUserRepository() {
		return userRepository;
	}

	/**
	 * Cascade: None Output: 4 queries
	 */
	@Test
	public void scenarioTest() {
		UserWithNoCascade user = new UserWithNoCascade("Dave", "Matthews");
		Role role = new Role("Manager");
		role = saveRole(role);
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
*****: Saving Role
Hibernate: call next value for hibernate_sequence
Hibernate: insert into Role (C_ROLE_NAME, id) values (?, ?)
*****: Saving User
Hibernate: call next value for hibernate_sequence
Hibernate: insert into UserWithNoCascade (C_FIRSTNAME, C_LASTNAME, role, id) values (?, ?, ?, ?)
*****: Saving User
Hibernate: select userwithno0_.id as id1_2_0_, userwithno0_.C_FIRSTNAME as C_FIRSTN2_2_0_, userwithno0_.C_LASTNAME as C_LASTNA3_2_0_, userwithno0_.role as role4_2_0_ from UserWithNoCascade userwithno0_ where userwithno0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update UserWithNoCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving Role
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
*****: Saving User
Hibernate: select userwithno0_.id as id1_2_0_, userwithno0_.C_FIRSTNAME as C_FIRSTN2_2_0_, userwithno0_.C_LASTNAME as C_LASTNA3_2_0_, userwithno0_.role as role4_2_0_ from UserWithNoCascade userwithno0_ where userwithno0_.id=?
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update UserWithNoCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving Role
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
@formatter:on
	 */

}
