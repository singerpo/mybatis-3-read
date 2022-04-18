package com.sing.bean;

import java.io.Serializable;

/**
 * @author songbo
 * @since 2022-04-16
 */
public class Emp implements Serializable {
  private Integer empno;
  private String ename;


  public Integer getEmpno() {
    return empno;
  }

  public void setEmpno(Integer empno) {
    this.empno = empno;
  }

  public String getEname() {
    return ename;
  }

  public void setEname(String ename) {
    this.ename = ename;
  }

  @Override
  public String toString() {
    return "Emp{" +
      "empno=" + empno +
      ", ename='" + ename + '\'' +
      '}';
  }
}
