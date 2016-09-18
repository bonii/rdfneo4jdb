package interfaces;

public class GraphDBException extends Exception {

	private static final long serialVersionUID = 3492431818988443283L;

	public GraphDBException() {

	}

	public GraphDBException(String arg0) {
		super(arg0);
	}

	public GraphDBException(Throwable arg0) {
		super(arg0);
	}

	public GraphDBException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GraphDBException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
