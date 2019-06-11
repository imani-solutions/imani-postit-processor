package com.github.nylasolutions.imanipostitprocessor

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.concurrent.TimeUnit
import nyla.solutions.core.security.user.data.UserProfile;
import org.junit.Assert.*

//@RunWith(SpringRunner::class)
//@SpringBootTest
class ImaniPostitProcessorApplicationTests {

	@Test
	fun basicListOfEmails() {

		var year = 2019;
		var month = 3;
		var dayOfMonth = 10;
		var hour = 10;
		var min = 10;
		var sec = 10;

		var date : LocalDateTime = LocalDateTime.of(year,month,dayOfMonth,hour,min,sec)
		var dao = PostitDao();


		var list : List<UserProfile> = dao.selectAnniversaryByDate(date);
		assertNotNull(list)

		assertTrue(list.size > 0);

		for (u in list){
			assertTrue("Has email",u.email != null && u.email.length > 0);
		}


	}

}
