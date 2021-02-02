package com.chy.sqlSession;

import com.chy.config.XMLConfigBuilder;
import com.chy.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }


    public SqlSessionFactory build(InputStream input) throws DocumentException, PropertyVetoException, ClassNotFoundException {
        //使用dom4j 解析配置文件，将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigbuilder = new XMLConfigBuilder(configuration);
        Configuration configuration = xmlConfigbuilder.parseConfig(input);

        //创建sqlSessionFactory对象 工厂类：生产sqlSession：会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);

        return defaultSqlSessionFactory;
    }
}
