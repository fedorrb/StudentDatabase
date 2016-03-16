package homework.three;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int SIZE_GROUP = 10;
	private String groupName;
	private ArrayList<Student> group = new ArrayList<Student>();

	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}

	public void addStudent(Student student) throws GroupFullException {
		if (group.size() >= SIZE_GROUP) {
			throw new GroupFullException();
		}
		if (student != null)
			group.add(student);
	}

	public void saveGroupToFile() {
		if (!group.isEmpty()) {
			try (PrintWriter fileOut = new PrintWriter(getGroupName() + ".stud", "UTF-8")) {
				for (Student student : group) {
					fileOut.println(student.toStringWithSeparator());
				}
				fileOut.flush();
			} catch (FileNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (UnsupportedEncodingException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void loadGroupFromFile() {
		try (Scanner fileIn = new Scanner(new FileInputStream((getGroupName() + ".stud")), "UTF-8")) {
			while (fileIn.hasNextLine()) {
				String line = fileIn.nextLine();
				String[] data = line.split("\\|");
				try {
					checkData(data);
					addStudentToGroup(data);
				} catch (WrongStudentException e) {
					System.out.println("Wrong format:");
					System.out.println(e.getMessage());
				} catch (GroupFullException e) {
					System.out.println(e.getMessage());
					break;
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public void addStudentConsole() {
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Write student data, 8 values:");
			System.out.println("FirstName LastName MiddleName dd/mm/yyyy male(female) institution group averageRating");
			String[] data = input.readLine().split(" ");
			checkData(data);
			addStudentToGroup(data);
		} catch (IOException e) {
			System.out.println("Input problem.");
		} catch (GroupFullException e) {
			System.out.println(e.getMessage());
		} catch (WrongStudentException e) {
			System.out.println("Wrong format:");
			System.out.println(e.getMessage());
		}
	}

	private void addStudentToGroup(String[] data) throws GroupFullException {
		String dayMonYear[] = data[3].trim().split("/");
		int day = 0;
		int mon = 0;
		int year = 0;
		day = Integer.parseInt(dayMonYear[0]);
		mon = Integer.parseInt(dayMonYear[1]) - 1; // month 0-11 but entered
													// 1-12
		year = Integer.parseInt(dayMonYear[2]);
		GregorianCalendar dtBirth = new GregorianCalendar(year, mon, day);
		addStudent(new Student(data[0].trim(), data[1].trim(), data[2].trim(), dtBirth,
				(data[4].trim().equalsIgnoreCase("male") ? Sex.MALE : Sex.FEMALE), data[5].trim(), data[6].trim(),
				Double.parseDouble(data[7].trim())));
	}

	private void checkData(String[] data) throws WrongStudentException {
		if (data.length < 8) {
			throw new WrongStudentException("In the row should be eight parameters.");
		}
		for (int i = 0; i < 3; i++) {
			if (Pattern.matches("^[a-zA-Z]+", data[i]) == false)
				throw new WrongStudentException("Name must contain only letters.");
		}
		try {
			if (Double.parseDouble(data[7].trim()) < 0 || Double.parseDouble(data[7].trim()) > 12) {
				throw new WrongStudentException("Average rating should be in the range from 0 to 12.");
			}
		} catch (NumberFormatException e) {
			throw new WrongStudentException("Average rating is not digit.");
		}
		String dayMonYear[] = data[3].trim().split("/");
		if (dayMonYear.length != 3)
			throw new WrongStudentException("Wrong date format.");
		int day = 0;
		int mon = 0;
		int year = 0;
		try {
			day = Integer.parseInt(dayMonYear[0]);
			mon = Integer.parseInt(dayMonYear[1]) - 1; // month 0-11 but entered
														// 1-12
			year = Integer.parseInt(dayMonYear[2]);
		} catch (NumberFormatException e) {
			throw new WrongStudentException("Wrong date format.");
		}
		GregorianCalendar now = (GregorianCalendar) Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());
		if (now.get(Calendar.YEAR) - year < 14) {
			throw new WrongStudentException("Wrong date format. Student is too young.");
		}
		if (now.get(Calendar.YEAR) - year > 65) {
			throw new WrongStudentException("Wrong date format. Student is too old.");
		}
		GregorianCalendar dtBirth = new GregorianCalendar(year, mon, day);
		if (day != dtBirth.get(Calendar.DAY_OF_MONTH) || mon != dtBirth.get(Calendar.MONTH)
				|| year != dtBirth.get(Calendar.YEAR)) {
			throw new WrongStudentException("Wrong date format.");
		}
	}

	public void delStudent(int index) {
		if (index < 0 || index >= group.size()) {
			System.out.println("Wrong index.");
			return;
		}
		group.remove(index);
	}

	public ArrayList<Student> findStudent(String lastName) {
		if (lastName == null) {
			System.out.println("The student's name can not be null.");
			return null;
		}
		ArrayList<Student> array = new ArrayList<Student>();
		for (Student student : group) {
			if (student.getLastName().equalsIgnoreCase(lastName))
				array.add(student);
		}
		if (array.isEmpty()) {
			return null;
		} else {
			return array;
		}
	}

	public String getGroupName() {
		return groupName;
	}

	public int getSIZE_GROUP() {
		return group.size();
	}

	public Student getStudent(int index) {
		if (index < 0 || index >= group.size()) {
			return null;
		} else
			return group.get(index);
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void sortGroup() {
		group.sort(Student.StudentNameComparator);
	}

	public void sortGroupByName() {
		group.sort(new SortStudentByName());
	}

	public void sortGroupByRating() {
		group.sort(new SortStudentByRating());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Student student : group) {
			sb.append((i + 1) + ") " + student + "\n");
			i++;
		}
		return sb.toString();
	}

	public void clearGroup() {
		group.clear();
	}

}
