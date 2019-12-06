package com.chan.nel.springboot.boilerplate.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "app_user_history")
@TypeDef(name = "Status", typeClass = MySQLEnumType.class)
@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserHistory {

	@Getter
	@Setter
	@EqualsAndHashCode(of = "userId")
	@AllArgsConstructor
	@NoArgsConstructor
	@Embeddable
	public static class UserHistoryId implements Serializable {
		@Column(name = "id")
		protected Long userId;
	}

	@EmbeddedId
	private UserHistoryId id;

	@Column(name = "uuid", nullable = false)
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

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "f_name", nullable = false)
	private String firstName;

	@Column(name = "l_name", nullable = false)
	private String lastName;
}