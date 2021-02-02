package com.chy.sqlSession;

import com.chy.pojo.Configuration;
import com.chy.pojo.MappedStatement;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;


    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {

        //去完成simpleExecutor里的query方法的调用
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);

        return (List<E>) list;
    }

//    @Override
//    public <E> List<E> selectList(String statementid, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
//        return null;
//    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        List<Object> objects = selectList(statementid, params);
        if(objects.size()==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或返回结果过多");
        }
    }

    @Override
    public <T> T addOne(String statementid, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> objects = simpleExecutor.query(configuration, mappedStatement, params);
        return null;
    }

    @Override
    public <T> T update(String statementid, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> objects = simpleExecutor.query(configuration, mappedStatement, params);
        return null;
    }

    @Override
    public <T> T delete(String statementid, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, ClassNotFoundException {
        simpleExecutor simpleExecutor = new simpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> objects = simpleExecutor.query(configuration, mappedStatement, params);
        return null;
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理来为Dao接口生成代理对象并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        //底层都是执行JDBC
                        //根据不同情况 调用selectList和selectOne
                        //准备参数 1：statementId namesapce.id=接口全限定名.方法名
                        Object result;
                        String methodNameame = method.getName();
                        String className = method.getDeclaringClass().getName();

                        String statementId = className+"."+methodNameame;
                        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                        //准备参数 2: 传入参数：args
                        // 获取被调用方法的返回值类型
                        Type genericReturnType = method.getGenericReturnType();
                        //判断返回类型是否为泛型，如果是泛型返回List，不是泛型返回对象
                        switch (mappedStatement.getMapperType()){
                            case "select":
                                if(genericReturnType instanceof ParameterizedType){
                                    List<Object> list = selectList(statementId, args);
                                    return list;
                                }else {
                                    result = selectOne(statementId, args);
                                    return result;
                                }
                            case "insert":{
                                return addOne(statementId,args);
                            }
                            case "update":{
                                return update(statementId,args);
                            }
                            case "delete":{
                                return delete(statementId,args);
                            }

                        }
                        return null;
                    }
                });

        return (T) proxyInstance;
    }


}
