package net.test.dao.users;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.test.domain.users.User;

public class MyBatisUserDao implements UserDao {
	private static final Logger log = LoggerFactory.getLogger(MyBatisUserDao.class);
	
	private SqlSession SqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.SqlSession = sqlSession;
	}

	@Override
	public User findById(String userId) {
		log.debug("mybatis findById");
		return SqlSession.selectOne("user.userMapper.findById", userId);
	}

	@Override
	public void create(User user) {
		log.debug("mybatis create");
		SqlSession.insert("user.userMapper.create", user);
	}

	@Override
	public void update(User user) {
		log.debug("mybatis update");
		SqlSession.insert("user.userMapper.update", user);
	}

}
