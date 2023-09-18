package com.amdocs.user.repo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amdocs.user.dto.UserSearchReq;
import com.amdocs.user.entity.User;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Iterable<User> search(UserSearchReq searchReq) throws Throwable {
		CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		Root<User> user = cq.from(User.class);
		try {
			if (searchReq.getFromDate() == null) {
				throw new Exception("Empty Date Range");
			}
			// predicates.add(cb.greaterThanOrEqualTo(user.get("apptDate"), dtFrom));
			// predicates.add(cb.lessThanOrEqualTo(user.get("apptDate"), dtUpTo));
			predicates.add(cb.between(user.get("doEntry"), searchReq.getFromDate(), searchReq.getToDate()));
		} catch (Exception e) {
		}
		try {
			if (searchReq.getName() == null || searchReq.getName().equalsIgnoreCase("")) {
				throw new Exception("Name Empty");
			}
			predicates.add(cb.like(user.get("userName"), "%" + searchReq.getName() + "%"));
		} catch (Exception e) {
		}
		try {
			if (searchReq.getMobile() == null || searchReq.getMobile() == 0) {
				throw new Exception("Mobile Empty");
			}
			predicates.add(cb.equal(user.get("mobile"), searchReq.getMobile()));
		} catch (Exception e) {
		}
		try {
			if (searchReq.getEmail() == null || searchReq.getEmail().equalsIgnoreCase("")) {
				throw new Exception("Email Empty");
			}
			predicates.add(cb.like(user.get("email"), "%" + searchReq.getEmail() + "%"));
		} catch (Exception e) {
		}
		try {
			In<Integer> statusIn = cb.in(user.get("status"));
			for (Integer status : searchReq.getStatus()) {
				statusIn.value(status);
			}
			predicates.add(statusIn);
		} catch (Exception e) {
		}
		try {
			if (searchReq.getCountryId() == null || searchReq.getCountryId() == 0) {
				throw new Exception("Country Empty");
			}
			predicates.add(cb.equal(user.get("countryId"), searchReq.getCountryId()));
		} catch (Exception e) {
		}
		cq.where(predicates.toArray(new Predicate[0]));

		TypedQuery<User> query = sessionFactory.createEntityManager().createQuery(cq);
		Set<User> set = new HashSet<User>();
		for (User x : query.getResultList())
			set.add(x);

		return set;
	}

}
