package com.neo.booyah.server.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Watchlist")
@Cacheable(false)
public class Watchlist{
	
	@Id //signifies the primary key
    @Column(name = "WatchlistId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String watchlistId;
	
	@Column(name = "Name", nullable = false)
	private String name;
	
	@Column(name = "LastUpdate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	public Timestamp timestamp;
	
	@Column(name = "UserId")
	public String userId;
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	@OneToMany(fetch= FetchType.LAZY)
    @JoinTable(name="WatchlistShows",joinColumns= @JoinColumn(name="WatchlistId"), inverseJoinColumns= @JoinColumn(name="ShowId"))
    private Set<Show> shows;
	
	
	@ManyToOne(cascade=CascadeType.ALL)
	 @JoinColumn(name="UserId", insertable=false, updatable=false)
	 private Customer user;

	
	public Customer getUser() {
		return user;
	}


	public void setUser(Customer user) {
		this.user = user;
	}


	public Watchlist() {
		//default constructor
	}
	
	
	public Watchlist(String watchlistId, String name, Timestamp timestamp, String userId, Set<Show> shows) {
		super();
		this.watchlistId = watchlistId;
		this.name = name;
		this.timestamp = timestamp;
		this.userId = userId;
		this.shows = shows;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Collection<Show> getShows() {
		return shows;
	}


	public void setShows(Set<Show> shows) {
		this.shows = shows;
	}


	public String getWatchlistId() {
		return watchlistId;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void addShow(Show show) {
        if (shows==null) {
        	shows = new HashSet<Show>();
        }
        if (!shows.contains(show)) {
        	shows.add(show);
        }
    }
	
	public void removeShow(Show show) {
		Iterator<Show> iterator = shows.iterator();
		
        while (iterator.hasNext()) {
        	Show temp = iterator.next();
        	if(temp.getShowId().equals(show.getShowId())) {
        		shows.remove(temp);
        		break;
        	}
        }
    }

}