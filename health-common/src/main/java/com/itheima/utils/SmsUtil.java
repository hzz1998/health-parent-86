package com.itheima.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

public class SmsUtil {

    public static void sendSmsCode(String PhoneNumbers,String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "LTAI4FxnPS2HiRsq4zGoFjdj",//替换成你的
                "9moVloUCJITv26znvxflUXsmVMybeJ");//替换成你的
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");


        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignName", "传智播客学生短信验证");
        request.putQueryParameter("TemplateCode", "SMS_177251088");

        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        JSONObject obj = new JSONObject();
        obj.put("code",code);
        request.putQueryParameter("TemplateParam", obj.toJSONString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
