package com.amdocs.user.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserKyc;

@Repository
interface UserKycRepo extends JpaRepository<UserKyc, UUID> {

	public UserKyc findByUserAndDocNumber(User user, String docNumber);

	public List<UserKyc> findByUser(User user);

}
