package cn.qm.exception;

/**
 * 注解未定义异常
 * 
 * @author QM
 *
 */
public class EventNotRegisterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	public EventNotRegisterException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param detailMessage
	 * @param throwable
	 */
	public EventNotRegisterException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	/**
	 * 构造方法
	 * 
	 * @param detailMessage
	 */
	public EventNotRegisterException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * 构造方法
	 * 
	 * @param throwable
	 */
	public EventNotRegisterException(Throwable throwable) {
		super(throwable);
	}

}
