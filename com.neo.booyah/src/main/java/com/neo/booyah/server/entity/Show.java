package com.neo.booyah.server.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="Show", schema="binged")
@NamedQuery(name="Show.findAll", query="SELECT s FROM Show s ")
@Cacheable(false)
public class Show implements Serializable{
	
	@Id //signifies the primary key
    @Column(name = "ShowId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String showId;
	
	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "URL", nullable = false)
	private String url;
	
	@Column(name = "Type", nullable = false)
	private String type;
	
	@Column(name = "Language", nullable = false)
	private String language;
	
	@Column(name = "RunTime", nullable = false)
	private Long runTime;
	
	@Column(name = "PremierDate", nullable = false)
	private String premierDate;
	
	@Column(name = "ImageUrl", nullable = false)
	private String imageUrl;
	
	@Column(name = "Summary", nullable = false, length=5000)
	private String summary;
	
	
	@Column(name = "LastUpdate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Timestamp timestamp;
	
	 /*@OneToMany(mappedBy = "show", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	 private Set<Favorite> favorites;*/

	public Show() {
		// TODO Auto-generated constructor stub
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getPremierDate() {
		return premierDate;
	}


	public void setPremierDate(String premiered) {
		this.premierDate = premiered;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getShowId() {
		return showId;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public Long getRunTime() {
		return runTime;
	}


	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}
	
	

}
