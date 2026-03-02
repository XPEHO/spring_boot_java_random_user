package com.xpeho.spring_boot_java_random_user;

import com.xpeho.spring_boot_java_random_user.data.sources.database.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
class SpringBootJavaRandomUserApplicationTests {

	@MockitoBean
	UserRepository userRepository;

	@Test
	void contextLoads() {
	}

}
