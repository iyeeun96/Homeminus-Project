package com.dto;

public class EmployeeDto {
   //아이디 비번 이름 직급
   private String e_id;
   private String e_pass;
   private String e_name;
   private String e_grade;
   
   public String getE_id() {
      return e_id;
   }
   public void setE_id(String e_id) {
      this.e_id = e_id;
   }
   public String getE_pass() {
      return e_pass;
   }
   public void setE_pass(String e_pass) {
      this.e_pass = e_pass;
   }
   public String getE_name() {
      return e_name;
   }
   public void setE_name(String e_name) {
      this.e_name = e_name;
   }
   public String getE_grade() {
      return e_grade;
   }
   public void setE_grade(String e_grade) {
      this.e_grade = e_grade;   
   }
   @Override
   public String toString() {
      return "아이디 = " + e_id + "\n" + "pass = " + e_pass + "\n" +
            "이름 = " + e_name + "\n" + "직급 = " + e_grade;
   }
   
   
}