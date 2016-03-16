package homework.three;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Database implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Group> dataBase = new ArrayList<Group>();

	private void addGroup() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a new name for the group...");
		String groupName = in.nextLine().trim();
		try {
			addGroup(groupName);
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
		}
	}

	private void addGroup(String name) throws InvalidInputException {
		if (name != null && name.length() > 0) {
			try {
				findGroup(name);
				throw new InvalidInputException("Error. Group '" + name + "' already exists.");
			} catch (GroupNotFoundException e) {
				dataBase.add(new Group(name));
			}
		} else
			throw new InvalidInputException("Error. Wrong group name.");
	}

	private void addStudentToGroup() {
		Group group = new Group("noname");
		String groupName;
		Student student;
		try {
			group.addStudentConsole();
			student = group.getStudent(0);
			if(student == null)
				throw new WrongStudentException("");
			groupName = student.getGroup();
			group = findGroup(groupName);
			try {
				group.addStudent(student);
			} catch (GroupFullException e) {
				System.out.println(e.getMessage());
			}
		} catch (GroupNotFoundException | WrongStudentException e) {
			System.out.println(e.getMessage() + " Student didn't  added.");
		}
	}

	private Group findGroup(String groupName) throws GroupNotFoundException {
		if (dataBase.isEmpty())
			throw new GroupNotFoundException("The database is empty.");
		else {
			if (groupName != null && groupName.length() > 0) {
				for (Group group : dataBase) {
					if (groupName.equalsIgnoreCase(group.getGroupName())) {
						return group;
					}
				}
			}
		}
		throw new GroupNotFoundException("Group not found.");
	}

	public void saveDataBase() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.ser"))) {
			// oos.writeObject(dataBase);
			oos.writeInt(dataBase.size());
			for (Group group : dataBase) {
				oos.writeObject(group);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadDataBase() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database.ser"))) {
			// dataBase = (ArrayList<Group>) ois.readObject();
			int number = ois.readInt();
			for (int i = 0; i < number; i++) {
				dataBase.add((Group) ois.readObject());
			}
		} catch (FileNotFoundException e) {
			// to do nothing
			return;
		} catch (EOFException e) {
			return;
		} catch (ClassCastException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void workWithBase() {
		int choosen = 0;
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome.");
		boolean cycle = true;
		while (cycle) {
			System.out.println("0. Exit");
			System.out.println("1. Get a list of groups.");
			System.out.println("2. Add new group.");
			if (dataBase.size() > 0) {
				System.out.println("3. Add student to group.");
				System.out.println("4. Find student by surname.");
			}
			String input = in.nextLine();
			try {
				choosen = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Error. Enter a number from 0 to 4.");
			}
			switch (choosen) {
			case 0:
				cycle = false;
				System.out.println("Goodbye");
				break;
			case 1:
				printGroupsInDatabase();
				break;
			case 2:
				addGroup();
				break;
			case 3:
				addStudentToGroup();
				break;
			case 4:
				findStudent();
				break;
			default:
				System.out.println("Error. Enter a number from 0 to 4.");
				break;
			}
		}
	}

	private void findStudent() {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a surname...");
		String surname = in.nextLine().trim();
		if (surname.length() > 0) {
			for (Group group : dataBase) {
				ArrayList<Student> foundStudent = group.findStudent(surname);
				if (foundStudent != null) {
					System.out.println("Found the next student(s):");
					for (Student student : foundStudent) {
						System.out.println(student);
					}
					return;
				}
			}
		}
		System.out.println("Student " + surname + " not found.");
	}

	private void printGroupsInDatabase() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Group group : dataBase) {
			sb.append((i + 1) + ") " + group.getGroupName() + "\n");
			i++;
		}
		return sb.toString();
	}

}
