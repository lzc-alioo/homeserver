package com.alioo.monitor.service.impl;

import com.alioo.monitor.constant.AppConfig;
import com.alioo.monitor.controller.dto.AccessCtrlRequest;
import com.alioo.monitor.controller.dto.NetWorkRequest;
import com.alioo.monitor.service.NetWorkStatisticService;
import com.alioo.monitor.service.component.NetWorkComponent;
import com.alioo.monitor.service.component.UnavailableTimeComponent;
import com.alioo.monitor.service.dto.*;
import com.alioo.monitor.tv.LeTvControl;
import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.FileUtil;
import com.alioo.monitor.util.HttpUtil;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LbLinkNetWorkStatisticServiceImpl implements NetWorkStatisticService {

    @Value("${app.monitorpath}")
    private String monitorpath;

    @Value("${mac.letv}")
    private String macLetv;

    @Autowired
    private UnavailableTimeComponent unavailableTimeComponent;

    @Autowired
    private NetWorkComponent netWorkComponent;


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
            TokenDto loginDto = JsonUtil.fromJson(ret, TokenDto.class);
            log.info("token信息：{}", JsonUtil.toJson(loginDto));

            if (loginDto != null) {
                return loginDto.getToken();
            }
        } catch (Exception e) {
            log.error("token信息异常", e);
        }
        return null;
    }


    public LbStatisticDto getMachineList() {

        String token = getToken();
        return getMachineList(token);
    }

    public List<Terminal2> getMachineList2() {

        LbStatisticDto lbStatisticDto = getMachineList();

        List<Terminal2> list = lbStatisticDto.getTerminals().stream()
                .map(terminal -> {

                    Terminal2 terminal2 = new Terminal2();
                    terminal2.setName(AppConfig.getNameAlias(terminal.getMac(),terminal.getName()));
                    terminal2.setIp(terminal.getIp());
                    terminal2.setMac(terminal.getMac());

                    terminal2.setDownSpeed(terminal.getSpeed());
                    terminal2.setUpSpeed(terminal.getUpSpeed());

                    terminal2.setChecked(("F".equals(terminal.getFlag().substring(2, 3))) ? true : false);
                    terminal2.setState(ObjectUtils.isEmpty(terminal.getIp()) ? "off_online" : "on_line");

                    return terminal2;
                })
                .collect(Collectors.toList());

        return list;
    }

    private LbStatisticDto getMachineList(String token) {
        try {

            String myurl = "http://192.168.16.1/protocol.csp?token=" + token;
            Map<String, String> headers = netWorkComponent.getHeaderMap();

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
            sortTerminals(lbStatisticDto);
//            log.info("machine原始信息：{}", JsonUtil.toJson(lbStatisticDto));

            return lbStatisticDto;

        } catch (Exception e) {
            log.error("获取统计数据时出现异常", e);
        }
        return null;
    }

    private void sortTerminals(LbStatisticDto lbStatisticDto) {
        if (lbStatisticDto == null || lbStatisticDto.getTerminals() == null || lbStatisticDto.getTerminals().isEmpty()) {
            return;
        }

        lbStatisticDto.getTerminals().forEach(terminal -> terminal.setOrder(AppConfig.getOrder(terminal.getMac(), 10000)));

        Collections.sort(lbStatisticDto.getTerminals());
    }


    public boolean setNetWorkSwitch(AccessCtrlRequest request) {

        try {
            String token = getToken();


            String myurl = "http://192.168.16.1/protocol.csp?token=" + token + "&fname=net&opt=host_black&function=set&mac=" + request.getMac() + "&act=" + request.getAct();
            Map<String, String> headers = netWorkComponent.getHeaderMap();
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

        List<UnavailableTimeDto> list = unavailableTimeComponent.getUnavailableTimeList();

        return list;

    }


    public int updateUnavailableTimeList(List<UnavailableTimeDto> list) {

        int result = unavailableTimeComponent.updateUnavailableTimeList(list);

        return result;

    }


    public void checkNetWork() {

        try {
            String now = DateTimeUtil.getDateTimeString("HH:mm");

            List<UnavailableTimeDto> list = getUnavailableTimeList();
            log.info("scheduled checkNetWork now:{},tmplist{}", now, list);


            list.forEach(obj -> {
                if (now.equals(obj.getStartTimeStr())) {
                    log.info("checkNetWork scheduled 命中开始时间:{}", obj.getStartTimeStr());
                    AccessCtrlRequest request = new AccessCtrlRequest();
                    request.setMac(macLetv);
                    request.setAct("on");
                    setNetWorkSwitch(request);

                    List<Terminal2> machineList2 = getMachineList2();
                    Map<String,String> machineMap2=machineList2.stream().collect(Collectors.toMap(Terminal2::getMac, Terminal2::getIp));
                    LeTvControl.sendCommond(machineMap2.get(macLetv),9900,"return");
                    LeTvControl.sendCommond(machineMap2.get(macLetv),9900,"ok");
                    LeTvControl.sendCommond(machineMap2.get(macLetv),9900,"return");
                    LeTvControl.sendCommond(machineMap2.get(macLetv),9900,"ok");
                    return;
                }

                if (now.equals(obj.getEndTimeStr())) {
                    log.info("checkNetWork scheduled 命中结束时间:{}", obj.getEndTimeStr());
                    AccessCtrlRequest request = new AccessCtrlRequest();
                    request.setMac(macLetv);
                    request.setAct("off");
                    setNetWorkSwitch(request);
                    return;
                }
            });
        } catch (Exception e) {
            log.error("出现异常了", e);
        }


    }

    public void monitorNetWork() {

        try {
            String now = DateTimeUtil.getDateTimeString("HH:mm");

            log.info("scheduled monitorNetWork now:{}", now);

            String token = getToken();
            LbStatisticDto lbStatisticDto = getMachineList(token);
            List<Terminal> terminals = lbStatisticDto.getTerminals();

            String realmonitorpath = this.monitorpath + "/" + DateTimeUtil.getDateTimeString("yyyyMMdd") + "/";

            FileUtil.mkdirs(realmonitorpath);

            //流量监控
            terminals.stream()
                    .filter(terminal -> {
                        return !ObjectUtils.isEmpty(terminal);
                    })
                    .forEach(terminal -> {
                        //format data
                        StringBuilder sb = new StringBuilder();
                        sb.append(now).append(",")
                                .append(terminal.getMac()).append(",")
                                .append(terminal.getIp()).append(",")
                                .append(terminal.getSpeed()).append(",")
                                .append(terminal.getUpSpeed());


                        FileUtil.writeFile(realmonitorpath + AppConfig.getNameAlias(terminal.getMac(),terminal.getName()), Arrays.asList(sb.toString()), true);

                    });


        } catch (Exception e) {
            log.error("monitorNetWork出现异常了", e);
        }


    }


    public List<NetWorkDetailDto> getNetWorkDetailList(NetWorkRequest request) {

        String dateStr = request.getStartTime().substring(0, 8);

        Map<String, List<String>> groupMap = netWorkComponent.getNetWorkGroup(dateStr, request.getMachineName());

        List<NetWorkDetailDto> list2 = netWorkComponent.getNetWorkDetailList(groupMap);

        List<NetWorkDetailDto> list3 = netWorkComponent.getSubList(request, list2);

        return list3;

    }


    public List<NetWorkOnLineDto> getNewWorkOnLineList(NetWorkRequest request) {

        String dateStr = request.getStartTime().substring(0, 8);

        Map<String, List<String>> groupMap = netWorkComponent.getNetWorkGroup(dateStr, request.getMachineName());

        List<NetWorkOnLineDto> list2 = netWorkComponent.getNewWorkOnLineList(groupMap);

        List<NetWorkOnLineDto> list3 = netWorkComponent.getSubList(request, list2);

        return list3;
    }


}
