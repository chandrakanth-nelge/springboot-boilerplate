package com.chan.nel.springboot.boilerplate.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "claim")
@TypeDef(name = "Status", typeClass = MySQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Claim {
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

	@Column(name = "resource_name", nullable = false, unique = true)
	private String resourceName;

	@Column(name = "resource_http_method", nullable = false)
	private String resourceHttpMethod;

	@Column(name = "resource_endpoint", nullable = false)
	private String resourceEndpoint;
}