/*
 * 功能：短信来了短信发送示例
 * 版本：1.3
 * 日期：2016-07-01
 * 说明：
 * 以下代码只是为了方便客户测试而提供的样例代码，客户可以根据自己网站的需要，按照技术文档自行编写,并非一定要使用该代码。
 * 该代码仅供学习和研究使用，只是提供一个参考。
 * 网址：http://www.laidx.com
 */
package com.gome.manager.sms;
import java.net.URLEncoder;
import java.util.HashMap;

public class SendSMSDemo {

	public static String SendSMS(String mobile,String six) {
		String address = "120.24.161.220";//远程地址：不带http://
		int port = 8800;//远程端口
		String account = "E5A70E09EDE541D28799D9E16B7178FC";//账户
		String token = "499e32a5008c4ce29ad6b059f6a5b58a";//token
		String body = "【嘉林药业】您的验证码为："+six+"，如非本人操作，请忽略。";//短信内容
		short rType = 0;//响应类型  0 json类型，1 xml类型
        String extno = "";//扩展号 可选
		KXTSmsSDK kxtsms = new KXTSmsSDK();
		kxtsms.init(address, port, account, token);
		try
		{
			body = URLEncoder.encode(body,"UTF-8");//URL编码 UTF-8方式
		}
		catch (Exception e) {
			
		}
		String result = kxtsms.send(mobile,body,rType,extno);
		HashMap<String, Object> hashMap = null;
		if(rType == 0)
		{
			//json
			hashMap = CommonUtils.jsonToMap(result);
		}
		if(rType == 1)
		{
			//xml
			hashMap = CommonUtils.xmlToMap(result);
		}
		if(hashMap != null)
		{
			//写自己的业务逻辑代码
			//hashMap.get("Code");
		}
		return result;
	}

}
