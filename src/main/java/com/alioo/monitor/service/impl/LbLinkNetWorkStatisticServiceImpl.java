package com.alioo.monitor.service.impl;

import com.alioo.monitor.constant.AppConfig;
import com.alioo.monitor.controller.dto.AccessCtrlRequest;
import com.alioo.monitor.service.NetWorkStatisticService;
import com.alioo.monitor.service.component.UnavailableTimeComponent;
import com.alioo.monitor.service.dto.*;
import com.alioo.monitor.util.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LbLinkNetWorkStatisticServiceImpl implements NetWorkStatisticService {

    public static Logger NET_LOGGER = LoggerFactory.getLogger("NET");

    @Value("${app.monitorpath}")
    private String monitorpath;

    @Value("${mac.letv}")
    private String macLetv;

    @Autowired
    private UnavailableTimeComponent unavailableTimeComponent;


    public String getToken() {
        try {
            String myurl = "http://192.168.16.1/protocol.csp?fname=system&opt=login&function=set&usrid=f11fcbaac530c673a91c6022a49c2219";
            Map<String, String> headers = getHeaderMap();
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
        LbStatisticDto lbStatisticDto = getMachineList(token);

        return lbStatisticDto;
    }


    private LbStatisticDto getMachineList(String token) {
        try {

            String myurl = "http://192.168.16.1/protocol.csp?token=" + token;
            Map<String, String> headers = getHeaderMap();

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
            log.info("lbStatisticDto信息：{}", JsonUtil.toJson(lbStatisticDto));

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

        lbStatisticDto.getTerminals().forEach(terminal -> {
            terminal.setOrder(AppConfig.orderdMacMap.getOrDefault(terminal.getMac(), 10000));
        });

        Collections.sort(lbStatisticDto.getTerminals());
    }


    public boolean setNetWorkSwitch(AccessCtrlRequest request) {

        try {
            String token = getToken();


            String myurl = "http://192.168.16.1/protocol.csp?token=" + token + "&fname=net&opt=host_black&function=set&mac=" + request.getMac() + "&act=" + request.getAct();
            Map<String, String> headers = getHeaderMap();
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
            log.info("scheduled checkNetWork now:{},tmplist{}", now, JsonUtil.toJson(list));

            list.forEach(obj -> {
                if (now.equals(obj.getStartTimeStr())) {
                    log.info("checkNetWork scheduled 命中开始时间:{}", obj.getStartTimeStr());
                    AccessCtrlRequest request = new AccessCtrlRequest();
                    request.setMac(macLetv);
                    request.setAct("on");
                    setNetWorkSwitch(request);
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

            log.info("scheduled checkNetWork now:{}", now);

            String token = getToken();
            LbStatisticDto lbStatisticDto = getMachineList(token);
            List<Terminal> terminals = lbStatisticDto.getTerminals();

//            //新设备加入提醒
//            terminals.forEach(terminal -> {
//                if (terminal.getIp() == null || terminal.getIp().isEmpty()) {
//                    return;
//                }
//                if (terminal.getIp() != null && !AppConfig.whiteMacMap.keySet().contains(terminal.getMac())) {
//                    LogUtil.info(NET_LOGGER, "新设备加入,terminal:{}", JsonUtil.toJson(terminal));
//                }
//            });

            String realmonitorpath = this.monitorpath + "/" + DateTimeUtil.getDateTimeString("yyyyMMdd") + "/";

            FileUtil.mkdirs(realmonitorpath);

            //流量监控
            terminals.stream()
                    .filter(terminal -> {
                        return true;
                    })
                    .forEach(terminal -> {
                        //format data
                        StringBuilder sb = new StringBuilder();
                        sb.append(now).append(",")
                                .append(terminal.getMac()).append(",")
                                .append(terminal.getIp()).append(",")
                                .append(terminal.getSpeed()).append(",")
                                .append(terminal.getUpSpeed());


                        FileUtil.writeFile(realmonitorpath + terminal.getName(), Arrays.asList(sb.toString()), true);

                    });


        } catch (Exception e) {
            log.error("monitorNetWork出现异常了", e);
        }


    }

    private Map<String, String> getHeaderMap() {
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("Connection", "keep-alive");
        headers.put("Accept", "application/json, text/javascript, */*; q=0.01");
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        headers.put("Origin", "http://192.168.16.1");
        headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");

        return headers;
    }


    /**
     * @param datestr     示例：20211002
     * @param machineName
     * @return
     */
    public List<NetWorkDataDto> getNetWorkData(String datestr, String machineName) {

        String realmonitorpath = this.monitorpath + "/" + datestr + "/";

        String filepath = realmonitorpath + machineName;

        File file = new File(filepath);
        if (!file.exists()) {
            return new ArrayList<>();
        }


        List<String> list = FileUtil.readFile2List(filepath);

        Map<String, List<String>> gmap = list.stream().collect(
                Collectors.groupingBy(
                        str -> getTime5Str(str),
                        LinkedHashMap::new,
                        Collectors.toList()
                )
        );

        log.info("查询网络数据datestr:{}, machineName:{}, gmap:{}", datestr, machineName, JsonUtil.toJson(gmap));


        List<NetWorkDataDto> list2 = getNetWorkDataList(gmap);

        log.info("查询网络数据处理后 datestr:{}, machineName:{}, gmap2:{}", datestr, machineName, JsonUtil.toJson(list2));

        return list2;

    }


    private String getTime5Str(String str) {
        String time = str.split(",")[0];

        String[] hhMM = time.split(":");

        int minute = Integer.parseInt(hhMM[1]);

        //先除5 后乘以5 的目的是为去了小数
        int minute5 = minute / 5 * 5;

        String minute5Str = minute5 < 10 ? "0" + minute5 : "" + minute5;

        return hhMM[0] + ":" + minute5Str;

    }

    private List<NetWorkDataDto> getNetWorkDataList(Map<String, List<String>> gmap) {
        List<NetWorkDataDto> list2 = new ArrayList<>();

        String now = DateTimeUtil.getDateTimeString("HH:mm");
//        int hhMax = Integer.parseInt(now.substring(0, 2));
//        int minuteMax = Integer.parseInt(now.substring(2, 4));

        for (int hh = 0; hh < 24; hh++) {
            String hhStr = hh < 10 ? "0" + hh : "" + hh;

            for (int minute5 = 0; minute5 < 60; minute5 = minute5 + 5) {
                String minute5Str = minute5 < 10 ? "0" + minute5 : "" + minute5;

                String timeStr = hhStr + ":" + minute5Str;
                if (timeStr.compareTo(now) > 0) {
                    return list2;
                }


                List<String> list = gmap.get(timeStr);

                NetWorkDataDto netWorkDataDto = getNetWorkDataDto(timeStr, list);
                list2.add(netWorkDataDto);

            }

        }


        return list2;
    }


    private NetWorkDataDto getNetWorkDataDto(String timeStr, List<String> list) {
        if (list == null || list.isEmpty()) {
            return new NetWorkDataDto(timeStr, 0);
        }

        Long netSum = list.stream().map(str -> {
            String arr[] = str.split(",");
            long net = Long.parseLong(arr[3]) + Long.parseLong(arr[4]);
            return net;

        }).reduce(Long::sum).get();

        return new NetWorkDataDto(timeStr, netSum);

    }

}
