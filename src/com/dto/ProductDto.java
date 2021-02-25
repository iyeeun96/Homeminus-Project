
package com.dto;

public class ProductDto {
   private String p_kind;
   private String p_code;
   private String p_name;
   private int p_price;
   private int p_amount;
   private String p_use;
   private String p_exp;
   
   public String getP_kind() {
      return p_kind;
   }
   public void setP_kind(String p_kind) {
      this.p_kind = p_kind;
   }
   public String getP_code() {
      return p_code;
   }
   public void setP_code(String p_code) {
      this.p_code = p_code;
   }
   public String getP_name() {
      return p_name;
   }
   public void setP_name(String p_name) {
      this.p_name = p_name;
   }
   public int getP_price() {
      return p_price;
   }
   public void setP_price(int p_price) {
      this.p_price = p_price;
   }
   public String getP_use() {
      return p_use;
   }
   public void setP_use(String p_use) {
      this.p_use = p_use;
   }
   public int getP_amount() {
      return p_amount;
   }
   public void setP_amount(int p_amount) {
      this.p_amount = p_amount;
   }
   public String getP_exp() {
      return p_exp;
   }
   public void setP_exp(String p_exp) {
      this.p_exp = p_exp;
   }
   @Override
   public String toString() {
      String str ="카테고리 : " + p_kind + "\n"
            +"제품코드 : " + p_code + "\n"
            +"제품명 : " + p_name + "\n"
            +"가격 : " + p_price + "\n"
            +"수량 : " + p_amount+"\n";
      return str;
   }
}//class end