package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Transient;
import lombok.Data;

@Data
public class UserFilter implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private UserFilter() {
		if (UserFilterIn.INSTANCE != null) {
			throw new RuntimeException("UserFilter Object Already Created");
		}
	}

	private static class UserFilterIn {
		private static volatile UserFilter INSTANCE = new UserFilter();
	}

	public static UserFilter getInstance() {
		return UserFilterIn.INSTANCE;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return getInstance();
	}

	public Object readResolve() {
		return getInstance();
	}

	@Transient
	private final Set<String> genderList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Male");
			add("Female");
			add("Other");
		}
	};

	@Transient
	private final Set<String> religionList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Christianity");
			add("Islam");
			add("Hinduism");
			add("Buddhism");
			add("Sikhism");
			add("Judaism");
			add("Folk religions");
		}
	};

	@Transient
	private final Set<String> casteList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("General");
			add("Scheduled Castes");
			add("Scheduled Tribes");
			add("OBC");
		}
	};

	@Transient
	private final Set<String> maritalList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Unmarried");
			add("Married");
			add("Separated");
			add("Divorced");
			add("Widowed");
		}
	};

	@Transient
	private final Set<String> mcaTypeList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Public Limited Company");
			add("Private Limited Company");
			add("Partnership Company");
			add("Proprietorship Company");
			add("Govt. Sector Company");
			add("Others");
		}
	};

	@Transient
	private Set<String> degreeTypeList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Matric");
			add("Intermediate");
			add("Bachelor Degree");
			add("Master degree");
			add("Doctoral degree");
			add("Post Doctoral");
			add("Higher Studies");
		}
	};

	@Transient
	private final Set<String> userDocsList = new HashSet<String>() {
		private static final long serialVersionUID = 1L;
		{
			add("Profile Picture");
			add("Pan Card");
			add("Aadhar Card");
			add("Voter ID Card");
			add("Passport");
			add("Driving Licence");
			add("Domicile Certificate");
			add("Income Certificate");
			add("Visiting Card");
			add("Bank Account");
			add("GSTIN Number");
			add("Others");
		}
	};
}
