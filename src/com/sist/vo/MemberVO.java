package com.sist.vo;
/*
 ID                                        NOT NULL VARCHAR2(20)
 PWD                                       NOT NULL VARCHAR2(10)
 NAME                                      NOT NULL VARCHAR2(51)
 SEX                                                VARCHAR2(6)
 POST                                      NOT NULL VARCHAR2(7)
 ADDR1                                     NOT NULL VARCHAR2(200)
 ADDR2                                              VARCHAR2(200)
 PHONE                                              VARCHAR2(14)
 CONTENT                                            CLOB
 ISADMIN                                            CHAR(1)
 REGDATE                                            DATE
 */

import java.util.Date;

import lombok.Data;
@Data
public class MemberVO {
   private String id,pwd,sex,name,post,addr1,addr2,
           phone,content,isadmin,dbday,msg,grade;
   private Date regdate;
}