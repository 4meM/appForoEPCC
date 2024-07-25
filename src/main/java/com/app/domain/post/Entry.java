package com.app.domain.post;

import java.time.LocalDateTime;

import com.app.domain.user.ForoUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "entry")
public class Entry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "id_user",referencedColumnName = "id",foreignKey = @ForeignKey(name = "FK_user_entry"),nullable = false)
  private ForoUser user;

  @Column(nullable = false,columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int upVotes;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int downVotes;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int comments;

  @Column(nullable = false,updatable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime createdAt;

  @Column(nullable = false,columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime lastUpdate;

  public Long getId() {
    return id;
  }

  public ForoUser getUser() {
    return user;
  }

  public String getContent() {
    return content;
  }

  public int getUpVotes() {
    return upVotes;
  }

  public int getDownVotes() {
    return downVotes;
  }

  public int getComments() {
    return comments;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getLastUpdate() {
    return lastUpdate;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setUpVotes(int upVotes) {
    this.upVotes = upVotes;
  }

  public void setDownVotes(int downVotes) {
    this.downVotes = downVotes;
  }

  public void setComments(int comments) {
    this.comments = comments;
  }

  public void setLastUpdate(LocalDateTime lastUpdate) {
    this.lastUpdate = lastUpdate;
  }

  public void setUser(ForoUser user) {
    this.user = user;
  }
  
}
