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
import com.amdocs.user.entity.Status;
import com.amdocs.user.entity.Type;
import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserKyc;
import com.amdocs.user.entity.UserProfession;
import com.amdocs.user.entity.UserWorkExpc;

public interface UserService {

	public Role saveUpdate(Role entity) throws Throwable;

	public List<Role> roleSaveUpdate(Iterable<Role> entity) throws Throwable;

	public Status saveUpdate(Status entity) throws Throwable;

	public List<Status> statusSaveUpdate(Iterable<Status> entity) throws Throwable;

	public Type saveUpdate(Type entity) throws Throwable;

	public List<Type> typeSaveUpdate(Iterable<Type> entity) throws Throwable;

	public User saveUpdate(User entity, Boolean encrypt) throws Throwable;

	public User saveUpdate(ReqisterReq entity) throws Throwable;

	public UserKyc saveUpdate(UserKyc entity) throws Throwable;

	public Address saveUpdate(Address entity) throws Throwable;

	public UserProfession saveUpdate(UserProfession entity) throws Throwable;

	public UserWorkExpc saveUpdate(UserWorkExpc entity) throws Throwable;

	public Optional<Status> statusById(String id) throws Throwable;

	public List<Status> status() throws Throwable;

	public Optional<Type> typeById(UUID id) throws Throwable;

	public Optional<Type> typeByName(String id) throws Throwable;

	public List<Type> types() throws Throwable;
	
	public List<Type> types(UUID id) throws Throwable;

	public Optional<User> userById(UUID id) throws Throwable;

	public User byCode(String userCode) throws Throwable;

	public User byMobile(Long mobile) throws Throwable;

	public User byEmail(String email) throws Throwable;

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
