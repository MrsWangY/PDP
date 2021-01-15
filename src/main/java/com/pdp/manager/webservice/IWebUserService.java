package com.pdp.manager.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService(name = "WebUserService",
targetNamespace="http://webservice.pdp.com/")
public interface IWebUserService {
	
	/**
	 * 验证处方
	 * @param jsonData 含处方及药品信息的json字符串
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	public String verifyRecipel(@WebParam(name = "jsonData") String jsonData) throws Exception;

	/**
	 * 医师强制开具处方，插入处方记录（更新标记为有效处方）
	 * @param orgOode 各医院机构编号
	 * @param recipeNo 处方号
	 * @param reason 强制开药理由
	 * @return 
	 * @throws Exception
	 */
	@WebMethod
	public String insertRecipel(@WebParam(name = "orgCode") String orgCode,
			@WebParam(name = "recipeNo") String recipeNo,
			@WebParam(name = "reason") String reason) throws Exception;
	
	/**
	 * 更新处方记录的取药相关信息
	 * @param orgCode 各医院机构编号
	 * @param recipeNo 处方号
	 * @param recipelDispensingDoctNo 处方发药药剂师编号
	 * @param recipelDispensingDoctName 处方发药药剂师签名
	 * @param recipelDispensingDate 发药时间
	 * @throws Exception
	 */
	@WebMethod
	public String updateDispensingInfo(@WebParam(name = "orgCode") String orgCode,
			@WebParam(name = "recipeNo") String recipeNo,
			@WebParam(name = "recipelDispensingDoctNo") String recipelDispensingDoctNo,
			@WebParam(name = "recipelDispensingDoctName") String recipelDispensingDoctName,
			@WebParam(name = "recipelDispensingDate") String recipelDispensingDate
			) throws Exception;
}
