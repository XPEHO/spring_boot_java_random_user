package com.xpeho.spring_boot_java_random_user;

import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SpringBootJavaRandomUserApplicationTests {

	@Autowired
	ApplicationContext applicationContext;

	@MockitoBean
	UserRepository userRepository;

	@Test
	@DisplayName("Spring context should load successfully")
	void springContextShouldLoadSuccessfully() {
		assertThat(applicationContext).isNotNull();
	}

}
