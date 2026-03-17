package com.xpeho.spring_boot_java_random_user.data.sources.database;

import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public final class UserSpecifications {
    private UserSpecifications() {
    }

    public static Specification<User> byFilter(UserFilter filter) {
        return (user, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.gender() != null) {
                predicates.add(criteriaBuilder.equal(
                        criteriaBuilder.lower(user.get("gender")),
                        filter.gender().name().toLowerCase()
                ));
            }

            addContainsPredicate(predicates, criteriaBuilder, user.get("firstname"), filter.firstname());
            addContainsPredicate(predicates, criteriaBuilder, user.get("lastname"), filter.lastname());
            addContainsPredicate(predicates, criteriaBuilder, user.get("civility"), filter.civility());
            addContainsPredicate(predicates, criteriaBuilder, user.get("email"), filter.email());
            addContainsPredicate(predicates, criteriaBuilder, user.get("phone"), filter.phone());
            addContainsPredicate(predicates, criteriaBuilder, user.get("nationality"), filter.nat());

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addContainsPredicate(
            List<Predicate> predicates,
            CriteriaBuilder criteriaBuilder,
            Path<String> field,
            String value
    ) {
        if (!StringUtils.hasText(value)) {
            return;
        }

        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(field),
                "%" + value.toLowerCase() + "%"
        ));
    }
}
