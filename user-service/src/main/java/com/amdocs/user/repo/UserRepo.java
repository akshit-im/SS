package com.amdocs.user.repo;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.User;

@Repository
interface UserRepo extends JpaRepository<User, UUID> {

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByCode(String userCode);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByMobile(Long mobile);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public User findByEmail(String email);

}

interface UserCrudRepo extends CrudRepository<User, UUID> {

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public Page<User> findAll(Pageable pageable);

	@EntityGraph(attributePaths = {"type", "refId", "status", "roles"})
	public Page<User> findAll(Example<User> example, Pageable pageable);
}