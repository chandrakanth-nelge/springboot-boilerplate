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
@Table(name = "role")
@TypeDef(name = "Status", typeClass = PostgreSQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", columnDefinition = "SERIAL")
	private Long id;

	@Generated(GenerationTime.INSERT)
	@Column(name = "uuid", columnDefinition = "UUID DEFAULT gen_random_uuid()", nullable = false, insertable = false, updatable = false, unique = true)
	@Type(type = "org.hibernate.type.PostgresUUIDType")
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

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
	private List<RoleClaim> roleClaims = new ArrayList<>();
}