package com.cetc.security.core.validate.code.sms;


import com.cetc.security.core.validate.code.ValidateCode;
import com.cetc.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.web.context.request.ServletWebRequest;

/*
腾讯云短信
 <dependency>
            <groupId>com.github.qcloudsms</groupId>
            <artifactId>qcloudsms</artifactId>
            <version>1.0.6</version>
            <scope>compile</scope>
        </dependency>
 */
public class DefaultSmsCodeSender implements SmsCodeSender {
    // 短信应用 SDK AppID
    int appid = 1400216477; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    String appkey = "942a00c5ddb5be7f7d333b16ac05c33c";
    // 短信模板 ID，需要在短信应用中申请
    //模板示例：【提醒】尊敬的商密OA系统用户{1}，您好！系统中有您的待办事项，请及时处理！
    int templateId = 342816; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    String smsSign = ""; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请
    //签名ID 213527

    @Override
    public void send(String mobile, String code) {
        System.out.println("默认短信发送接口实现:向手机"+mobile+"发送验证码："+code);
//        String[] phoneNumbers = {""+mobile+""};
//        String[] params = {""+code+""};
//        try {
//            MySender msender = new MySender(appid, appkey);
//            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
//                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
//            System.out.println(result);
//        } catch (HTTPException e) {
//            // HTTP 响应码错误
//            System.out.println("HTTP 响应码错误");
//            e.printStackTrace();
//        } catch (JSONException e) {
//            // JSON 解析错误
//            System.out.println("JSON 解析错误");
//            e.printStackTrace();
//        } catch (IOException e) {
//            // 网络 IO 错误
//            System.out.println("网络 IO 错误");
//            e.printStackTrace();
//        }
    }
}
