package homework.three;

public class GroupNotFoundException extends Exception {
	private String message;

	public GroupNotFoundException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage(){
		return message;
	}	
}
