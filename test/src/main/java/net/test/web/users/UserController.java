package net.test.web.users;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.test.dao.users.UserDao;
import net.test.domain.users.Authenticate;
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
	public String create(@Valid User user, BindingResult bindingResult) {
		log.debug("User : {}", user);
		
		if(bindingResult.hasErrors()) {
			log.debug("Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors) {
				log.debug("error : {}, {}", error.getCode(), error.getDefaultMessage());
			}
			
			return "users/form";
		}
		
		userDao.create(user);
		log.debug("Database : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
	@RequestMapping("/login/form")
	public String loginForm(Model model) {
		model.addAttribute("authenticate", new Authenticate());
		return "users/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Valid Authenticate authenticate, BindingResult bindingResult, HttpSession session, Model model) {
		if(bindingResult.hasErrors()) {
			return "users/login";
		}
		
		User user = userDao.findById(authenticate.getUserId());
		
		log.debug("login : {}", user);
		
		if(user == null) {	//아이디가 없으면
			model.addAttribute("errorMessage", "존재하지 않는 사용자입니다.");
			return "users/login";
		}
		
		if(!user.matchPassword(authenticate)) {	//비밀번호가 틀리면
			model.addAttribute("errorMessage", "비밀번호가 틀렸습니다.");
			return "users/login";
		}
		
		//세션정보 저장
		session.setAttribute("userId", user.getUserId());
		
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {		
		session.removeAttribute("userId");
		return "redirect:/";
	}
	
	@RequestMapping("{userId}/form")
	public String updateForm(@PathVariable String userId, Model model) {
		if(userId == null) {
			throw new IllegalArgumentException("사용자 ID가 필요합니다.");
		}
		
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		
		return "users/form";
	}
	
	@RequestMapping(value="", method=RequestMethod.PUT)
	public String update(@Valid User user, BindingResult bindingResult, HttpSession session) {
		log.debug("User : {}", user);
		if(bindingResult.hasErrors()) {
			log.debug("Update Binding Result has error!");
			List<ObjectError> errors = bindingResult.getAllErrors();
			for(ObjectError error : errors) {
				log.debug("error : {}, {}", error.getObjectName(), error.getDefaultMessage());
			}
			return "users/form";
		}
		
		Object temp = session.getAttribute("userId");
		if(temp == null) {
			throw new NullPointerException();
		}
		
		String userId = (String)temp;
		
		if(!user.matchUserId(userId)) {
			throw new NullPointerException();
		}
		
		userDao.update(user);
		log.debug("Database update : {}", userDao.findById(user.getUserId()));
		return "redirect:/";
	}
	
}
