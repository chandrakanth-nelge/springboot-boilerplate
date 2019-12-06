package com.chan.nel.springboot.boilerplate.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "app_user")
@TypeDef(name = "Status", typeClass = MySQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(name = "uuid", nullable = false, unique = true)
	private UUID uuid;

	@Enumerated(EnumType.STRING)
	@Type(type = "Status")
	@Column(name = "status", columnDefinition = "Status", nullable = false)
	private Status status;

	@Column(name = "performed_by", nullable = false)
	private Long performedBy;

	@Generated(GenerationTime.ALWAYS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ts", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private Date timestamp;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "f_name", nullable = false)
	private String firstName;

	@Column(name = "l_name", nullable = false)
	private String lastName;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<UserRole> userRoles = new ArrayList<>();
}
