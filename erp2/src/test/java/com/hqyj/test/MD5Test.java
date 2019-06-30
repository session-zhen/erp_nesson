package com.hqyj.test;

import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class MD5Test {

	@Test
	public void test1() {
		
		//source,需要加密的源数据
		//加盐，就是用户名
		//散列次数
		Md5Hash md5 = new Md5Hash("tangseng", "tangseng", 2);
		//加密之后的数据
		System.out.println(md5.toString());
		
	}
	
	@Test
	public void test2() {
		Date date = Calendar.getInstance().getTime();
		System.out.println(date);
	}
}
