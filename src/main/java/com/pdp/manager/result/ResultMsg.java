package com.pdp.manager.result;


/**
 * @author Administrator
 */
public  class ResultMsg {
    /**
     * 通用成功
     */
    public static final ResultMsg  SUCCESS = new ResultMsg("1000","SUCCESS");
    /**
     * 通用失败
     */
    public static final ResultMsg  FAILED = new ResultMsg("999","FAILED");
    /**
     * token验证失败
     */
    public static final ResultMsg NOTOKEN = new ResultMsg("300","NOTOKEN");





    private ResultMsg(String msgCode,String msg){
        this.msgCode = msgCode;
        this.msg = msg;
    }
    
    public ResultMsg(String msgCode,String msg,String data){
        this.msgCode = msgCode;
        this.msg = msg;
        this.data =data;
    }
    private String msgCode;
    private String msg;
    private String data;
    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
