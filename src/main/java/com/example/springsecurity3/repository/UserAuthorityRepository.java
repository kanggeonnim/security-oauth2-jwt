package com.example.springsecurity3.repository;

import com.example.springsecurity3.dto.User;
import com.example.springsecurity3.dto.UserAuthority;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 *
 * @author cos
 * JPA는 기본 CRUD를 JpaRepository가 상속하는 CrudRepository가 가지고 있음.
 * JPA는 method names 전략을 가짐. README.md 사진 참고
 */

// JpaRepository 를 상속하면 자동 컴포넌트 스캔됨.
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long>{
	Optional<UserAuthority> findByUserId(Long userId);
}


