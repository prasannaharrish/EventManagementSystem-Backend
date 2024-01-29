package com.project.eventManagement.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class ValidateUniqueField implements ConstraintValidator<UniqueField, String> {

    private String fieldName;
    private Class<?> className;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueField uniqueField) {
        fieldName = uniqueField.fieldName();
        className = uniqueField.className();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

        if (value == null || value.trim().isEmpty()) {
            return true;
        }

        String queryString = "SELECT COUNT(e) FROM " + className.getSimpleName() + " e WHERE e." + fieldName
                + "= :value";
        Long count = entityManager.createQuery(queryString, Long.class).setParameter("value", value)
                .getSingleResult();

        return count == 0;
        
    }

}
