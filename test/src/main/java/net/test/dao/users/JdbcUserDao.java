package net.test.dao.users;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import net.test.domain.users.User;

public class JdbcUserDao extends JdbcDaoSupport implements UserDao {
	
	private static final Logger log = LoggerFactory.getLogger(JdbcUserDao.class);

	@PostConstruct
	public void initialize() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("test.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		
		log.info("database initialized success!");
	}
	
	/* (non-Javadoc)
	 * @see net.test.dao.users.IUserDao#findById(java.lang.String)
	 */
	@Override
	public User findById(String userId) {
		String sql = " select * from USERS where userId = ? ";
		RowMapper<User> rowMaapper = new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {				
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
			}			
		};
		
		try {
			return getJdbcTemplate().queryForObject(sql, rowMaapper, userId);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see net.test.dao.users.IUserDao#create(net.test.domain.users.User)
	 */
	@Override
	public void create(User user) {
		String sql = " insert into USERS values (?, ?, ?, ?) ";
		getJdbcTemplate().update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	/* (non-Javadoc)
	 * @see net.test.dao.users.IUserDao#update(net.test.domain.users.User)
	 */
	@Override
	public void update(User user) {
		String sql = " update USERS set password = ?, name = ?, email = ? where userId = ? ";
		getJdbcTemplate().update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
		
	}
}
