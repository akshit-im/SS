package com.amdocs.user.repo;

import com.amdocs.user.dto.UserSearchReq;
import com.amdocs.user.entity.User;

interface UserDao {

	public Iterable<User> search(UserSearchReq userSearchReq) throws Throwable;
}
