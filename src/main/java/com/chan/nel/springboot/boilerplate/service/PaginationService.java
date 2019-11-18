package com.chan.nel.springboot.boilerplate.service;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
public interface PaginationService<T, P> {

	void setSortingParameters();

	void setDefaultSortParameter();

	P getPageDto(Integer pageNo, Integer pageSize, String sortBy, boolean isAscending);
}