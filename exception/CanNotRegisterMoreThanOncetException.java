package cn.qm.exception;

/**
 * 注解未定义异常
 * 
 * @author QM
 *
 */
public class CanNotRegisterMoreThanOncetException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造方法
	 */
	public CanNotRegisterMoreThanOncetException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param detailMessage
	 * @param throwable
	 */
	public CanNotRegisterMoreThanOncetException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
	}

	/**
	 * 构造方法
	 * 
	 * @param detailMessage
	 */
	public CanNotRegisterMoreThanOncetException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * 构造方法
	 * 
	 * @param throwable
	 */
	public CanNotRegisterMoreThanOncetException(Throwable throwable) {
		super(throwable);
	}

}
