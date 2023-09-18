package com.amdocs.user.repo;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.Address;
import com.amdocs.user.entity.User;

@Repository
interface AddressRepo extends JpaRepository<Address, UUID> {

	public List<Address> findByUser(User user);

}
