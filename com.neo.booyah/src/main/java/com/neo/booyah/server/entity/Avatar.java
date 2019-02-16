package com.neo.booyah.server.entity;

import java.sql.Timestamp;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity(name="Avatar")
@Cacheable(false)
public class Avatar{
	
	@Id //signifies the primary key
    @Column(name = "avatarId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String avatarId;
	
	@Column(name = "userId", nullable = false)
	private String userId;
	
	@Column(name = "filename", nullable = false)
	private String filename;
	
	
	@Column(name = "lastUpdate", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp lastUpdate;
	
	@Lob
	 @Column(name = "avatar", nullable = false)
	private byte[] avatar;
	
	
	public Avatar() {
		//default constructor
	}


	public String getAvatarId() {
		return avatarId;
	}


	public void setAvatarId(String avatarId) {
		this.avatarId = avatarId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public Timestamp getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	public byte[] getAvatar() {
		return avatar;
	}


	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}


	public Avatar(String avatarId, String userId, String filename, Timestamp lastUpdate, byte[] avatar) {
		super();
		this.avatarId = avatarId;
		this.userId = userId;
		this.filename = filename;
		this.lastUpdate = lastUpdate;
		this.avatar = avatar;
	}
	
	
}