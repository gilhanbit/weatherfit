package com.weatherfit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
class WeatherfitApplicationTests {

	@Test
	void test1() {
		 log.info("hello world");
	}

	@Transactional // insert -> delete 롤백
	@Test
	void test2() {
//		int a = 10;
//		int b = 20;
//		assertEquals(expected:30, actual:a+b);
	}

}
