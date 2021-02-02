package com.chy.sqlSession;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface SqlSession {

    //查询所有
    public <E> List<E> selectList(String statementid,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException;

    //根据条件查询单个
    public <T> T selectOne(String statementid,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException;

    public <T> T addOne(String statmentid,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException;

    public <T> T update(String statmentid,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException;

    public <T> T delete(String statmentid,Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException;

    //为Dao接口实现代理实现类
    public <T> T getMapper(Class<?> mapperClass);
}
