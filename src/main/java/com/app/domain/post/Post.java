package com.app.domain.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "id_entry",referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_entry_post"),nullable = false)
  private Entry entry;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int views;

  @Column(columnDefinition = "INT DEFAULT 0")
  private int answers;
  
}