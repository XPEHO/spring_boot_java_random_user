package com.xpeho.spring_boot_java_random_user.data.sources.database;

import com.xpeho.spring_boot_java_random_user.data.models.database.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserDAO, Long>, JpaSpecificationExecutor<UserDAO> {
}
