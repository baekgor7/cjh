package net.test.domain.users;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class UserTest {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void userIdWhenIsEmpty() {
		User user = new User("", "password", "name", "test@test.net");		
		Set<ConstraintViolation<User>> constrainViolations = validator.validate(user);		    
		
		assertThat(constrainViolations.size(), is(2));
	}

}
