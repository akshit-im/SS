package com.amdocs.user.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.User;

@Repository
interface UserRepo extends JpaRepository<User, UUID> {

	@Query(value = "SELECT CONCAT((SELECT ORG.CODE FROM ORG WHERE ID=?1),IFNULL(COUNT(AUSER.ID),0)+1) FROM AUSER", nativeQuery = true)
	public String genUserCode(Long orgId);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByCode(String userCode);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByMobile(Long mobile);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByEmail(String email);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByLoginId(String loginId);
}
