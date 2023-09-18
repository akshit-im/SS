package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class UserSearchReq implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long countryId;

	private String name;

	private Long mobile;

	private String email;

	private Date fromDate;

	private Date toDate;

	private List<Integer> status;

}
