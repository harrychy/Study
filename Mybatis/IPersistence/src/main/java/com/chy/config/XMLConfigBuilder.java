package com.chy.config;

import com.chy.io.Resources;
import com.chy.pojo.Configuration;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     *
     * 该方法就是使用dom4j将配置文件解析，封装Configuration
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {

        Document document = new SAXReader().read(inputStream);
        /**
         * 拿根对象
         */
        Element rootElement = document.getRootElement();
        /**
         *  //property 标识在文件中任意位置都能被查找到
         */
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("username"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml解析
        List<Element> mapperElement = rootElement.selectNodes("//mapper");
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        for (Element element : mapperElement) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            xmlMapperBuilder.parsemapper(resourceAsSteam);
        }
        return configuration;
    }
}
