package com.alioo.monitor.service.component;

import com.alioo.monitor.controller.dto.NetWorkRequest;
import com.alioo.monitor.service.dto.NetWorkDetailDto;
import com.alioo.monitor.service.dto.NetWorkDto;
import com.alioo.monitor.service.dto.NetWorkOnLineDto;
import com.alioo.monitor.util.FileUtil;
import com.alioo.monitor.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NetWorkComponent {


    @Value("${app.monitorpath}")
    private String monitorpath;

    public Map<String, String> getHeaderMap() {
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


    public Map<String, List<String>> getNetWorkGroup(String datestr, String machineName) {
        String realmonitorpath = this.monitorpath + "/" + datestr + "/";

        String filepath = realmonitorpath + machineName;

        File file = new File(filepath);
        if (!file.exists()) {
            return new HashMap<>();
        }

        List<String> list = FileUtil.readFile2List(filepath);

        Map<String, List<String>> groupMap = list.stream().collect(
                Collectors.groupingBy(
                        str -> getTime5Str(str),
                        LinkedHashMap::new,
                        Collectors.toList()
                )
        );

        log.info("查询网络数据datestr:{}, machineName:{}, groupMap:{}", datestr, machineName, JsonUtil.toJson(groupMap));
        return groupMap;
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


    public List<NetWorkDetailDto> getNetWorkDetailList(Map<String, List<String>> groupMap) {
        List<NetWorkDetailDto> list2 = new ArrayList<>();

        for (int hh = 0; hh < 24; hh++) {
            String hhStr = hh < 10 ? "0" + hh : "" + hh;

            for (int minute5 = 0; minute5 < 60; minute5 = minute5 + 5) {
                String minute5Str = minute5 < 10 ? "0" + minute5 : "" + minute5;
                String timeStr = hhStr + ":" + minute5Str;
                List<String> list = groupMap.get(timeStr);

                NetWorkDetailDto netWorkDetailDto = getNetWorkDetailDto(timeStr, list);
                list2.add(netWorkDetailDto);

            }

        }


        return list2;
    }

    private NetWorkDetailDto getNetWorkDetailDto(String timeStr, List<String> list) {
        if (list == null || list.isEmpty()) {
            return new NetWorkDetailDto(timeStr, 0);
        }

        Long netSum = list.stream().map(str -> {
            String arr[] = str.split(",");
            long net = Long.parseLong(arr[3]) + Long.parseLong(arr[4]);
            return net;

        }).reduce(Long::sum).get();

        return new NetWorkDetailDto(timeStr, netSum);

    }


    public List<NetWorkOnLineDto> getNewWorkOnLineList(Map<String, List<String>> groupMap) {
        List<NetWorkOnLineDto> list2 = new ArrayList<>();

        for (int hh = 0; hh < 24; hh++) {
            String hhStr = hh < 10 ? "0" + hh : "" + hh;

            for (int minute5 = 0; minute5 < 60; minute5 = minute5 + 5) {
                String minute5Str = minute5 < 10 ? "0" + minute5 : "" + minute5;
                String timeStr = hhStr + ":" + minute5Str;
                List<String> list = groupMap.get(timeStr);

                NetWorkOnLineDto netWorkDataDto = getNetWorkOnLineDto(timeStr, list);
                list2.add(netWorkDataDto);

            }

        }

        return list2;
    }


    //数据样例 00:39,B8:FC:9A:3E:6A:DC,,0,0
    //数据样例 13:06,B8:FC:9A:3E:6A:DC,192.168.16.214,0,0
    private NetWorkOnLineDto getNetWorkOnLineDto(String timeStr, List<String> list) {
        if (list == null || list.isEmpty()) {
            return new NetWorkOnLineDto(timeStr, 0);
        }

        Long netSum = list.stream().map(str -> {
            String arr[] = str.split(",");
            if (arr[2] == null || arr[2].isEmpty()) {
                return 0L;
            }

            return 1L;

        }).reduce(Long::sum).get();

        return new NetWorkOnLineDto(timeStr, netSum);

    }


    public List getSubList(NetWorkRequest request, List<? extends NetWorkDto> list2) {
        String startTimeStr = request.getStartTime().substring(8, 10) + ":" + request.getStartTime().substring(10, 12);
        String endTimeStr = request.getEndTime().substring(8, 10) + ":" + request.getEndTime().substring(10, 12);

        return list2.stream().filter(netWorkDto -> {
            String timeStr = netWorkDto.getTimeStr();
            if (timeStr.compareTo(startTimeStr) < 0) {
                return false;
            }
            if (timeStr.compareTo(endTimeStr) > 0) {
                return false;
            }

            return true;
        }).collect(Collectors.toList());
    }

}
