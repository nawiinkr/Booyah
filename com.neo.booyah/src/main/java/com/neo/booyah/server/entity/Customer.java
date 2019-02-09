package com.neo.booyah.server.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Customer")
@Cacheable(false)
public class Customer{
	
	@Id //signifies the primary key
    @Column(name = "UserId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;
	
	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "Email", nullable = false)
	private String email;
	
	@Column(name = "PasswordSalt", nullable = false)
	private String passwordSalt;
	
	@Column(name = "DateOfBirth", nullable = false)
	private String dob;
	
	@Column(name = "LastUpdate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Timestamp timestamp;
	
	public Collection<Watchlist> getWatchlists() {
		return watchlists;
	}
	
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="favorite",joinColumns= @JoinColumn(name="UserId"), inverseJoinColumns= @JoinColumn(name="ShowId"))
    private Set<Show> favoriteShows;
	
	@JsonIgnore
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<Watchlist> watchlists;
	
	public Customer() {
		//default constructor
	}
	public Customer(String userId, String name, String email, String passwordSalt, String dob, Timestamp timestamp) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.passwordSalt = passwordSalt;
		this.dob = dob;
		this.timestamp = timestamp;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getUserId() {
		return userId;
	}
	
	public void addFavoriteShow(Show show) {
        if (favoriteShows==null) {
        	favoriteShows = new HashSet<Show>();
        }
        if (!favoriteShows.contains(show)) {
        	favoriteShows.add(show);
        }
    }
	
	public void removeFavoriteShow(Show show) {
		Iterator<Show> iterator = favoriteShows.iterator();
		
        while (iterator.hasNext()) {
        	Show temp = iterator.next();
        	if(temp.getShowId().equals(show.getShowId())) {
        		favoriteShows.remove(temp);
        		break;
        	}
        }
    }
	
    public Collection<Show> getFavoriteShows() {
        return favoriteShows;
    }

}