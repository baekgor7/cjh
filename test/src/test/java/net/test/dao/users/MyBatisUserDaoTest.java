package net.test.dao.users;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.test.dao.MyBatisTest;
import net.test.domain.users.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/applicationContext.xml")
public class MyBatisUserDaoTest {

	private static final Logger log = LoggerFactory.getLogger(MyBatisTest.class);
	
	@Autowired
	private UserDao userDao;

	@Test
	public void findById() {
		User user = userDao.findById("test2");
		log.debug("User : {}", user);
	}

}
