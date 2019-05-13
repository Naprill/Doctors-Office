package com.chernivtsi.doctorsoffice.repository;

import com.chernivtsi.doctorsoffice.model.Reception;
import com.chernivtsi.doctorsoffice.model.Reception_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {

	private EntityManager entityManager;

	public ScheduleRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Page<Reception> getReceptionsByDateAndUser(Pageable pageable, LocalDate date, Long userId) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reception> criteriaQuery = criteriaBuilder.createQuery(Reception.class);
		Root<Reception> receptionRoot = criteriaQuery.from(Reception.class);

		List<Predicate> predicates = new ArrayList<>();

		if (date != null) {
			predicates.add(criteriaBuilder.equal(receptionRoot.get(Reception_.date), date)); //"date"
		}
		if (userId != null) {
			predicates.add(criteriaBuilder.equal(receptionRoot.get("user"), userId)); //Reception_.user.getId()
		}

		criteriaQuery.select(receptionRoot)
				.where(predicates.toArray(new Predicate[0]))
				.orderBy(criteriaBuilder.asc(receptionRoot.get(Reception_.intervalStart)));

		TypedQuery<Reception> query = entityManager.createQuery(criteriaQuery);
		long total = (long) query.getResultList().size();
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return new PageImpl<>(query.getResultList(), pageable, total);
	}
}
