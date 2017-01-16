package cn.qm.exception;

/**
 * 注解未定义异常
 * 
 * @author QM
 * 
 */
public class AnnotationDoNotExistException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1802263138291518953L;

	/**
	 * 构造方法
	 */
	public AnnotationDoNotExistException() {
		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param detailMessage
	 * @param throwable
	 */
	// public AnnotationDoNotExistException(String detailMessage, Throwable
	// throwable) {
	// super(detailMessage, throwable);
	// }

	/**
	 * 构造方法
	 * 
	 * @param detailMessage
	 */
	public AnnotationDoNotExistException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * 构造方法
	 * 
	 * @param throwable
	 */
	// public AnnotationDoNotExistException(Throwable throwable) {
	// super(throwable);
	// }

}
