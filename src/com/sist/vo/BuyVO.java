package com.sist.vo;

import java.util.*;

import lombok.Data;
@Data
public class BuyVO {

   private int no,type,gno,account,price;
   private String id,dbday;
   private Date regdate;
   private GoodsVO gvo=new GoodsVO();
}
