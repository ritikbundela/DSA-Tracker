	package com.dsatrackeradv.dsa_tracker_adv;

	import org.springframework.boot.SpringApplication;
	import org.springframework.boot.autoconfigure.SpringBootApplication;
	import org.springframework.cache.annotation.EnableCaching;
	import org.springframework.scheduling.annotation.EnableScheduling;

	@SpringBootApplication
	@EnableCaching
	@EnableScheduling
	public class  DsaTrackerAdvApplication {

		public static void main(String[] args) {
			SpringApplication.run(DsaTrackerAdvApplication.class, args);
		}

	}

