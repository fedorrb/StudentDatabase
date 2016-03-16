package homework.three;

import java.util.Comparator;

public class SortStudentByName implements Comparator<Student> {

	public int compare(Student o1, Student o2) {
		String str1 = o1.getName();
		String str2 = o2.getName();
		return str1.compareTo(str2);
	}
}
