/*
 * 功能：短信来了获取短信状态示例
 * 版本：1.3
 * 日期：2016-07-01
 * 说明：
 * 以下代码只是为了方便客户测试而提供的样例代码，客户可以根据自己网站的需要，按照技术文档自行编写,并非一定要使用该代码。
 * 该代码仅供学习和研究使用，只是提供一个参考。
 * 网址：http://www.laidx.com
 */
package com.gome.manager.sms;

import java.util.HashMap;

public class SMSStatusDemo {

	public static void main(String[] args) {
		String address = "120.24.161.220";//远程地址：不带http://
		int port = 8800;//远程端口
		String account = "";//账户
		String token = "";//token
		String smsId = "";
		short rType = 1;//响应类型  0 json类型，1 xml类型
		KXTSmsSDK kxtsms = new KXTSmsSDK();
		kxtsms.init(address, port, account, token);
		String result = kxtsms.smsStatus(smsId, rType);
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
	}

}
