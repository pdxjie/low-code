package com.pdx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdx.utils.ParseXml;
import com.pdx.utils.ParseXmlForWx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @Author: 派大星
 * @Date: 2023/01/13 2023/1/13
 * @Description:
 */
@Slf4j
@RestController
public class WeChatController {
    // 记录所有登录人的状态
    Map<String, Boolean> loginObj = new HashMap<String, Boolean>();
    // 公众号数据
    // 以下四个变量，是你需要改成你自己的
    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.appsecret}")
    private String appsecret;
    @Value("${wx.token}")
    private String token;

    // 请求得到的access_token
    JSONObject access_token_obj=null;

    @GetMapping("/")
    public String kk(HttpServletRequest req){
        String signature = req.getParameter("signature");
        String echostr = req.getParameter("echostr");
        System.out.println("进来了----: "+signature);
        String str = this.getValidateStr(req);
        if (str.equals(signature)) {
            System.out.println("验证通过-2--");
            // res.send(true)
            return echostr;
        } else {
            System.out.println("验证不通过-1--");
            return "fail";
        }
    }

    // 验证信息是否来自微信服务器
    public String getValidateStr(HttpServletRequest req) {
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String arr[] = { token, timestamp, nonce };
        // let arr = [token, timestamp, nonce];
        Arrays.sort(arr);

        String arrStr = String.join("", arr);
        System.out.println("排序后: "+arrStr);

        // 然后通过sha1加密
        // const relStr = sha1(arrStr);
        String sign = this.sha1_encode(arrStr);
        System.out.println("sha1加密后sign: "+sign);
        return sign;
    }

    // sha1加密方法
    public static String sha1_encode(String str) {
        char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            int len = digest.length;
            for (int j = 0; j < len; j++) {
                sb.append(CHARS[(digest[j] >> 4) & 15]);
                sb.append(CHARS[digest[j] & 15]);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 原文链接：https://blog.csdn.net/languageStudent/article/details/114891744

    // 获取新的token:因为token每隔7200秒会过期一次
    public void getNewToken(){

        // 严格来说 access_token的过期时间是 7200秒，在每次请求前判断一下
        if(this.access_token_obj != null){
            System.out.println("已存在token"+this.access_token_obj);
            Long now = new Date().getTime();
            Long lastTime = Long.valueOf(this.access_token_obj.getString("lastTime"));
            Long expires_in = Long.valueOf(this.access_token_obj.getString("expires_in"));
            // 提早30000毫秒请求一次，避免微信服务器阻塞 expires_in是秒的单位，故要乘1000
            if((now - 30000) < (lastTime + expires_in * 1000)){
                System.out.println("未过期，可以用旧的---");
                return;
            }
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));

        // 1.根据前端，或者微信服务器返回来的code，去请求access_token
        String uri = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&"
                +"appid="+appId
                +"&secret="+appsecret;

        System.out.println("uri:"+uri);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        System.out.println(response);
        System.out.println(response.getBody());
        JSONObject obj = JSON.parseObject(response.getBody());

        // 将access_token存储在内存里，备用
        // {"access_token":"63L81ZsSElgw","expires_in":7200}
        // 严格来说 access_token的过期时间是 7200秒，在每次请求前判断一下
        Long now = new Date().getTime();
        obj.put("lastTime", now);
        this.access_token_obj = obj;
        System.out.println("obj"+obj);
    }

    // 获取ticket
    public JSONObject getTicket(JSONObject json){

        System.out.println("前端传来的json："+json);

        // 1.根据access_token请求ticket
        String access_token = this.access_token_obj.getString("access_token");
        String uri = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="
                +access_token;

        System.out.println("ticket-uri:"+uri);

        //请求头-法2
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //请求体
        // JSONObject json = new JSONObject();
        // json.put("order_by", "createTime" );
        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);

        //请求参数
        // 设置全局的默认请求参数.使用场景:比如访问某一个系统,所有的url都需要携带token请求参数,那么就可以使用此设置
        // Map<String,String> map= new HashMap();
        // map.put("apikey","e10adc3949ba59abbe56e057f2gg88dd");
        // restTemplate.setDefaultUriVariables(map);

        //发送请求
        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        // 原文链接：https://blog.csdn.net/qq_39466683/article/details/107057891
        System.out.println(response);
        System.out.println(response.getBody());
        JSONObject obj = JSON.parseObject(response.getBody());

        return obj;
    }

    @PostMapping("/getQrCode")
    public JSONObject getQrCode(@RequestBody JSONObject jsonObject){
        /**
         *  获取带参数的二维码的过程包括3步：
         *      微信公众号开发文档：https://developers.weixin.qq.com/doc/offiaccount/Account_Management/Generating_a_Parametric_QR_Code.html
         *      1.获取access_token：文档https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Get_access_token.html
         *      2.获取ticket
         *      3.用ticket 到指定 URL 换取二维码（这一步由前端完成）
         */
        // 获取access_token
        // 严格说要等待这个请求完毕再继续往下走
        this.getNewToken();
        // 2.获取ticket
        JSONObject json = this.getTicket(jsonObject);
        // 将ticket返回给前端，加载二维码
        return json;
    }

    // 当用户用手机扫码时，微信服务器会通过post方法给我们传递数据
    @PostMapping("/")
    public String awaitData(@RequestBody ParseXmlForWx px){
        String FromUserName = px.getFromUserName();
        String ToUserName = px.getToUserName();
        String MsgType = px.getMsgType();
        String Event = px.getEvent();
        String fromContent = px.getContent();

        // 这个作为区分哪个用户扫码的
        String EventKey = px.getEventKey();
        this.loginObj.put(EventKey, true);
        System.out.println("所有用户"+this.loginObj);

        Long now = new Date().getTime();

        // 回复信息给 微信服务器
        String content = "";
        if(MsgType.equals("text") ){
            if(fromContent.equals("1") ){
                content = "努力吧！";
            } else if(fromContent.equals("2")){
                content = "再坚持一会，就成功了";
            } else if(fromContent.contains("爱")){
                content = "爱你一万年！";
            } else {
                content = "谢谢！";
            }
        }
        else if(MsgType.equals("event")){
            content = "event事件";
            if(Event.equals("SCAN")){
                content = "欢迎登录代码下载器平台";
            }else if(Event.equals("subscribe")){
                content = "感谢您的关注，祝您使用愉快";
            }
            if(Event.equals("unsubscribe")){
                content = "江湖再见！";
            }
        }
        else{
            content = "其他信息来源！";
        }
        // 根据来时的信息格式，重组返回。(注意中间不能有空格)
        String msgStr = "<xml>"
                +"<ToUserName><![CDATA["+FromUserName+"]]></ToUserName>"
                +"<FromUserName><![CDATA["+ToUserName+"]]></FromUserName>"
                +"<CreateTime>"+now+"</CreateTime>"
                +"<MsgType><![CDATA[text]]></MsgType>"
                +"<Content><![CDATA["+content+"]]></Content>"
                +"</xml>";

        return msgStr;
    }

    @PostMapping("parseXml")
    public ParseXml parseXml(@RequestBody ParseXml px){
        return px;
    }
    @PostMapping("parseXmlForWx")
    public ParseXmlForWx parseXml(@RequestBody ParseXmlForWx px){
        return px;
    }

    @GetMapping("Login")
    public Boolean Login(@RequestParam String myid){
        if(this.loginObj !=null){
            Boolean bool = this.loginObj.get(myid);
            return bool;
        } else {
            return false;
        }
    }
    @GetMapping("Logout")
    public Boolean Logout(@RequestParam String myid){
        if(this.loginObj !=null){
            this.loginObj.remove(myid);
            return true;
        } else {
            return false;
        }
    }
}
