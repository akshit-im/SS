package com.amdocs.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.amdocs.entity.Brand;

public interface ProductService {

	public Brand saveUpdate(Brand entity) throws Throwable;

	public Optional<Brand> brand(UUID id) throws Throwable;

	public Iterable<Brand> brand(Example<Brand> user) throws Throwable;

	public Page<Brand> brand(Example<Brand> example, Pageable pageable) throws Throwable;

	public Page<Brand> brand(Pageable pageable) throws Throwable;
}
