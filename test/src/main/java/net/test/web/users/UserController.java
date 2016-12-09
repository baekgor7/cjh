package net.test.web.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.test.dao.users.UserDao;
import net.test.domain.users.User;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value="/form", method=RequestMethod.GET) //GET방식은 기본으로 @RequestMapping("/users/form") 이것만 써줘도 됨
	public String form(Model model) {
		model.addAttribute("user", new User());
		return "users/form";
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public String create(User user) {
		log.debug("User : {}", user);
		userDao.create(user);
		log.debug("Database : {}", userDao.findById(user.getUserId()));
		return "users/form";
	}
}
