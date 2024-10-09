package com.dsi.insibo.sice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dsi.insibo.sice.Seguridad.SeguridadService.SessionService;

@SpringBootTest
class SiceApplicationTests {

	@Autowired(required = false)
	SessionService sesion;
	@Test
	void contextLoads() {
	}

}
