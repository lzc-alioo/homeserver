package com.alioo.monitor.machine.service.component;

import com.alioo.monitor.machine.service.lblink.dto.LbToken;
import com.alioo.monitor.util.HttpUtil;
import com.alioo.monitor.util.JsonUtil;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Component
public class TokenComponent {

    @Autowired
    Cache<String, String> tokenCache;

    @Autowired
    private NetWorkComponent netWorkComponent;

    public String getTokenWithCache() {
        return tokenCache.get("token", k -> getToken());
    }


    public String getToken() {
        try {
            String myurl = "http://192.168.16.1/protocol.csp?fname=system&opt=login&function=set&usrid=f11fcbaac530c673a91c6022a49c2219";
            Map<String, String> headers = netWorkComponent.getHeaderMap();
            Map<String, String> datas = new LinkedHashMap<>();

            String ret = HttpUtil.post(myurl, headers, datas);
            if (ret == null) {
                return null;
            }

            //{ "opt": "login", "fname": "system", "function": "set", "token": "B65FC5DC95A91524866BBC91B9C20625", "error": 0 }
            LbToken loginDto = JsonUtil.fromJson(ret, LbToken.class);
            log.info("token信息：{}", JsonUtil.toJson(loginDto));

            if (loginDto != null) {
                return loginDto.getToken();
            }
        } catch (Exception e) {
            log.error("token信息异常", e);
        }
        return null;
    }

}
