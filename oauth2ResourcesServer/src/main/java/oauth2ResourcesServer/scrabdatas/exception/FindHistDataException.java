package oauth2ResourcesServer.scrabdatas.exception;

/**
 * 自定義FindHistDataExceptionn
 */
public class FindHistDataException extends Exception{

	/**
	 * 序列號
	 */
	private static final long serialVersionUID = 1L;
	
	
	public FindHistDataException(String message) {
		super(message);
	}
	
	public FindHistDataException(Throwable cases) {
		super(cases);
	}

}
