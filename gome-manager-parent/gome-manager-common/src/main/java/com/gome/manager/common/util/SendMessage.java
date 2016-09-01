/**
 * @author tianyu-ds
 * @date 2014-4-10
 * @project_name test
 * @company 国美在线有限公司
 * @desc 发送对账平台信息邮件工具
 */
package com.gome.manager.common.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMessage {

	public static void main(String[] args)throws Exception{

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "本站用户名"),new NameValuePair("Key", "接口安全密码"),new NameValuePair("smsMob","手机号码"),new NameValuePair("smsText","短信内容")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode);
		for(Header h : headers)
		{
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		System.out.println(result); //打印返回消息状态
		post.releaseConnection();
	}
}
