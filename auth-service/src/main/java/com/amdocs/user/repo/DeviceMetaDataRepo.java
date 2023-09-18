package com.amdocs.user.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amdocs.user.entity.DeviceMetaData;
import com.amdocs.user.entity.User;

@Repository
interface DeviceMetaDataRepo extends JpaRepository<DeviceMetaData, Long> {

	public List<DeviceMetaData> findByUser(User user);
}
