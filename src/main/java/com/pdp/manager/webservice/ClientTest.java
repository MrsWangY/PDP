package com.pdp.manager.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class ClientTest {

	public static void main(String args[]) throws Exception{
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client =dcf.createClient("http://127.0.0.1:8102/services/WebUserService?wsdl");
        // invoke("方法名",参数1,参数2,参数3....);
        //这里注意如果是复杂参数的话，要保证复杂参数可以序列化
        Object[] objects=client.invoke("insertRecipel","112222","334455","哪那么多为什么");
//        Object[] objects=client.invoke("updateDispensingInfo","112222","334455","zhangsan","张三","2020-11-17 12:12:12");
        //输出调用结果
        System.out.println("-----------------");
        System.out.println(objects[0]); //格式：{"msg":"SUCCESS","code":"200"}
        System.out.println("-----------------");
    }
}
