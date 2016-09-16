package interfaces;

public class FileHandlingException extends Exception {

	private static final long serialVersionUID = 3492431818988443283L;

	public FileHandlingException() {

	}

	public FileHandlingException(String arg0) {
		super(arg0);
	}

	public FileHandlingException(Throwable arg0) {
		super(arg0);
	}

	public FileHandlingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public FileHandlingException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
