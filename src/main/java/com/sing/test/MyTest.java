package com.sing.test;

import com.sing.bean.Emp;
import com.sing.mapper.EmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author songbo
 * @since 2022-04-16
 */
public class MyTest {

  public static void main(String[] args) {
    //根据全局配置文件创建出SqlSessionFactory
    // SqlSessionFactory:负责创建SqlSession对象的工厂
    // SqlSession:表示跟数据库建立的一次会话
    String resource = "mybatis-config.xml";
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(resource);
    } catch (IOException e) {
      e.printStackTrace();
    }
    SqlSession sqlSession = null;
    try {
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
      // 获取数据库的会话
      sqlSession = sqlSessionFactory.openSession();
      Emp emp = null;
      // 获取要调用的接口类
      EmpMapper empMapper = sqlSession.getMapper(EmpMapper.class);
      // 调用方法开始执行
      emp = empMapper.selectEmpByEmpno(9689);
      System.out.println(emp);
    } finally {
      if (sqlSession != null) {
        sqlSession.close();
      }
    }


  }
}
