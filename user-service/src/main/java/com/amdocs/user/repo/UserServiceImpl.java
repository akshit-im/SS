package com.amdocs.user.repo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amdocs.user.dto.ReqisterReq;
import com.amdocs.user.dto.UserSearchReq;
import com.amdocs.user.entity.Address;
import com.amdocs.user.entity.Role;
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

	@Override
	public User saveUpdate(User user, Boolean encrypt) throws Throwable {
		if (encrypt) {
			String hashedPassword = passEncode.encode(user.getPassword());
			user.setPassword(hashedPassword);
		}
		return userRepo.saveAndFlush(user);
	}

	public User saveUpdate(ReqisterReq user) throws Throwable {
		User userEntity = user.user();
		userEntity.setPassword(passEncode.encode(user.getPassword()));
		return userRepo.saveAndFlush(userEntity);
	}

	@Override
	public UserKyc saveUpdate(UserKyc userKyc) throws Throwable {
		return usrKycRepo.saveAndFlush(userKyc);
	}

	@Override
	public Address saveUpdate(Address address) throws Throwable {
		return addrsRepo.saveAndFlush(address);
	}

	@Override
	public UserProfession saveUpdate(UserProfession profession) throws Throwable {
		return profRepo.saveAndFlush(profession);
	}

	@Override
	public UserWorkExpc saveUpdate(UserWorkExpc workExpc) throws Throwable {
		return workExpcRepo.saveAndFlush(workExpc);
	}

	@Override
	public Optional<User> byId(UUID userId) throws Throwable {
		System.out.println("#" + userId);
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
