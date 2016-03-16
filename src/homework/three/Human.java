package homework.three;

import java.util.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Human implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String lastName;
	private String middleName;
	private GregorianCalendar birthDate;
	private Sex sex;
	private int age;

	public Human(String lastName, String name, String middleName, GregorianCalendar birthDate, Sex sex) {
		super();
		this.lastName = lastName;
		this.name = name;
		this.middleName = middleName;
		this.birthDate = birthDate;
		this.sex = sex;
	}

	private void calcAge() {
		GregorianCalendar now = (GregorianCalendar) Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		age = OperationCalendar.getYearsBetweenDates(now, birthDate);
	}

	public int getAge() {
		calcAge();
		return age;
	}

	public GregorianCalendar getBirthDate() {
		return birthDate;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getName() {
		return name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		SimpleDateFormat stf = new SimpleDateFormat("dd MMMM yyyy");
		String birthDateStr = stf.format(birthDate.getTime());

		StringBuilder sb = new StringBuilder();
		sb.append("Human [name is " + lastName + " " + name + " " + middleName + ",\n");
		sb.append("birth date = " + birthDateStr + ", age = " + getAge() + ", sex = " + sex + "]");
		return sb.toString();
	}

}
