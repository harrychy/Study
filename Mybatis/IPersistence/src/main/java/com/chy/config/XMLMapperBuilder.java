package com.chy.config;

import com.chy.pojo.Configuration;
import com.chy.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;


    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parsemapper(InputStream resourceAsSteam) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(resourceAsSteam);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> mapperlist = rootElement.selectNodes("//select");
        for (Element element : mapperlist) {
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            String mapperType = element.attributeValue("mapperType");
            String sql = element.getTextTrim();

            Class<?> paramterTypeClass = getClassType(paramterType);
            Class<?> resultTypeClass = getClassType(resultType);

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(sql);
            mappedStatement.setMapperType(mapperType);

            String key = namespace+"."+id;

            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

        List<Element> list = rootElement.selectNodes("//insert");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            String mapperType = element.attributeValue("mapperType");
            String sql = element.getTextTrim();

            Class<?> paramterTypeClass = getClassType(paramterType);
            Class<?> resultTypeClass = getClassType(resultType);

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(sql);
            mappedStatement.setMapperType(mapperType);

            String key = namespace+"."+id;

            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

        List<Element> updatelist = rootElement.selectNodes("//update");
        for (Element element : updatelist) {
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            String mapperType = element.attributeValue("mapperType");
            String sql = element.getTextTrim();

            Class<?> paramterTypeClass = getClassType(paramterType);
            Class<?> resultTypeClass = getClassType(resultType);

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(sql);
            mappedStatement.setMapperType(mapperType);

            String key = namespace+"."+id;

            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

        List<Element> deletelist = rootElement.selectNodes("//delete");
        for (Element element : deletelist) {
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            String mapperType = element.attributeValue("mapperType");
            String sql = element.getTextTrim();

            Class<?> paramterTypeClass = getClassType(paramterType);
            Class<?> resultTypeClass = getClassType(resultType);

            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(sql);
            mappedStatement.setMapperType(mapperType);

            String key = namespace+"."+id;

            configuration.getMappedStatementMap().put(key,mappedStatement);
        }

    }

    private Class<?> getClassType(String Type) throws ClassNotFoundException {
        if(Type==null){
            return null;
        }
        Class<?> aClass = Class.forName(Type);
        return aClass;
    }


}
