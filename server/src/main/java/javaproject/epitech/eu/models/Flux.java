package javaproject.epitech.eu.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="flux")
public class Flux extends BaseModel {
	@ManyToOne(cascade=CascadeType.PERSIST)
	public User user;
	public String name;
	public String url;
	public String tag;
	@Column(columnDefinition = "TEXT")
	public String title;
	@Column(columnDefinition = "TEXT")
	public String description;
	public String language;
	public String copyright;
	public String image;
	public Timestamp lastsync;

}
