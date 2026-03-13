package com.xpeho.spring_boot_java_random_user.data.sources.database;

import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT * FROM users WHERE " +
            "(:gender IS NULL OR LOWER(gender) LIKE LOWER(:gender)) AND " +
            "(:firstname IS NULL OR LOWER(firstname) LIKE LOWER(:firstname)) AND " +
            "(:lastname IS NULL OR LOWER(lastname) LIKE LOWER(:lastname)) AND " +
            "(:civility IS NULL OR LOWER(civility) LIKE LOWER(:civility)) AND " +
            "(:email IS NULL OR LOWER(email) LIKE LOWER(:email)) AND " +
            "(:phone IS NULL OR LOWER(phone) LIKE LOWER(:phone)) AND " +
            "(:nationality IS NULL OR LOWER(nationality) LIKE LOWER(:nationality))")
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
