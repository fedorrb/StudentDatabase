package homework.three;

import java.util.Comparator;

public class SortStudentByRating implements Comparator<Student> {

	public int compare(Student o1, Student o2) {
		if(o1.getAverageRating() > o2.getAverageRating())
			return -1;
		else if(o1.getAverageRating() < o2.getAverageRating())
			return 1;		
		return 0;
	}
}