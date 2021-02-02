package com.chy.test;

import com.chy.dao.IUserDao;
import com.chy.io.Resources;
import com.chy.pojo.User;
import com.chy.sqlSession.SqlSession;
import com.chy.sqlSession.SqlSessionFactory;
import com.chy.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class IpersistenceTest {

    @Test
    public void select() throws DocumentException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(19);
        user.setName("chy");
//        User user1 = sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user1.getId()+" "+user1.getName());

        /*List<User> userList = sqlSession.selectList("user.selectList");
        for (User user2 : userList) {
            System.out.println(user2.getId()+" "+user2.getName());
        }*/
        // mapper 是代理对象
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        //代理对象调用任意方法，都会执行invoke方法
//        List<User> all = mapper.findAll();
//        System.out.println(all.get(0).getId()+" "+all.get(0).getName());
        User user1 = mapper.findByCondition(user);
        System.out.println(user1.getId()+" "+user1.getName());

    }

    @Test
    public void add() throws DocumentException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(19);
        user.setName("chy");
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        User user1 = mapper.addOne(user);
    }

    @Test
    public void update() throws DocumentException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(19);
        user.setName("updatechy");
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        mapper.update(user);
    }

    @Test
    public void delete() throws DocumentException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(19);
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        mapper.delete(user);
    }
}
