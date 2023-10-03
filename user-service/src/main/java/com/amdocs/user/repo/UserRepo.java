package com.amdocs.user.repo;

import java.util.List;
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

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public User findByCode(String userCode);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public User findByMobile(Long mobile);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public User findByEmail(String email);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public List<User> findByTypeIdInOrderByName(UUID[] name);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public List<User> findByTypeNameInOrderByName(String[] name);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public List<User> findByTypeReferenceIdInOrderByName(UUID[] name);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public List<User> findByTypeReferenceNameInOrderByName(String[] name);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public List<User> findByRolesNameIn(String[] name);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public List<User> findByRolesIdIn(UUID[] id);
}

interface UserCrudRepo extends CrudRepository<User, UUID> {

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public Page<User> findAll(Pageable pageable);

	@EntityGraph(attributePaths = {"type", "reference", "status", "roles", "city"})
	public Page<User> findAll(Example<User> example, Pageable pageable);
}