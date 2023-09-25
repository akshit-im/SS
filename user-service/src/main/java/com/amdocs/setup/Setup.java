package com.amdocs.setup;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amdocs.user.entity.Role;
import com.amdocs.user.entity.Status;
import com.amdocs.user.entity.Type;
import com.amdocs.user.entity.User;
import com.amdocs.user.repo.UserService;

import jakarta.annotation.PostConstruct;

@Component
class Setup {

	@Autowired
	private UserService userSvc;

	@PostConstruct
	public void init() {

		try {
			if (userSvc.role().size() < 1) {
				List<Role> roleList = new ArrayList<>();
				roleList.add(new Role("Administrator", "Active"));
				roleList.add(new Role("Director", "Active"));
				roleList.add(new Role("Manager", "Active"));
				roleList.add(new Role("Employee", "Active"));
				roleList.add(new Role("Representative", "Active"));
				roleList.add(new Role("Manufacturer", "Active"));
				roleList.add(new Role("Distributer", "Active"));
				roleList.add(new Role("Client", "Active"));
				roleList.add(new Role("Application", "Active"));
				userSvc.roleSaveUpdate(roleList);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		try {
			if (userSvc.status().size() < 1) {
				List<Status> statusList = new ArrayList<>();
				statusList.add(new Status("Active", "USER"));
				statusList.add(new Status("Deactive", "USER"));
				statusList.add(new Status("Locked", "USER"));
				userSvc.statusSaveUpdate(statusList);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		try {
			if (userSvc.types().size() < 1) {
				List<Type> typeList = new ArrayList<>();
				Type individual = new Type("Individual", "Active");
				typeList.add(individual);
				typeList.add(new Type("Regular", individual));
				typeList.add(new Type("International", individual));

				Type business = new Type("Business", "Active");
				typeList.add(business);
				typeList.add(new Type("Regular", business));
				typeList.add(new Type("Retail", business));
				typeList.add(new Type("Reseller", business));
				typeList.add(new Type("Wholesale", business));
				typeList.add(new Type("Dealer", business));
				typeList.add(new Type("Corporate", business));
				typeList.add(new Type("Manufacturer", business));
				typeList.add(new Type("Government", business));
				typeList.add(new Type("NonProfit", business));

				Type special = new Type("Special", "Active");
				typeList.add(special);
				typeList.add(new Type("Employee", special));
				typeList.add(new Type("Contractor", special));
				typeList.add(new Type("Administrator", special));
				typeList.add(new Type("WEB", special));
				typeList.add(new Type("API", special));

				try {
					userSvc.typeSaveUpdate(typeList);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

		try {
			if (userSvc.byCode("EXP360UI") == null) {
				User user = new User();
				user.setName("EXP360 UI");
				user.setCode("EXP360UI");
				user.setMobile(1000000001L);
				user.setEmail("exp360ui@gmail.com");
				user.setLoginId("a9e463de1328718f96004ab222e4ed24");
				user.setPassword("Singh!@#app");
				user.setType(userSvc.typeByName("WEB").get());
				user.setStatus(new Status("Active", "USER"));
				user.setAccountLocked(false);
				userSvc.saveUpdate(user, true);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}
}
