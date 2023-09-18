package com.amdocs.user.repo;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import com.amdocs.user.entity.DeviceMetaData;
import com.amdocs.user.entity.User;

import ua_parser.Client;
import ua_parser.Parser;

@SuppressWarnings("unused")
@Component
public class DeviceService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String UNKNOWN = "UNKNOWN";

	@Autowired
	private DeviceMetaDataRepo deviceRepo;

	private String from;

	private MessageSource messages;

	public void verifyDevice(String userId, HttpServletRequest request) throws IOException, AccessDeniedException {
		String deviceDetails = getDeviceDetail(request.getHeader("user-agent"));
		DeviceMetaData existingDevice = findExistingDevice(userId, deviceDetails);
		if (Objects.isNull(existingDevice)) {
			DeviceMetaData deviceMetadata = new DeviceMetaData();
			deviceMetadata.setUser(new User(UUID.fromString(userId)));
			deviceMetadata.setLocation("NA");
			deviceMetadata.setDeviceDetail(deviceDetails.replace(".null", ""));
			deviceMetadata.setLastLogin(new Date());
			deviceRepo.saveAndFlush(deviceMetadata);
			// emailServiceUser.newDevice(userSvc.device(deviceMetadata.getId()), request);
		} else {
			DeviceMetaData deviceMetaData = new DeviceMetaData();
			deviceMetaData.setId(existingDevice.getId());
			deviceMetaData.setLastLogin(new Date());
			deviceMetaData.setUser(new User(UUID.fromString(userId)));
			deviceMetaData.setLocation("NA");
			deviceMetaData.setDeviceDetail(deviceDetails.replace(".null", ""));
			deviceRepo.saveAndFlush(deviceMetaData);
		}
	}

	private String getDeviceDetail(String userAgent) throws IOException {
		String deviceDetails = "";
		Parser parser = new Parser();
		Client client = parser.parse(userAgent);
		if (Objects.nonNull(client)) {
			deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor + " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
		}
		return deviceDetails;
	}

	private DeviceMetaData findExistingDevice(String userId, String deviceDetails) throws AccessDeniedException {
		List<DeviceMetaData> knownDevices = null;// deviceRepo.findByUser(userId);
		boolean test = true;
		for (DeviceMetaData existingDevice : knownDevices) {
			// if (existingDevice.getDeviceDetail().equals(deviceDetails) && existingDevice.getLocation().equals("NA") && test) {
			return existingDevice;
			// }else {
			// test=false;
			// throw new AccessDeniedException("This Device is not Registered with your Account, Please Contact Respective Authority");
			// }
		}
		return null;
	}

}