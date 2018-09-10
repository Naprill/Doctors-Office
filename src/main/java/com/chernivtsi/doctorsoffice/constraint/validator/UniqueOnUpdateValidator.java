package com.chernivtsi.doctorsoffice.constraint.validator;

import com.chernivtsi.doctorsoffice.constraint.UniqueOnUpdateConstraint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Field uniqueness validator
 */
@Slf4j
public class UniqueOnUpdateValidator implements ConstraintValidator<UniqueOnUpdateConstraint, Object> {

	private final EntityManager entityManager;

	private UniqueOnUpdateConstraint uniqueFieldOnUpdate;

	public UniqueOnUpdateValidator(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void initialize(UniqueOnUpdateConstraint constraintAnnotation) {
		this.uniqueFieldOnUpdate = constraintAnnotation;
	}

	@Override
	public boolean isValid(Object target, ConstraintValidatorContext context) {
		boolean valid = false;

		final CriteriaBuilder criteriaBuilder = entityManager
				.getCriteriaBuilder();

		final CriteriaQuery<Object> criteriaQuery = criteriaBuilder
				.createQuery();

		final Class<?> entityClass = uniqueFieldOnUpdate.targetClass();
		final Root<?> root = criteriaQuery.from(entityClass);

		Field field = ReflectionUtils.findField(target.getClass(), uniqueFieldOnUpdate.checkField());
		ReflectionUtils.makeAccessible(field);
		Object fieldValue = ReflectionUtils.getField(field, target);

		Predicate equalPredicate = criteriaBuilder.equal(root.get(uniqueFieldOnUpdate.targetField()), fieldValue);

		// replace "id" with constant from AbstractIdentifiable
		criteriaQuery.select(root.get("id")).where(equalPredicate);

		final List<Object> resultSet = entityManager.createQuery(criteriaQuery).getResultList();//can be getSingleResult

		if (resultSet.isEmpty()) {
			valid = true;
		} else if (resultSet.size() == 1 && !uniqueFieldOnUpdate.idField().equals("")) {
			Field idField = ReflectionUtils.findField(target.getClass(), uniqueFieldOnUpdate.idField());
			ReflectionUtils.makeAccessible(idField);
			Object idFieldValue = ReflectionUtils.getField(idField, target);
			valid = resultSet.get(0).equals(idFieldValue);
		}
		return valid;
	}
}
