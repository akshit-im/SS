package com.amdocs.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.Status;

@Repository
interface StatusRepo extends JpaRepository<Status, String> {

}