package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class People {
	private int id;
	private String name;
	private int age;
	private String work;
	
	public People() {
		id = -1;
		name = "";
		age = 0;
		work = "";
	}

	public People(int id, String name, int age, String work) {
		setId(id);
		setName(name);
		setAge(age);
		setWork(work);
	}		
	
	public int getID() {
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getWork() {
		return work;
	}
	
	public void setWork(String work) {
		this.work = work;
	}
	

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "People: " + name + " " + age + "   Trabalho: " + work;  
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((People) obj).getID());
	}	
}
