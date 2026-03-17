package com.xpeho.spring_boot_java_random_user.data.sources.database;

import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import com.xpeho.spring_boot_java_random_user.domain.entities.UserFilter;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.mockito.Mockito.*;

class UserSpecificationsTest {
    private Root<User> user;
    private CriteriaQuery<?> query;
    private CriteriaBuilder cb;
    private Expression<String> lowerExpr;
    private Predicate predicate;

    @BeforeEach
    @SuppressWarnings("unchecked")
    void setUp() {
        user = mock(Root.class);
        query = mock(CriteriaQuery.class);
        cb = mock(CriteriaBuilder.class);
        lowerExpr = mock(Expression.class);
        predicate = mock(Predicate.class);
        when(cb.lower(any())).thenReturn(lowerExpr);
        when(cb.equal(any(), anyString())).thenReturn(predicate);
        when(cb.like(any(Expression.class), anyString())).thenReturn(predicate);
        when(cb.and(any(Predicate[].class))).thenReturn(predicate);
    }

    

    @Test
    @DisplayName("Should add like predicates for all text fields")
    void shouldAddLikePredicatesForAllTextFields() {
        UserFilter filter = new UserFilter(null, "John", "Doe", "Mr", "john@doe.com", "1234", "FR");

        Specification<User> spec = UserSpecifications.byFilter(filter);
        spec.toPredicate(user, query, cb);

        verify(user).get("firstname");
        verify(user).get("lastname");
        verify(user).get("civility");
        verify(user).get("email");
        verify(user).get("phone");
        verify(user).get("nationality");
        verify(cb).like(lowerExpr, "%john%");
        verify(cb).like(lowerExpr, "%doe%");
        verify(cb).like(lowerExpr, "%mr%");
        verify(cb).like(lowerExpr, "%john@doe.com%");
        verify(cb).like(lowerExpr, "%1234%");
        verify(cb).like(lowerExpr, "%fr%");
    }

    
}
