package com.chy.dao;

import com.chy.pojo.User;
import org.dom4j.DocumentException;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface IUserDao {

    // select all user
    public List<User> findAll() throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, DocumentException, PropertyVetoException, ClassNotFoundException;

    // select user by property
    public User findByCondition(User user) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, DocumentException, PropertyVetoException, ClassNotFoundException;

    // add user
    public User addOne(User user)throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, DocumentException, PropertyVetoException, ClassNotFoundException;

    // update user
    public User update(User user)throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, DocumentException, PropertyVetoException, ClassNotFoundException;

    // delete user
    public User delete(User user)throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, DocumentException, PropertyVetoException, ClassNotFoundException;
}
