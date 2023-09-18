package com.amdocs.user.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amdocs.user.dto.ReqisterReq;
import com.amdocs.user.dto.UserSearchReq;
import com.amdocs.user.entity.Address;
import com.amdocs.user.entity.Role;
import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserKyc;
import com.amdocs.user.entity.UserProfession;
import com.amdocs.user.entity.UserWorkExpc;

public interface UserService {

	public User saveUpdate(User user, Boolean encrypt) throws Throwable;

	public User saveUpdate(ReqisterReq user) throws Throwable;

	public UserKyc saveUpdate(UserKyc userKyc) throws Throwable;

	public Address saveUpdate(Address address) throws Throwable;

	public UserProfession saveUpdate(UserProfession profession) throws Throwable;

	public UserWorkExpc saveUpdate(UserWorkExpc workExpc) throws Throwable;

	public Optional<User> byId(UUID userId) throws Throwable;

	public User byMobile(Long mobile) throws Throwable;

	public User byEmail(String email) throws Throwable;

	public User byCode(String userCode) throws Throwable;

	public List<User> user(Example<User> user) throws Throwable;

	public Page<User> user(Example<User> example, Pageable pageable) throws Throwable;

	public Page<User> user(Pageable pageable) throws Throwable;

	public Iterable<User> search(UserSearchReq userSearchReq) throws Throwable;

	public Address addrs(UUID id) throws Throwable;

	public List<Address> addrs(User user) throws Throwable;

	public List<Address> addrs(Example<Address> addrs) throws Throwable;

	public Role role(String id) throws Throwable;

	public List<Role> role() throws Throwable;

	public UserKyc kycDesc(UUID kycId);

	public UserKyc kycDesc(User user, String docNumber);

	public List<UserKyc> kycDesc(User user);

	public UserProfession profession(UUID professionId);

	public List<UserProfession> profession();

	public UserWorkExpc workExpc(UUID workExpcId);

	public List<UserWorkExpc> workExpcList(Example<UserWorkExpc> workExpc);

}
