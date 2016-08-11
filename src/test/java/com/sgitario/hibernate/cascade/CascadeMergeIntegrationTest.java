package com.sgitario.hibernate.cascade;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sgitario.hibernate.cascade.config.AppConfig;
import com.sgitario.hibernate.cascade.entities.Role;
import com.sgitario.hibernate.cascade.entities.UserWithMergeCascade;
import com.sgitario.hibernate.cascade.repositories.UserWithMergeCascadeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CascadeMergeIntegrationTest extends IntegrationTestBase<UserWithMergeCascade> {

	@Autowired
	private UserWithMergeCascadeRepository userRepository;

	@Override
	protected CrudRepository<UserWithMergeCascade, Integer> getUserRepository() {
		return userRepository;
	}

	/**
	 * @formatter:off
	 * PRO: One save action to update parent and child entities.
	 * CONS: One save action for every new entity.
	 * @formatter:on
	 */
	@Test
	public void scenario() {
		UserWithMergeCascade user = new UserWithMergeCascade("Dave", "Matthews");
		Role role = saveRole(new Role("Manager")); // 1 save action: 1 insert
		user.setRole(role);
		user = saveUser(user);// 1 save action: 1 insert

		user.setFirstname("New Name 1");
		user.getRole().setRoleName("Employer 1");
		user = saveUser(user);// 1 save action: 1 query, 2 updates

		user.setFirstname("New Name 2");
		user.getRole().setRoleName("Employer 2");
		user = saveUser(user);// 1 save action: 1 query, 2 updates
	}
	/*
@formatter:off
*****: Saving Role
Hibernate: call next value for hibernate_sequence
Hibernate: insert into Role (C_ROLE_NAME, id) values (?, ?)
*****: Saving User
Hibernate: call next value for hibernate_sequence
Hibernate: insert into UserWithMergeCascade (C_FIRSTNAME, C_LASTNAME, role, id) values (?, ?, ?, ?)
*****: Saving User
Hibernate: select userwithme0_.id as id1_2_1_, userwithme0_.C_FIRSTNAME as C_FIRSTN2_2_1_, userwithme0_.C_LASTNAME as C_LASTNA3_2_1_, userwithme0_.role as role4_2_1_, role1_.id as id1_0_0_, role1_.C_ROLE_NAME as C_ROLE_N2_0_0_ from UserWithMergeCascade userwithme0_ left outer join Role role1_ on userwithme0_.role=role1_.id where userwithme0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
Hibernate: update UserWithMergeCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
*****: Saving User
Hibernate: select userwithme0_.id as id1_2_1_, userwithme0_.C_FIRSTNAME as C_FIRSTN2_2_1_, userwithme0_.C_LASTNAME as C_LASTNA3_2_1_, userwithme0_.role as role4_2_1_, role1_.id as id1_0_0_, role1_.C_ROLE_NAME as C_ROLE_N2_0_0_ from UserWithMergeCascade userwithme0_ left outer join Role role1_ on userwithme0_.role=role1_.id where userwithme0_.id=?
Hibernate: update Role set C_ROLE_NAME=? where id=?
Hibernate: update UserWithMergeCascade set C_FIRSTNAME=?, C_LASTNAME=?, role=? where id=?
@formatter:on
	 */
}
