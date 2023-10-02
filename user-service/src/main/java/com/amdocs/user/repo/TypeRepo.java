package com.amdocs.user.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.Type;

@Repository
interface TypeRepo extends JpaRepository<Type, UUID> {

	public List<Type> findByReferenceNameInOrderByName(String[] name);

	public List<Type> findByReferenceIdInOrderByName(UUID[] id);
}

interface TypeCrudRepo extends CrudRepository<Type, UUID> {

	@EntityGraph(attributePaths = {"reference"})
	public List<Type> findAll(Example<Type> type, Sort sort);
}