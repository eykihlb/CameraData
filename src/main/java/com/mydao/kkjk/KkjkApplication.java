package com.mydao.kkjk;

import com.mydao.kkjk.ui.frmMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.xml.ws.Endpoint;

@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class KkjkApplication {

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(KkjkApplication.class);
		ApplicationContext applicationContext = builder.headless(false).run(args);
		frmMain swing = applicationContext.getBean(frmMain.class);
		swing.setVisible(false);
		//SpringApplication.run(KkjkApplication.class, args);
	}

}

