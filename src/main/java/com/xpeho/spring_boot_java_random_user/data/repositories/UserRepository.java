package com.xpeho.spring_boot_java_random_user.data.repositories;

import com.xpeho.spring_boot_java_random_user.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
