package com.amdocs.user.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.Type;

@Repository
interface TypeRepo extends JpaRepository<Type, UUID> {

}

interface TypeCrudRepo extends CrudRepository<Type, UUID> {

	@EntityGraph(attributePaths = {"reference"})
	public List<Type> findAll(Example<Type> example);
}