package com.chy.sqlSession;


import com.chy.config.BoundSql;
import com.chy.pojo.Configuration;
import com.chy.pojo.MappedStatement;
import com.chy.utils.GenericTokenParser;
import com.chy.utils.ParameterMapping;
import com.chy.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class simpleExecutor implements  Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, SQLException, ClassNotFoundException, IntrospectionException, InvocationTargetException {

        // 1. 注册驱动，获取连接
        Connection connection = configuration.getDataSource().getConnection();

        // 2. 获取sql语句 : select * from user where id = #{id} and username = #{username}
        /**
         *  转换sql语句 : select * from user where id = ? and username = ?
         *  同还需要对#{}里面的值进行解析存储
         */
        String sql = mappedStatement.getSql();

        //转换sql
        /**
         * 要完成对#{}解析工作：1.将#{}使用？代替 2.解析出#{}里面的值进行存储
         */
        BoundSql boundSql = getBoundSql(sql);

        // 3. 获取预处理对象：preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());

        // 4. 设置参数
        String paramterType = mappedStatement.getParamterType();
        String mapprtType = mappedStatement.getMapperType();
        Class<?> paramtertypeClass = getClassType(paramterType);
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String name = parameterMapping.getContent();
            //反射
            Field declaredField = paramtertypeClass.getDeclaredField(name);
            declaredField.setAccessible(true);
            Object o = declaredField.get(params[0]);

            preparedStatement.setObject(i+1,o);
        }
        // 5. 执行sql
        ArrayList<Object> results = new ArrayList<>();
        if(mapprtType.equals("select")){
            ResultSet resultSet = preparedStatement.executeQuery();
            Class<?> resultType = mappedStatement.getResultType();

            // 6. 封装返回结果集
            while (resultSet.next()){
                Object resultInstance = resultType.newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount() ; i++) {
                    //字段名
                    String columnName = metaData.getColumnName(i);
                    //字段值
                    Object value = resultSet.getObject(columnName);

                    //使用反射或内省，根据数据库与实体的对应关系，完成封装
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultType);
                    Method writeMethod = propertyDescriptor.getWriteMethod();
                    writeMethod.invoke(resultInstance,value);

                }
                results.add(resultInstance);
            }
        }else if(mapprtType.equals("update")||mapprtType.equals("insert")||mapprtType.equals("delete")){
            int i = preparedStatement.executeUpdate();
        }


        return (List<E>)results;

    }

    private BoundSql getBoundSql(String sql){

        //ParameterMappingTokenHandler标记处理类：配置标记解析器来完成对占位符的解析工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parse = genericTokenParser.parse(sql);
        //#{}解析出来的参数名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parse,parameterMappings);



        return boundSql;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if(paramterType!=null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }
        return null;

    }

}
