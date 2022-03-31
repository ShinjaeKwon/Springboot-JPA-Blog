package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, length = 100, unique = true)
	@NotBlank(message = "Username은 필수 입력값 입니다.")
	private String username;

	@Column(nullable = false, length = 100)
	@NotBlank(message = "Password는 필수 입력값 입니다.")
	private String password;

	@Column(nullable = false, length = 50)
	@NotBlank(message = "Email은 필수 입력 값입니다.")
	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String email;

	@Enumerated(EnumType.STRING)
	private RoleType role;

	private String oauth;

	@CreationTimestamp
	private Timestamp createDate;

}