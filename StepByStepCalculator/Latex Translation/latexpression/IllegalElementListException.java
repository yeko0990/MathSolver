package latexpression;

public class IllegalElementListException extends RuntimeException {
	private static final long serialVersionUID = -6484367566731054077L;

	public IllegalElementListException() {
		
	}
	
	public IllegalElementListException(String message) {
		super(message);
	}

	public IllegalElementListException(Throwable cause) {
		super(cause);
	}

	public IllegalElementListException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalElementListException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
