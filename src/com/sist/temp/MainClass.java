package com.sist.temp;
import java.util.*;
import com.sist.dao.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        MemberDAO dao=new MemberDAO();
        List<String> list=dao.memberGetId();
        String[] grades={"BRONZE", "SILVER", "GOLD", "DIAMOND"};
        for(String id:list)
        {
        	dao.gradeInsert(id, grades[(int)(Math.random()*4)]);
        }
        System.out.println("완료");
	}

}
