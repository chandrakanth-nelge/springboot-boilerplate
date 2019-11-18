package com.chan.nel.springboot.boilerplate.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chan.nel.springboot.boilerplate.config.ApplicationProperties;
import com.chan.nel.springboot.boilerplate.dao.UserRepository;
import com.chan.nel.springboot.boilerplate.dto.UserDto;
import com.chan.nel.springboot.boilerplate.dto.UserPageDto;
import com.chan.nel.springboot.boilerplate.entity.User;
import com.chan.nel.springboot.boilerplate.service.UserService;
import com.chan.nel.springboot.boilerplate.util.FunctionalReadWriteLock;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@Service("UserPaginationService")
public class UserPaginationServiceImpl extends AbstractPaginationService<User, UserPageDto> {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	private final AtomicLong totalRecords = new AtomicLong(0);

	private final FunctionalReadWriteLock totalRecordsGuard = new FunctionalReadWriteLock();

	@Override
	public void setSortingParameters() {
		this.sortParameters = applicationProperties.getSort().getUser().getParams();
	}

	@Override
	public void setDefaultSortParameter() {
		this.defaultSort = applicationProperties.getSort().getUser().getDefaultParam();
	}

	@Override
	@Transactional
	public UserPageDto getPageDto(final Integer pageNo, final Integer pageSize, final String sortBy,
			final boolean isAscending) {
		UserPageDto userPageDto = new UserPageDto();
		if (!isValidPageNo(pageNo, pageSize)) {
			return userPageDto;
		}
		Pageable pageable = this.getPageable(Optional.ofNullable(pageNo), Optional.ofNullable(pageSize),
				Optional.ofNullable(sortBy), isAscending);
		Page<User> pagedResult = userRepository.findAll(pageable);
		if (pagedResult.hasContent()) {
			final List<User> users = pagedResult.getContent();
			List<UserDto> userList = users.stream().map(user -> userService.prepareUserDto(user))
					.collect(Collectors.toList());
			userPageDto.setUsers(userList);
			totalRecordsGuard.write(() -> totalRecords.set(pagedResult.getTotalElements()));
			totalRecordsGuard.read(() -> userPageDto.setTotalRecords(totalRecords.get()));
		}
		return userPageDto;
	}

	private boolean isValidPageNo(Integer pageNo, Integer pageSize) {
		if (Objects.nonNull(pageNo)) {
			if (Objects.isNull(pageSize)) {
				pageSize = this.getDefaultPageSize();
			}
			AtomicLong totalPages = new AtomicLong();
			Integer finalPageSize = pageSize;
			totalRecordsGuard.read(() -> totalPages.set((totalRecords.get() / finalPageSize) + 1));
			if (pageNo > totalPages.get()) {
				totalRecordsGuard.write(() -> {
					long totalElements = userRepository.count();
					totalRecords.set(totalElements);
					totalPages.set((totalRecords.get() / finalPageSize) + 1);
				});
				return pageNo <= totalPages.get();
			}
		}
		return true;
	}
}