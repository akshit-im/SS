package com.amdocs.repo;

import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.entity.Brand;

@Repository
interface BrandRepo extends JpaRepository<Brand, UUID> {

}

interface BrandCrudRepo extends CrudRepository<Brand, UUID> {

	public Page<Brand> findAll(Pageable pageable);

	public Page<Brand> findAll(Example<Brand> example, Pageable pageable);
}