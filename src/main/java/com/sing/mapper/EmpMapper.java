package com.sing.mapper;

import com.sing.bean.Emp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface EmpMapper {

  @Select("select * from emp where empno = #{empno} and ename = #{ename}")
  List<Emp> selectEmpList(Integer empno, String ename);

  Emp selectEmpByEmpno(Integer empno);

}
