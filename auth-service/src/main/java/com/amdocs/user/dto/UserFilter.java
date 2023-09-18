package com.amdocs.user.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;

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
	private final LinkedHashMap<String, String> genderList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Male", "Male");
			put("Female", "Female");
			put("Other", "Other");
		}
	};

	@Transient
	private final LinkedHashMap<String, String> religionList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Christianity", "Christianity");
			put("Islam", "Islam");
			put("Hinduism", "Hinduism");
			put("Buddhism", "Buddhism");
			put("Sikhism", "Sikhism");
			put("Judaism", "Judaism");
			put("Folk religions", "Folk religions");
		}
	};

	@Transient
	private final LinkedHashMap<String, String> casteList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Gen", "General");
			put("SC", "Scheduled Castes");
			put("ST", "Scheduled Tribes");
			put("OBC", "OBC");
		}
	};

	@Transient
	private final LinkedHashMap<String, String> maritalList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Unmarried", "Unmarried");
			put("Married", "Married");
			put("Separated", "Separated");
			put("Divorced", "Divorced");
			put("Widowed", "Widowed");
		}
	};

	@Transient
	private final LinkedHashMap<String, String> orgTypeList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Public Limited Company", "Public Limited Company");
			put("Private Limited Company", "Private Limited Company");
			put("Partnership Company)", "Partnership Company)");
			put("Proprietorship Company", "Proprietorship Company");
			put("Govt. Sector Company", "Govt. Sector Company");
			put("Others", "Others");
		}
	};

	@Transient
	private LinkedHashMap<String, String> degreeTypeList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Matric", "Matric");
			put("Intermediate", "Intermediate");
			put("Bachelor Degree", "Bachelor Degree");
			put("Master degree", "Master degree");
			put("Doctoral degree", "Doctoral degree");
			put("Post Doctoral", "Post Doctoral");
			put("Higher Studies", "Higher Studies");
		}
	};

	@Transient
	private final LinkedHashMap<String, String> userDocsList = new LinkedHashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("Profile Picture", "Profile Picture");
			put("Pan Card", "Pan Card");
			put("Aadhar Card", "Aadhar Card");
			put("Voter ID Card", "Voter ID Card");
			put("Passport", "Passport");
			put("Driving Licence", "Driving Licence");
			put("Domicile Certificate", "Domicile Certificate");
			put("Income Certificate", "Income Certificate");
			put("Visiting Card", "Visiting Card");
			put("Bank Account", "Bank Account");
			put("GSTIN Number", "GSTIN Number");
			put("Others", "Others");
		}
	};

}
