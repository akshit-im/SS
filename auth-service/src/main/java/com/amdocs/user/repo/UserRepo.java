package com.amdocs.user.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.User;

@Repository
interface UserRepo extends JpaRepository<User, UUID> {

	@Query(value = "SELECT CONCAT((SELECT ORG.CODE FROM ORG WHERE ID=?1),IFNULL(COUNT(AUSER.ID),0)+1) FROM AUSER", nativeQuery = true)
	public String genUserCode(Long orgId);

	public User findByCode(String userCode);

	public User findByMobile(Long mobile);

	public User findByEmail(String email);

	public User findByLoginId(String loginId);
}
