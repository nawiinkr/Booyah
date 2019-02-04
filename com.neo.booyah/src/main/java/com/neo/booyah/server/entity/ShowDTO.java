package com.neo.booyah.server.entity;

import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="ShowDTO")
@Cacheable(false)
public class ShowDTO {
	
	@Id
	private String showId;
	private String name;
	private String url;
	private String type;
	private String language;
	private Long runTime;
	private String premierDate;
	private String imageUrl;
	private String summary;
	private Timestamp lastUpdate;
	private String isFavorite;
	
	public ShowDTO(String id, String name, String url, String type, String language, Long runTime, String premierDate,
			String imageUrl, String summary, Timestamp timestamp, String isFavorite) {
		super();
		this.showId = id;
		this.name = name;
		this.url = url;
		this.type = type;
		this.language = language;
		this.runTime = runTime;
		this.premierDate = premierDate;
		this.imageUrl = imageUrl;
		this.summary = summary;
		this.lastUpdate = timestamp;
		this.isFavorite = isFavorite;
	}
	
	public ShowDTO() {
		super();
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
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

	public Long getRunTime() {
		return runTime;
	}

	public void setRunTime(Long runTime) {
		this.runTime = runTime;
	}

	public String getPremierDate() {
		return premierDate;
	}

	public void setPremierDate(String premierDate) {
		this.premierDate = premierDate;
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

	public Timestamp getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Timestamp timestamp) {
		this.lastUpdate = timestamp;
	}

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

}
