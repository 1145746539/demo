package com.yc.dingcan.vo;

public class Result {

	public static final String SUCCESS = "1";

	public static final String FAILURE = "0";

	// 结果码
	private String code;

	// 返回的数据
	private Object data;

	public Result(String code, Object data) {
		this.code = code;
		this.data = data;
	}

	// 创建成功的结果对象
	public static Result getSuccess(Object data) {
		return new Result(SUCCESS, data);
	}

	// 创建失败的结果对象
	public static Result getFailure(Object data) {
		return new Result(FAILURE, data);
	}

	public String getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

}
