package com.chy.sqlSession;

import com.chy.pojo.Configuration;
import com.chy.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface Executor {

    public <E> List<E> query(Configuration configuration,
                             MappedStatement mappedStatement,
                             Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, ClassNotFoundException;
}
