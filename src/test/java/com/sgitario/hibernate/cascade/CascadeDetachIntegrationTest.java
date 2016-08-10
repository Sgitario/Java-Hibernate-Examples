package com.sgitario.hibernate.cascade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgitario.hibernate.cascade.config.AppConfig;
import com.sgitario.hibernate.cascade.entities.Role;
import com.sgitario.hibernate.cascade.entities.UserWithDetachCascade;
import com.sgitario.hibernate.cascade.repositories.UserWithDetachCascadeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CascadeDetachIntegrationTest extends IntegrationTestBase<UserWithDetachCascade> {

	@Autowired
	private UserWithDetachCascadeRepository userRepository;

	@Override
	protected CrudRepository<UserWithDetachCascade, Integer> getUserRepository() {
		return userRepository;
	}

	/**
	 * Cascade: None Output: 4 queries
	 */
	@Test
	public void scenarioTest() {
		UserWithDetachCascade user = new UserWithDetachCascade("Dave", "Matthews");
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
		deleteRole(role);
	}
	/*
@formatter:off
*****: Saving Role
*****: Saving Role
Hibernate: call next value for hibernate_sequence
Hibernate: insert into Role (C_ROLE_NAME, id) values (?, ?)
*****: Saving User
Hibernate: call next value for hibernate_sequence
Hibernate: insert into UserWithMergeCascade (C_FIRSTNAME, C_LASTNAME, role, id) values (?, ?, ?, ?)
*****: Saving User
Hibernate: select userwithme0_.id as id1_1_1_, userwithme0_.C_FIRSTNAME as C_FIRSTN2_1_1_, userwithme0_.C_LASTNAME as C_LASTNA3_1_1_, userwithme0_.role as role4_1_1_, role1_.id as id1_0_0_, role1_.C_ROLE_NAME as C_ROLE_N2_0_0_ from UserWithMergeCascade userwithme0_ left outer join Role role1_ on userwithme0_.role=role1_.id where userwithme0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
Hibernate: update UserWithMergeCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving User
Hibernate: select userwithme0_.id as id1_1_1_, userwithme0_.C_FIRSTNAME as C_FIRSTN2_1_1_, userwithme0_.C_LASTNAME as C_LASTNA3_1_1_, userwithme0_.role as role4_1_1_, role1_.id as id1_0_0_, role1_.C_ROLE_NAME as C_ROLE_N2_0_0_ from UserWithMergeCascade userwithme0_ left outer join Role role1_ on userwithme0_.role=role1_.id where userwithme0_.id=?
Hibernate: update UserWithMergeCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Removing User
Hibernate: select userwithme0_.id as id1_1_1_, userwithme0_.C_FIRSTNAME as C_FIRSTN2_1_1_, userwithme0_.C_LASTNAME as C_LASTNA3_1_1_, userwithme0_.role as role4_1_1_, role1_.id as id1_0_0_, role1_.C_ROLE_NAME as C_ROLE_N2_0_0_ from UserWithMergeCascade userwithme0_ left outer join Role role1_ on userwithme0_.role=role1_.id where userwithme0_.id=?
Hibernate: delete from UserWithMergeCascade where id=?
*****: Removing Role
Hibernate: select role0_.id as id1_0_0_, role0_.C_ROLE_NAME as C_ROLE_N2_0_0_ from Role role0_ where role0_.id=?
Hibernate: delete from Role where id=?
@formatter:on
	 */
}
