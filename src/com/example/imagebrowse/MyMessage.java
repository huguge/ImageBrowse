package com.example.imagebrowse;

import java.util.ArrayList;
/**
 * 反馈的信息
 * @author ZhouXiaoNa
 *
 */
public class MyMessage {
	
	public int code;//反馈状态
	public String msg;//反馈信息
	
	public ArrayList<MyBean> list;//返回的内容
	
	@Override
	public String toString() {
		return "MyMessage [code=" + code + ", msg=" + msg + ", list=" + list + "]";
	}

}
