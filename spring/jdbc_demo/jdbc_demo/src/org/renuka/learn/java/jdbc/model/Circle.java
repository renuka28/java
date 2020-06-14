package org.renuka.learn.java.jdbc.model;

public class Circle {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private int id;
	private String name;
	public Circle(int id, String name) {
		setId(id);
		setName(name);
	}
}
