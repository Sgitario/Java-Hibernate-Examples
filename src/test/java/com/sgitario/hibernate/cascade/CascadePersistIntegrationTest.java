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
	 * @formatter:off
	 * PRO: One save action per parent entity for new entities (User)
	 * CONS: Still child entity needs to be updated separately.
	 * @formatter:on
	 */
	@Test
	public void scenario() {
		UserWithPersistCascade user = new UserWithPersistCascade("Dave", "Matthews");
		user.setRole(new Role("Manager"));
		user = saveUser(user); // 1 save action: 2 inserts

		user.setFirstname("New Name 1");
		user = saveUser(user); // 1 save action: 2 queries, 1 update
		user.getRole().setRoleName("Employer 1");
		saveRole(user.getRole()); // 1 save action: 1 query, 1 update
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
@formatter:on
	 */
}
