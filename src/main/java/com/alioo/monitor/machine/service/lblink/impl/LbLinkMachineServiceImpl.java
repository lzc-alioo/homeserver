package com.alioo.monitor.machine.service.lblink.impl;

import com.alioo.monitor.machine.config.MacIpConfig;
import com.alioo.monitor.machine.controller.request.AccessControlCommand;
import com.alioo.monitor.machine.controller.request.NetWorkQuery;
import com.alioo.monitor.machine.service.MachineService;
import com.alioo.monitor.machine.service.component.DisabledTimeComponent;
import com.alioo.monitor.machine.service.component.NetWorkComponent;
import com.alioo.monitor.machine.service.component.TokenComponent;
import com.alioo.monitor.machine.service.domian.*;
import com.alioo.monitor.machine.service.lblink.dto.LbResult;
import com.alioo.monitor.machine.service.lblink.dto.LbStatistic;
import com.alioo.monitor.machine.service.lblink.dto.LbTerminal;
import com.alioo.monitor.machine.tv.LeTvControl;
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
import java.util.stream.Stream;

@Slf4j
@Service
public class LbLinkMachineServiceImpl implements MachineService {

    @Value("${app.monitorpath}")
    private String monitorpath;

    @Value("#{${app.time-control}}")
    private Map<String, String> timeControl;

    @Autowired
    private DisabledTimeComponent disabledTimeComponent;

    @Autowired
    private NetWorkComponent netWorkComponent;

    @Autowired
    private TokenComponent tokenComponent;


    @Override
    public String getToken() {
        return tokenComponent.getTokenWithCache();
    }


    @Override
    public TerminalStatistic getMachineList() {

        String token = getToken();
        LbStatistic lbStatistic = getMachineList(token);
        if (lbStatistic == null || lbStatistic.getTerminals() == null) {
            return TerminalStatistic.builder().list(Collections.EMPTY_LIST).build();
        }

        List<Terminal> list = lbStatistic.getTerminals().stream().map(terminal -> {

            Terminal terminal2 = new Terminal();
            terminal2.setName(MacIpConfig.getNameAliasByMac(terminal.getMac(), terminal.getName()));
            terminal2.setIp(terminal.getIp());
            terminal2.setMac(terminal.getMac());

            terminal2.setDownSpeed(terminal.getSpeed());
            terminal2.setUpSpeed(terminal.getUpSpeed());

            terminal2.setChecked(("F".equals(terminal.getFlag().substring(2, 3))) ? true : false);
            terminal2.setState(ObjectUtils.isEmpty(terminal.getIp()) ? "off_online" : "on_line");
            terminal2.setOrder(MacIpConfig.getOrder(terminal.getMac(),99999));

            return terminal2;
        }).sorted().collect(Collectors.toList());

        return TerminalStatistic.builder().downloadSpeed(lbStatistic.getCurSpeed()).upSpeed(lbStatistic.getUpSpeed()).onlineTime(lbStatistic.getOntime()).list(list).build();

    }

