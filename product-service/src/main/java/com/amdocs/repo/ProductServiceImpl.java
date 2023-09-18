package com.amdocs.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.amdocs.entity.Brand;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private BrandRepo brandRepo;

	@Override
	public Brand saveUpdate(Brand entity) throws Throwable {
		return brandRepo.saveAndFlush(entity);
	}

	@Override
	public Optional<Brand> brand(UUID id) throws Throwable {
		return brandRepo.findById(id);
	}

	@Override
	public Iterable<Brand> brand(Example<Brand> example) throws Throwable {
		return brandRepo.findAll(example);
	}

	@Override
	public Page<Brand> brand(Example<Brand> example, Pageable pageable) throws Throwable {
		return brandRepo.findAll(null, pageable);
	}

	@Override
	public Page<Brand> brand(Pageable pageable) throws Throwable {
		return brandRepo.findAll(pageable);
	}

}
