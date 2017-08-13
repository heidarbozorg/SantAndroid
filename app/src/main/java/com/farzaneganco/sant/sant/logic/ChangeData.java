package com.farzaneganco.sant.sant.logic;

public class ChangeData {
	public static String pad(int c) {
		return (pad(c, 2));
	}
	
	public static String pad(int c, int len) {
		String rst = String.valueOf(c);
		while (rst.length() < len){
			rst = "0" + rst;
		}
		return (rst);
	}
}
