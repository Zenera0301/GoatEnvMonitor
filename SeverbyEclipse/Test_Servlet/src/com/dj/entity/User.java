package com.dj.entity;
import java.io.Serializable;
public class User {

	private static final long serialVersionUID = 1L;
	private String name;
	private char sex;
	private int age;
	private double height;
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public User() {
		//TODO Auto-generated constructor stub
	}
	public User(String name,char sex,int age,double height,String password) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.height = height;
		this.password = password;		
	}
	
	@Override
	public String toString() {
		return "User [name="+name+",sex="+sex+",age="+age+",height="+height+",password="+password+"]";
	}
	
}
