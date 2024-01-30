package com.example.springsecurity3.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

// ORM - Object Relation Mapping


@Entity
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Table(name = "users")
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = true)
	private String userImage;

	@Column(nullable = false)
	private String userNickname;

	@Column(nullable = true)
	private String email;

	@Column(nullable = true)
	private String userPhone;

	@Column(nullable = true)
	private String userInfo;

	@CreationTimestamp
	private Timestamp createAt;

	@Column(nullable = false)
	private String provider;

	@Column(nullable = false)
	private String providerId;

	@OneToMany(mappedBy = "role")
	private List<UserAuthority> roles = new ArrayList<>();
}
