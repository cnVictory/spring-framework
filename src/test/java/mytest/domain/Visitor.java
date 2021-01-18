package mytest.domain;

import java.util.Objects;

/**
 * @BelongsProject: spring
 * @BelongsPackage: mytest.domain
 * @Author: Lovezly
 * @CreateTime: 2020-08-22 10:37
 * @Description:
 */
public class Visitor {

	private String name;

	private int age;

	private boolean worked;

	public Visitor() {
		System.out.println("============> My Visitor Constructed");
	}

	public Visitor(String name, int age, boolean worked) {
		this.name = name;
		this.age = age;
		this.worked = worked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isWorked() {
		return worked;
	}

	public void setWorked(boolean worked) {
		this.worked = worked;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Visitor visitor = (Visitor) o;
		return age == visitor.age &&
				worked == visitor.worked &&
				Objects.equals(name, visitor.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, age, worked);
	}
}
