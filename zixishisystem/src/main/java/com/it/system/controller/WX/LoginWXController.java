package com.it.system.controller.WX;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.system.domain.Kehu;
import com.it.system.domain.Result;
import com.it.system.service.KehuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("WXLogin")
public class LoginWXController {

    private final String AppId = "wx958c331bb5b02d97";
    private final String AppSecret = "c7d7d587e080c1dda3b07c7a6ff47f96";


    @Autowired
    private KehuService kehuService;

    @GetMapping("setCode")
    public Result setCode(@RequestParam("code") String code) {

        System.out.println("code" + code);

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", AppId);
        params.add("secret", AppSecret);
        params.add("js_code", code);
        String url = "https://api.weixin.qq.com/sns/jscode2session";

        System.out.println(url + "?appid=" + AppId + "&secret=" + AppSecret + "&js_code=" + code);
        HttpHeaders headers = new HttpHeaders();
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        try {
            System.out.println("response.getStatusCode() :" + response.getStatusCode());

            JSONObject jsonObject = JSON.parseObject(response.getBody());
            String session_key = (String) jsonObject.get("session_key");
            String openid = (String) jsonObject.get("openid");
            Kehu kehuEntity = AddOrUpdate(openid, session_key);
            return Result.ok(kehuEntity);
        } catch (Exception e) {
            return Result.fail(e);
        }
    }


    private Kehu AddOrUpdate(String openID, String sessionKey) {
        Kehu kehu = new Kehu();
        kehu.setOpenid(openID);
        kehu.setSessionkey(sessionKey);
        boolean openid = kehuService.saveOrUpdate(kehu, new QueryWrapper<Kehu>().eq("openid", openID));

        Kehu info = kehuService.getOne(new QueryWrapper<Kehu>().eq("openid", openID));
        return info;
    }

}
