package com.chan.nel.springboot.boilerplate.entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public class MySQLEnumType extends org.hibernate.type.EnumType {

	public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
		} else {
			st.setObject(index, value.toString(), Types.OTHER);
		}
	}
}