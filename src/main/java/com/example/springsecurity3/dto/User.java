package com.example.springsecurity3.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nimbusds.oauth2.sdk.TokenIntrospectionSuccessResponse;
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

	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<UserAuthority> authorityList = new ArrayList<>();

	@Builder
	public User(String username, String userNickname, String email, String provider, String providerId, List<UserAuthority> roles) {
		this.username = username;
		this.userNickname = userNickname;
		this.email = email;
		this.provider = provider;
		this.providerId = providerId;
	}

	public void addAuthority(UserAuthority userAuthority) {
		authorityList.add(userAuthority);
		userAuthority.setUser(this);
	}
}
