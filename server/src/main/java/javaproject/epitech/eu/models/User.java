package javaproject.epitech.eu.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User extends BaseModel {
	@Column(unique=true)
	public String username;
	public String password;
}
