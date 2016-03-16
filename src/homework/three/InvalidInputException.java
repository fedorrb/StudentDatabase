package homework.three;

public class InvalidInputException extends Exception{
	private String message;

	public InvalidInputException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage(){
		return message;
	}
}
