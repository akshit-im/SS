package com.amdocs.user.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

import jakarta.transaction.Transactional;

@Service
@Transactional
class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passEncode;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private UserCrudRepo userCrudRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private AddressRepo addrsRepo;

	@Autowired
	private UserKycRepo usrKycRepo;

	@Autowired
	private UserProfessionRepo profRepo;

	@Autowired
	private UserWorkExpcRepo workExpcRepo;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TypeRepo typeRepo;

	@Autowired
	private TypeCrudRepo typeCrudRepo;

	@Autowired
	private StatusRepo statusRepo;

	@Override
	public Role saveUpdate(Role entity) throws Throwable {
		return roleRepo.saveAndFlush(entity);
	}

	@Override
	public List<Role> roleSaveUpdate(Iterable<Role> entity) throws Throwable {
		return roleRepo.saveAllAndFlush(entity);
	}

	@Override
	public Status saveUpdate(Status entity) throws Throwable {
		return statusRepo.saveAndFlush(entity);
	}

	@Override
	public List<Status> statusSaveUpdate(Iterable<Status> entity) throws Throwable {
		return statusRepo.saveAllAndFlush(entity);
	}

	@Override
	public Type saveUpdate(Type entity) throws Throwable {
		return typeRepo.saveAndFlush(entity);
	}

	@Override
	public List<Type> typeSaveUpdate(Iterable<Type> entity) throws Throwable {
		return typeRepo.saveAllAndFlush(entity);
	}

	@Override
	public User saveUpdate(User user, Boolean encrypt) throws Throwable {
		if (encrypt) {
			String hashedPassword = passEncode.encode(user.getPassword());
			user.setPassword(hashedPassword);
		}
		return userRepo.saveAndFlush(user);
	}

	@Override
	public User saveUpdate(ReqisterReq entity) throws Throwable {
		User userEntity = entity.user();
		userEntity.setPassword(passEncode.encode(entity.getPassword()));
		return userRepo.saveAndFlush(userEntity);
	}

	@Override
	public UserKyc saveUpdate(UserKyc entity) throws Throwable {
		return usrKycRepo.saveAndFlush(entity);
	}

	@Override
	public Address saveUpdate(Address entity) throws Throwable {
		return addrsRepo.saveAndFlush(entity);
	}

	@Override
	public UserProfession saveUpdate(UserProfession entity) throws Throwable {
		return profRepo.saveAndFlush(entity);
	}

	@Override
	public UserWorkExpc saveUpdate(UserWorkExpc entity) throws Throwable {
		return workExpcRepo.saveAndFlush(entity);
	}

	@Override
	public Status status(String name, String refTable) throws Throwable {
		return statusRepo.findByNameAndRefTable(name, refTable);
	}

	@Override
	public Optional<Status> status(UUID id) throws Throwable {
		return statusRepo.findById(id);
	}

	@Override
	public List<Status> status() throws Throwable {
		return statusRepo.findAll();
	}

	@Override
	public List<Status> status(Example<Status> status, Sort sort) throws Throwable {
		return statusRepo.findAll(status, sort);
	}

	@Override
	public Optional<Type> type(UUID id) throws Throwable {
		return typeRepo.findById(id);
	}

	@Override
	public Optional<Type> type(String name) throws Throwable {
		return typeRepo.findOne(Example.of(new Type(name)));
	}

	@Override
	public List<Type> types() throws Throwable {
		return typeRepo.findAll();
	}

	@Override
	public List<Type> typesByRef(String... name) throws Throwable {
		return typeRepo.findByReferenceNameInOrderByName(name);
	}

	@Override
	public List<Type> typesByRef(UUID... id) throws Throwable {
		return typeRepo.findByReferenceIdInOrderByName(id);
	}

	@Override
	public List<Type> types(Example<Type> type, Sort sort) throws Throwable {
		return typeCrudRepo.findAll(type, sort);
	}

	@Override
	public Optional<User> userById(UUID userId) throws Throwable {
		return userRepo.findById(userId);
	}

	@Override
	public User byCode(String userCode) throws Throwable {
		return userRepo.findByCode(userCode);
	}

	@Override
	public User byMobile(Long mobile) throws Throwable {
		return userRepo.findByMobile(mobile);
	}

	@Override
	public User byEmail(String email) throws Throwable {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> user(Example<User> user) throws Throwable {
		return userRepo.findAll(user);
	}

	@Override
	public Page<User> user(Pageable pageable) throws Throwable {
		return userCrudRepo.findAll(pageable);
	}

	@Override
	public Page<User> user(Example<User> example, Pageable pageable) throws Throwable {
		return userCrudRepo.findAll(example, pageable);
	}

	@Override
	public Iterable<User> search(UserSearchReq userSearchReq) throws Throwable {
		return userDao.search(userSearchReq);
	}

	@Override
	public Address addrs(UUID id) throws Throwable {
		return addrsRepo.findById(id).get();
	}

	@Override
	public List<Address> addrs(User user) throws Throwable {
		return addrsRepo.findByUser(user);
	}

	@Override
	public List<Address> addrs(Example<Address> addrs) throws Throwable {
		return addrsRepo.findAll(addrs);
	}

	@Override
	public Role role(String id) throws Throwable {
		return roleRepo.findById(id).get();
	}

	@Override
	public List<Role> role() throws Throwable {
		return roleRepo.findAll();
	}

	@Override
	public UserKyc kycDesc(UUID kycId) {
		return usrKycRepo.findById(kycId).get();
	}

	@Override
	public UserKyc kycDesc(User user, String docNumber) {
		return usrKycRepo.findByUserAndDocNumber(user, docNumber);
	}

	@Override
	public List<UserKyc> kycDesc(User user) {
		return usrKycRepo.findByUser(user);
	}

	@Override
	public UserProfession profession(UUID professionId) {
		return profRepo.findById(professionId).get();
	}

	@Override
	public List<UserProfession> profession() {
		return profRepo.findAll();
	}

	@Override
	public UserWorkExpc workExpc(UUID workExpcId) {
		return workExpcRepo.findById(workExpcId).get();
	}

	@Override
	public List<UserWorkExpc> workExpcList(Example<UserWorkExpc> workExpc) {
		return workExpcRepo.findAll(workExpc);
	}

}
