package com.sgitario.hibernate.cascade.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserWithDetachCascade {
	private Integer id;
	private String firstname;
	private String lastname;

	public UserWithDetachCascade() {

	}

	public UserWithDetachCascade(String firstname, String lastname) {
		setFirstname(firstname);
		setLastname(lastname);
	}

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "C_FIRSTNAME")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Column(name = "C_LASTNAME")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Save-Update cascade
	 */

	private Role role;

	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "role")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
