package homework.three;

import java.io.*;
import java.util.*;

/**
 * @author Fedorchuk Ruslan
 */
public class Test {
	private Group group;
	private Student[] students = new Student[6];

	public Test(Group group) {
		super();
		this.group = group;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * 
	 */
	public void testAddStudentFromConsole() {
		System.out.println("--------- testAddStudentFromConsole --------------");
		group.addStudentConsole();
		System.out.println(group);
	}

	/**
	 * 
	 */
	public void testAddStudentsInGroup() {
		testFillStudentsArray();
		System.out.println("------------- testAddStudentsInGroup ----------------");
		try {
			for (Student student : students) {
				group.addStudent(student);
			}
			// add same students for test GroupFullException
			for (Student student : students) {
				group.addStudent(student);
			}
		} catch (GroupFullException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(group);
	}

	/**
	 * 
	 */
	private void testFillStudentsArray() {
		students[0] = new Student("Sidorov", "Sidor", "Sidorovich", new GregorianCalendar(1991, Calendar.FEBRUARY, 2),
				Sex.MALE, "KPI", "FEA", 3.0);
		students[1] = new Student("Ivanova", "Anna", "Ivanovna", new GregorianCalendar(1992, Calendar.MAY, 20),
				Sex.FEMALE, "KPI", "FEA", 4.0);
		students[2] = new Student("Fedorov", "Fedor", "Fedorovich", new GregorianCalendar(1991, Calendar.AUGUST, 15),
				Sex.MALE, "KPI", "FEA", 4.5);
		students[3] = new Student("Bondarenko", "Lesya", "Petrovna",
				new GregorianCalendar(1989, Calendar.SEPTEMBER, 11), Sex.FEMALE, "KPI", "FEA", 5.0);
		students[4] = new Student("Pogrebniak", "Nikolay", "Nikolaevich",
				new GregorianCalendar(1988, Calendar.OCTOBER, 12), Sex.MALE, "KPI", "FEA", 2.5);
		students[5] = new Student("Avakov", "Vasiliy", "Petrovich", new GregorianCalendar(1987, Calendar.OCTOBER, 5),
				Sex.MALE, "KPI", "FEA", 1.0);
	}

	/**
	 * find same students and delete them
	 */
	public void testFindAndDeleteStudents() {
		System.out.println("---------- testFindAndDeleteStudents ---------------");
		Student studentToFind;
		for (int i = group.getSIZE_GROUP() - 1; i >= 0; i--) {
			studentToFind = group.getStudent(i);
			if (studentToFind == null)
				continue;
			ArrayList<Student> foundStudent = group.findStudent(studentToFind.getLastName());
			if (foundStudent != null) {
				System.out.println("Found the next student(s):");
				for (Student student : foundStudent) {
					System.out.println(student);
				}
				if (foundStudent.size() > 1) {
					System.out.println("And now will delete last one.");
					group.delStudent(i);
				}
			} else {
				System.out.println("Nobody Found.");
			}
			System.out.println("-----");
		}
		System.out.println(group);
	}

	public void testFindStudent(String name) {
		System.out.println("--------------- testFindStudent -------------------");
		ArrayList<Student> foundStudent = group.findStudent(name);
		if (foundStudent != null) {
			System.out.println("Found the next student(s):");
			for (Student student : foundStudent) {
				System.out.println(student);
			}
		} else {
			System.out.println("Nobody Found.");
		}
	}

	public void testSortStudentsByFirstName() {
		System.out.println("---------- sort by name (second column) -----------");
		group.sortGroupByName(); // second column
		System.out.println(group);
	}

	/**
	 * sorting array containing null values in the middle and the end of array
	 */
	public void testSortStudentsByLastName() {
		System.out.println("--------- testSortStudentsByLastname --------------");
		group.delStudent(3);// null within the array
		group.sortGroup();
		System.out.println(group);
	}

	public void testSortStudentsByRating() {
		System.out.println("--------------- sort by rating ---------------------");
		group.sortGroupByRating();
		System.out.println(group);
	}
	
	public void testSaveGroup(){
		group.saveGroupToFile();
	}
	
	public void testLoadGroup(){
		group.loadGroupFromFile();
	}	
	
	public void testPrintGroup(){
		System.out.println("Group size = " + group.getSIZE_GROUP());
		System.out.println(group);
	}
	
	public void testClearGroup(){
		group.clearGroup();
		System.out.println("Group is empty.");
	}
	
	public void serializeGroupToFile(){
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(group.getGroupName() + ".ser"))){
			oos.writeObject(group);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deserializeGroupFromFile(){
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(group.getGroupName() + ".ser"))){
			group = (Group) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}


}
