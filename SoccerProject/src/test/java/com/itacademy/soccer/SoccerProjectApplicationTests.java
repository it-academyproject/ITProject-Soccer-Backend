package com.itacademy.soccer;

import com.itacademy.soccer.controller.StadiumController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SoccerProjectApplicationTests {

	@Autowired
	StadiumController stadiumController;

	@Test
	void contextLoads() throws Exception {
		assertThat(stadiumController).isNotNull();

	}




}
