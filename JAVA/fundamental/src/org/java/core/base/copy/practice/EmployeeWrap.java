package org.java.core.base.copy.practice;

public class EmployeeWrap extends Employee implements Cloneable {

	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String t) {
		this.title = t;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}
}
