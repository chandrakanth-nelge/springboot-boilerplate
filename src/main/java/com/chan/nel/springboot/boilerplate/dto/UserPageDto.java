package com.chan.nel.springboot.boilerplate.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chandrakanth Nelge
 * @version 1.0
 * @since 1.0
 */
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "totalRecords", "users" })
@Data
@NoArgsConstructor
@ApiModel(description = "Paginated result of user list")
public class UserPageDto {

	@ApiModelProperty(notes = "The total number available users", position = 0)
	private Long totalRecords = 0l;

	@ApiModelProperty(notes = "List of user with user details", position = 6)
	private List<UserDto> users = new ArrayList<>();

}