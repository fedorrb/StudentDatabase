package homework.three;

public class GroupFullException extends Exception {
	@Override
	public String getMessage(){
		return "Group is full. Come next year.";
	}
}
