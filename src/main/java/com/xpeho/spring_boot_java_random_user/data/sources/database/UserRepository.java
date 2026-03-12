package com.xpeho.spring_boot_java_random_user.data.sources.database;

import com.xpeho.spring_boot_java_random_user.data.models.database.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
