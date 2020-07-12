package com.alioo.monitor.router.impl;

import com.alioo.monitor.router.AccessCtrlRequest;
import com.alioo.monitor.router.FlowStatisticService;
import com.alioo.monitor.router.dto.LbStatisticDto;
import com.alioo.monitor.router.dto.LoginDto;
import com.alioo.monitor.router.dto.Result;
import com.alioo.monitor.router.dto.UnavailableTimeDto;
import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.FileUtil;
import com.alioo.monitor.util.HttpUtil;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LbLinkFlowStatisticService implements FlowStatisticService {


    @Value("${app.path}")
    private String path;


    public LbStatisticDto getList() {

        String token = getToken();
        LbStatisticDto lbStatisticDto = getStatisticMap(token);

//        AccessCtrlRequest accessCtrlRequest = new AccessCtrlRequest();
//        accessCtrlRequest.setMac("80:0C:67:1F:69:F7");
//        accessCtrlRequest.setAct("on");
//        accessCtrl(token,accessCtrlRequest);

        return lbStatisticDto;
    }


    public String getToken() {
        try {
            String myurl = "http://192.168.16.1/protocol.csp?fname=system&opt=login&function=set&usrid=f11fcbaac530c673a91c6022a49c2219";
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("Connection", "keep-alive");
//            headers.put("Content-Length", "0");
            headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
            headers.put("X-Requested-With", "XMLHttpRequest");
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            headers.put("Origin", "http://192.168.16.1");
            headers.put("Referer", "http://192.168.16.1");
            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");

            Map<String, String> datas = new LinkedHashMap<>();

            String ret = HttpUtil.post(myurl, headers, datas);
            if (ret == null) {
                return null;
            }

            //{ "opt": "login", "fname": "system", "function": "set", "token": "B65FC5DC95A91524866BBC91B9C20625", "error": 0 }
            LoginDto loginDto = JsonUtil.fromJson(ret, LoginDto.class);
            log.info("token信息：{}", JsonUtil.toJson(loginDto));

            if (loginDto != null) {
                return loginDto.getToken();
            }
        } catch (Exception e) {
            log.error("token信息异常", e);
        }
        return null;
    }

    private LbStatisticDto getStatisticMap(String token) {
        try {

            String myurl = "http://192.168.16.1/protocol.csp?token=" + token;
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("Connection", "keep-alive");
            headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
            headers.put("X-Requested-With", "XMLHttpRequest");
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            headers.put("Origin", "http://192.168.16.1");
            headers.put("Referer", "http://192.168.16.1/user/index.html?tt=1593269725937");
            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            headers.put("Cookie", "lstatus=true; token=" + token);

            Map<String, String> datas = new LinkedHashMap<>();
            datas.put("fname", "system");
            datas.put("opt", "main");
            datas.put("function", "get");
            datas.put("math", "0.12594814682128974");

            String ret = HttpUtil.post(myurl, headers, datas);
            if (ret == null) {
                return null;
            }

            //{ "opt": "host_if", "fname": "system", "function": "get", "terminals": [ { "mac": "80:0C:67:1F:69:F7", "flag": "FTFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "70:48:0F:52:ED:C1", "flag": "FTFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "DC:A6:32:23:35:D4", "flag": "FTFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "48:3C:0C:74:9B:F0", "flag": "FTFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "38:F9:D3:2E:B6:DF", "flag": "TTFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "78:0F:77:62:47:E0", "flag": "FTFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "A4:83:E7:3C:3F:3D", "flag": "FFFFFFTFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "B8:FC:9A:3E:6A:DC", "flag": "FFFFFFFFFTFF", "ls": 0, "ls_up": 0 }, { "mac": "E4:A3:2F:2D:29:00", "flag": "FFFFFFFFFTFF", "ls": 0, "ls_up": 0 } ], "error": 0 }%                                                                                                                                                                           alioo@alioo15 ~ %
            LbStatisticDto lbStatisticDto = JsonUtil.fromJson(ret, LbStatisticDto.class);
            log.info("getStatisticMap信息：{}", JsonUtil.toJson(lbStatisticDto));
            return lbStatisticDto;

        } catch (Exception e) {
            log.error("获取统计数据时出现异常", e);
        }
        return null;
    }


    public boolean accessCtrl(String token, AccessCtrlRequest request) {

        try {

            String myurl = "http://192.168.16.1/protocol.csp?token=" + token + "&fname=net&opt=host_black&function=set&mac=" + request.getMac() + "&act=" + request.getAct();
            Map<String, String> headers = new LinkedHashMap<>();
            headers.put("Connection", "keep-alive");
            headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
            headers.put("X-Requested-With", "XMLHttpRequest");
            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
            headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            headers.put("Origin", "http://192.168.16.1");
            headers.put("Referer", "http://192.168.16.1/user/index.html?tt=1593269725937");
            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
            headers.put("Cookie", "lstatus=true; token=" + token);

            Map<String, String> datas = new LinkedHashMap<>();

            String ret = HttpUtil.post(myurl, headers, datas);
            if (ret == null) {
                return false;
            }

            //{ "opt": "host_black", "fname": "net", "function": "set", "error": 0 }
            Result result = JsonUtil.fromJson(ret, Result.class);
            log.info("accessCtrl信息：{}", JsonUtil.toJson(result));
            return true;

        } catch (Exception e) {
            log.error("accessCtrl时出现异常", e);
        }


        return true;

    }

    public List<UnavailableTimeDto> getUnavailableTimeList() {

        List<String> tmplist = FileUtil.readFile2List(path);
        System.out.println("tmplist=" + tmplist);

        List<UnavailableTimeDto> list = tmplist.stream()
                .map(str -> {
                    String[] arr = str.split(",");
                    if (arr.length != 2) {
                        return null;
                    }
                    String startTimeStr = arr[0];
                    String endTimeStr = arr[1];

                    return new UnavailableTimeDto(startTimeStr, endTimeStr);

                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());


//        list.add(new UnavailableTimeDto("08:00", "08:15"));
//        list.add(new UnavailableTimeDto("09:00", "09:15"));


        return list;

    }


    public int updateUnavailableTimeList(List<UnavailableTimeDto> list) {

        List<String> tmplist = list.stream()
                .map(dto -> {
                    return dto.getStartTimeStr() + "," + dto.getEndTimeStr();
                })
                .filter(dto -> dto != null)
                .collect(Collectors.toList());


        FileUtil.writeFile2(path, tmplist);

        return tmplist.size();

    }


    public void checkNetWork() {

        String now = DateTimeUtil.getDateTimeString("HH:mm");
        List<String> tmplist = FileUtil.readFile2List(path);
        log.info("now:{},tmplist=" + tmplist);

        List<UnavailableTimeDto> list = getUnavailableTimeList();

//        AccessCtrlRequest request = new AccessCtrlRequest();
//        request.setMac("B8:FC:9A:3E:6A:DC");
//        list.stream().map(obj -> {
//            if (obj.getStartTimeStr().equals(now)) {
//                request.setAct("on");
//            } else if (obj.getEndTimeStr().equals(now)) {
//                request.setAct("off");
//            }
//            String token = getToken();
//            boolean flag = accessCtrl(token, request);
//            log.info("定时设置网络结果 now:{},request:{},flag:{}", now, JsonUtil.toJson(request), flag);
//            return request;
//        });

    }


}
