package com.amdocs.user.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.User;
import com.amdocs.user.entity.UserProfession;
import com.amdocs.user.entity.UserWorkExpc;

@Repository
interface UserWorkExpcRepo extends JpaRepository<UserWorkExpc, UUID> {

	public UserWorkExpc findByUser(User user);
}

@Repository
interface UserProfessionRepo extends JpaRepository<UserProfession, UUID> {

}
