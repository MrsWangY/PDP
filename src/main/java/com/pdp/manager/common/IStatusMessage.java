package com.pdp.manager.common;

/**
 * @Title: IStatusMessage
 * @Description: 响应状态信息
 * @author: LIXr
 * @version: 1.0
 */
public interface IStatusMessage {

    String getCode();

    String getMessage();

    public enum SystemStatus implements IStatusMessage{

        SUCCESS("200","SUCCESS"), //请求成功
        NO_FOUND("404","NO_FOUND"),	   //
        ERROR("500","ERROR"),	   //请求失败 --系统错误，请联系技术支持方
        NO_MK("300","NO_MK"),	   //验证失败 -- 缺少麻卡记录，联系管理员维护麻卡记录，不可继续开具处方
        FAIL_ORG_CF("302","FAIL_ORG_CF"),	   //验证失败 -- 重复开药,返回麻卡归属地供药机构名称，不能在此医疗机构开药
        FAIL_NO_DONE_CF("303","FAIL_NO_DONE_CF"),	   //验证失败 -- 最近处方记录，存在未取药的处方，不能重复开药
        FAIL_DRUG_CF("304","FAIL_DRUG_CF"),	   //验证失败 -- 最近处方记录，存在未使用完的药品，同一药品不能重复开药，返回值中的
        FAIL_DRUG_CLASS_CF("305","FAIL_DRUG_USE_CF"),	//验证失败 -- 最近处方记录，存在未使用完的药品，同药效的药品不能重复开药
        FAIL_CCYL("308","FAIL_CCYL"),	   //验证失败 -- 有药品超出规定用量
        NO_TOKEN("9999","NO_TOKEN"), //NO_TOKEN，或TOKEN已失效
        PARAM_ERROR("1002","PARAM_ERROR"), //请求参数有误
        
        SUCCESS_MATCH("1003","SUCCESS_MATCH"), //表示成功匹配
        NO_LOGIN("1100","NO_LOGIN"), //未登录
        MANY_LOGINS("1101","MANY_LOGINS"), //多用户在线（踢出用户）
        UPDATE("1102","UPDATE"), //用户信息或权限已更新（退出重新登录）
        LOCK("1111","LOCK"); //用户已锁定
        private String code;
        private String message;
        
        private SystemStatus(String code,String message){
            this.code = code;
            this.message = message;
        }

        public String getCode(){
            return this.code;
        }

        public String getMessage(){
            return this.message;
        }
    }

}