    private LbStatistic getMachineList(String token) {
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
            LbStatistic lbStatistic = JsonUtil.fromJson(ret, LbStatistic.class);
            sortTerminals(lbStatistic);
//            log.info("machine原始信息：{}", JsonUtil.toJson(lbStatistic));

            return lbStatistic;

        } catch (Exception e) {
            log.error("获取统计数据时出现异常", e);
        }
        return null;
    }

    private void sortTerminals(LbStatistic lbStatistic) {
        if (lbStatistic == null || lbStatistic.getTerminals() == null || lbStatistic.getTerminals().isEmpty()) {
            return;
        }

        lbStatistic.getTerminals().forEach(terminal -> terminal.setOrder(MacIpConfig.getOrder(terminal.getMac(), 10000)));

        Collections.sort(lbStatistic.getTerminals());
    }

    public boolean accessControl(String mac, String act) {
        AccessControlCommand request = new AccessControlCommand();
        request.setMac(mac);
        request.setAct(act);
        return accessControl(request);
    }

    public boolean accessControl(AccessControlCommand request) {

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
            LbResult lbResult = JsonUtil.fromJson(ret, LbResult.class);
            log.info("accessCtrl信息：{}", JsonUtil.toJson(lbResult));

        } catch (Exception e) {
            log.error("accessCtrl时出现异常", e);
        }


        return true;

    }


    public List<DisabledTime> getDisabledTimeList(String group) {

        List<DisabledTime> list = disabledTimeComponent.getDisabledTimeList(group);

        return list;

    }


    public int updateDisabledTimeList(String machine, List<DisabledTime> list) {

        int result = disabledTimeComponent.updateUnavailableTimeList(machine, list);

        return result;

    }


    public void controlNetWork(String group) {

        try {
            String now = DateTimeUtil.getDateTimeString("HH:mm");

            List<DisabledTime> timeList = getDisabledTimeList(group);

            String macs = timeControl.get(group);
            log.info("定时执行 controlNetWork now:{},group:{},timeList{}", now, group, timeList);

            timeList.forEach(obj -> {
                if (!obj.isChecked()) {
                    return;
                }

                if (now.equals(obj.getStartTimeStr())) {
                    log.info("controlNetWork group:{}, 命中开始时间:{}", group, obj.getStartTimeStr());
                    List<Terminal> machineList2 = getMachineList().getList();
                    Map<String, String> mapIpMap = machineList2.stream().collect(Collectors.toMap(Terminal::getMac, Terminal::getIp));

                    Stream.of(macs.split(",")).forEach(mac -> {
                        accessControl(mac, "on");

                        LeTvControl.sendCommond(mapIpMap.get(mac), 9900, "power");
                    });

                    return;
                }

                if (now.equals(obj.getEndTimeStr())) {
                    log.info("controlNetWork group:{}, 命中结束时间:{}", group, obj.getEndTimeStr());
                    Stream.of(macs.split(",")).forEach(mac -> {
                        accessControl(mac, "off");
                    });
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

            log.info("定时执行 monitorNetWork now:{}", now);

            String token = getToken();
            LbStatistic lbStatistic = getMachineList(token);
            if (lbStatistic == null || lbStatistic.getTerminals() == null) {
                return;
            }
            List<LbTerminal> terminals = lbStatistic.getTerminals();

            String realmonitorpath = this.monitorpath + "/" + DateTimeUtil.getDateTimeString("yyyyMMdd") + "/";

            FileUtil.mkdirs(realmonitorpath);

            //流量监控
            terminals.stream().filter(terminal -> {
                return !ObjectUtils.isEmpty(terminal);
            }).forEach(terminal -> {
                //format data
                StringBuilder sb = new StringBuilder();
                sb.append(now).append(",").append(terminal.getMac()).append(",").append(terminal.getIp()).append(",").append(terminal.getSpeed()).append(",").append(terminal.getUpSpeed());


                FileUtil.writeFile(realmonitorpath + MacIpConfig.getNameAliasByMac(terminal.getMac(), terminal.getName()), Arrays.asList(sb.toString()), true);

            });


        } catch (Exception e) {
            log.error("monitorNetWork出现异常了", e);
        }


    }


    public List<Net> getNetList(NetWorkQuery request) {

        String dateStr = request.getStartTime().substring(0, 8);

        Map<String, List<String>> groupMap = netWorkComponent.getNetWorkGroup(dateStr, request.getMachineName());

        List<Net> list2 = netWorkComponent.getNetList(groupMap);

        List<Net> list3 = netWorkComponent.getSubList(request, list2);

        return list3;

    }


    public List<Online> getOnlineList(NetWorkQuery request) {

        String dateStr = request.getStartTime().substring(0, 8);

        Map<String, List<String>> groupMap = netWorkComponent.getNetWorkGroup(dateStr, request.getMachineName());

        List<Online> list2 = netWorkComponent.getOnlineList(groupMap);

        List<Online> list3 = netWorkComponent.getSubList(request, list2);

        return list3;
    }


}
