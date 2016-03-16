package homework.three;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.GregorianCalendar;

public class Student extends Human implements Comparable<Student>, Serializable{
	private static final long serialVersionUID = 1L;
	private String institution;
	private String group;
	private double averageRating;
	
	public Student(String lastName, String name, String middleName, GregorianCalendar birthDate, Sex sex,
			String institution, String group, double averageRating) {
		super(lastName, name, middleName, birthDate, sex);
		this.institution = institution;
		this.group = group;
		this.averageRating = averageRating;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	@Override
	public String toString() {
		SimpleDateFormat stf = new SimpleDateFormat("dd MMMM yyyy");
		String birthDateStr = stf.format(getBirthDate().getTime());
		StringBuilder sb = new StringBuilder();
		sb.append("Student [name is " + getLastName() + " " + getName() + " " + getMiddleName() + ",\n");
		sb.append("birth date = " + birthDateStr + ", age = " + getAge() + ", sex = " + getSex() + "\n");
		sb.append("institution = " + institution + ", group = " + group + ", averageRating = " + averageRating + "]");
		return sb.toString();		
	}
	/**
	 * 
	 * @return formatted string for saving to file
	 */
	public String toStringWithSeparator(){
		SimpleDateFormat stf = new SimpleDateFormat("dd/MM/yyyy");
		String birthDateStr = stf.format(getBirthDate().getTime());		
		StringBuilder sb = new StringBuilder();
		sb.append(getLastName() + "|" + getName() + "|" + getMiddleName() + "|");
		sb.append(birthDateStr + "|" + getSex() + "|");
		sb.append(institution + "|" + group + "|" + averageRating);
		return sb.toString();
	}

	@Override
	public int compareTo(Student o) {
		if(o == null)
			return -1;
		Student student = (Student) o;
		return this.getLastName().compareToIgnoreCase(student.getLastName());		
	}
	
	public static Comparator<Student> StudentNameComparator = new Comparator<Student>() {
		@Override
		public int compare(Student student1, Student student2) {
			String studentName1 = student1.getLastName().toUpperCase();
			String studentName2 = student2.getLastName().toUpperCase();
			return studentName1.compareTo(studentName2);
		}
	};

}
