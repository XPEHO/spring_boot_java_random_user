package com.xpeho.spring_boot_java_random_user.data.sources.database;

import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE " +
            "(:gender IS NULL OR LOWER(gender) = LOWER(:gender)) AND " +
            "(:firstname IS NULL OR LOWER(firstname) LIKE LOWER(CONCAT('%', :firstname, '%'))) AND " +
            "(:lastname IS NULL OR LOWER(lastname) LIKE LOWER(CONCAT('%', :lastname, '%'))) AND " +
            "(:civility IS NULL OR civility LIKE CONCAT('%', :civility, '%')) AND " +
            "(:email IS NULL OR email LIKE CONCAT('%', :email, '%')) AND " +
            "(:phone IS NULL OR phone LIKE CONCAT('%', :phone, '%')) AND " +
            "(:nationality IS NULL OR LOWER(nationality) LIKE LOWER(CONCAT('%', :nationality, '%')))")
    List<User> findByFilters(
            @Param("gender") String gender,
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("civility") String civility,
            @Param("email") String email,
            @Param("phone") String phone,
            @Param("nationality") String nationality
    );
}
