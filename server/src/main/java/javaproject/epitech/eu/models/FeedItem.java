package javaproject.epitech.eu.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="feed_item")
public class FeedItem extends BaseModel {
	@ManyToOne(cascade=CascadeType.ALL)
	public Flux flux;
	@Column(columnDefinition = "TEXT")
	public String title;
	@Column(columnDefinition = "TEXT")
	public String description;
	public String link;
	public Timestamp pub_date;
}