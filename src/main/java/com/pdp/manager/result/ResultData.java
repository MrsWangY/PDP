package com.pdp.manager.result;

 


public class ResultData<T> extends Result {

	public ResultData(String msgCode, String message) {
		super(msgCode, message);
	}



	public ResultData() {
		 
	}

	public static   ResultData   genSuccessResultData(Object data) {
		Result r1 = Result.genSuccessResult();
		ResultData resultData = new ResultData(r1.getMsgCode(),r1.getMessage());
		return resultData;
	}
	

}
