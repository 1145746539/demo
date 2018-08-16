package com.yc.dingcan.biz;

public class BizException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BizException() {
		super();
	}

	public BizException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public BizException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BizException(String arg0) {
		super(arg0);
	}

	public BizException(Throwable arg0) {
		super(arg0);
	}
	
	

}
