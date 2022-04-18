package com.sing;

import com.sing.mapper.EmpMapper;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Mybatis实现的猜想
 * @author songbo
 * @since 2022-04-16
 */
public class MyMybatis {

  public static void main(String[] args) {
    EmpMapper empMapper = (EmpMapper) Proxy.newProxyInstance(MyMybatis.class.getClassLoader(), new Class[]{EmpMapper.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 通过参数来完成替换功能，因此需要先去解析参数
        Map<String, Object> argMap = parseArgs(method, args);
        // 获取方法上的注解
        Select select = method.getAnnotation(Select.class);
        if (select != null) {
          String sql = select.value()[0];
          sql = parseSql2(sql, argMap);
          System.out.println("方法上的sql：" + sql);
        }
        // 数据库操作
        return null;
      }
    });
    empMapper.selectEmpList(9689, "zhangsan");
  }

  public static String parseSql(String sql, Map<String, Object> argMap) {
    for (Map.Entry entry : argMap.entrySet()) {
      String value = entry.getValue().toString();
      sql = sql.replace("#{" + entry.getKey() + "}", entry.getValue().toString());
    }
    return sql;
  }

  public static String parseSql2(String sql, Map<String, Object> argMap) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < sql.length(); i++) {
      char c = sql.charAt(i);
      if (c == '#') {
        int index = i + 1;
        char nextChar = sql.charAt(index);
        if (nextChar != '{') {
          throw new RuntimeException("sql语句错误，#后缺少{");
        }
        // 替换参数值
        StringBuilder argBuilder = new StringBuilder();
        index++;
        for (; index < sql.length(); index++) {
          char c2 = sql.charAt(index);
          if (c2 != '}') {
            argBuilder.append(c2);
          } else {
            break;
          }
        }
        i = index;
        String argName = argBuilder.toString();
        Object value = argMap.get(argName);
        builder.append(value.toString());
        continue;
      }
      builder.append(c);
    }
    return builder.toString();
  }

  /**
   * 解析方法参数、参数值
   *
   * @param method 方法
   * @param args   参数值数组
   * @return
   */
  public static Map<String, Object> parseArgs(Method method, Object[] args) {
    Map<String, Object> map = new HashMap<>();
    // 获取方法的参数
    Parameter[] parameters = method.getParameters();
    final int[] index = {0};
    Arrays.asList(parameters).forEach(parameter -> {
      String name = parameter.getName();
      map.put(name, String.class.isAssignableFrom(parameter.getType()) ? "'" + args[index[0]] + "'" : args[index[0]]);
      index[0]++;
    });
    return map;
  }
}
