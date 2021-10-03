//package com.alioo.monitor.service.impl;
//
//import com.alioo.monitor.service.NetWorkStatisticService;
//import com.alioo.monitor.service.dto.FlowStatisticDto;
//import com.alioo.monitor.util.HttpUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Assert;
//
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Slf4j
////@Service
//public class TpLinkFlowStatisticService implements NetWorkStatisticService {
//
//    public List<FlowStatisticDto> getList() {
//
//        Map<String, FlowStatisticDto> statisticDtoMap = getStatisticMap();
//        Map<String, String> hostMap = getHostMap();
//        Map<String, Integer> accessCtrlMap = getAccessCtrlMap();
//
//        mergeMap(statisticDtoMap, hostMap, accessCtrlMap);
//
//        log.info("统计数据|主机数据|控制数据：{}", statisticDtoMap);
//        return new ArrayList<FlowStatisticDto>(statisticDtoMap.values());
//    }
//
//
//    private Map<String, FlowStatisticDto> getStatisticMap() {
//        Map<String, FlowStatisticDto> map = new LinkedHashMap<>();
//
//        try {
//
//            String myurl = "http://192.168.1.253/userRpm/SystemStatisticRpm.htm?contType=1&sortType=4&autoRefresh=2&Num_per_page=20&Goto_page=1";
//            Map<String, String> headers = new LinkedHashMap<>();
//            headers.put("Connection", "keep-alive");
//            headers.put("Upgrade-Insecure-Requests", "1");
//            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
//            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            headers.put("Referer", "http://192.168.1.253/userRpm/SystemStatisticRpm.htm?contType=1&sortType=2&autoRefresh=2&Num_per_page=10&Goto_page=1");
//            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//            headers.put("Cookie", "tLargeScreenP=1; subType=pcSub; tPlatform=1; ChgPwdSubTag=; TPLoginTimes=0; Authorization=Basic%20YWRtaW46bHpjMTIxMDI4");
//
//            String ret = HttpUtil.get(myurl, headers);
//            if (ret == null) {
//                return map;
//            }
//
//            String rowarr[] = null;
//
//            String regex = "new Array\\((.*?)\\)\\;"; // 这里的(.*?) 会发现有个问号?，它的作用就是懒惰匹配
//            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL); //多行匹配
//            Matcher matcher = pattern.matcher(ret);
//            if (matcher.find()) {
//                // System.out.println("matcher.group()=" + matcher.group());
//                System.out.println("matcher.group(1)=" + matcher.group(1));
//
//                rowarr = dataProcess(matcher.group(1)).split("\n");
//            }
//
//            if (rowarr == null || rowarr.length == 0) {
//                return Collections.EMPTY_MAP;
//            }
//
//            // 0,"192.168.1.104","78-0F-77-62-47-E0",187216,370170,0,0,
//            // 1,"192.168.1.102","48-3C-0C-74-9B-F0",19400411,1560153,0,0,
//            // 2,"192.168.1.103","70-48-0F-52-ED-C1",22275757,2703201,0,0,
//            // 3,"192.168.1.101","80-0C-67-1F-69-F7",83140867,7985354,558,6963,
//            // 4,"192.168.1.100","38-F9-D3-2E-B6-DF",208460343,76051314,0,78,
//            // 5,"192.168.1.105","B8-FC-9A-3E-6A-DC",450399675,15578890,0,0,
//            // 0,0
//            for (int i = 0; i < rowarr.length; i++) {
//                String colarr[] = rowarr[i].split(",");
//                if (colarr == null || colarr.length != 7) {
//                    continue;
//                }
//                FlowStatisticDto dto = new FlowStatisticDto();
//                dto.setId(Integer.parseInt(colarr[0]));
//                dto.setIp(colarr[1]);
//                dto.setMac(colarr[2]);
//                dto.setDownloadTotal(Long.parseLong(colarr[3]));
//                dto.setUploadTotal(Long.parseLong(colarr[4]));
//                dto.setDownloadSpeed(Long.parseLong(colarr[5]));
//                dto.setUploadSpeed(Long.parseLong(colarr[6]));
//
//                map.put(colarr[2], dto);
//            }
//        } catch (Exception e) {
//            log.error("获取统计数据时出现异常", e);
//        }
//
//        log.info("统计数据：{}", map);
//
//        return map;
//    }
//
//    private Map<String, String> getHostMap() {
//        Map<String, String> map = new LinkedHashMap<>();
//
//        map.putAll(getHostMap(1));
//        map.putAll(getHostMap(2));
//
//        return map;
//    }
//
//    private Map<String, String> getHostMap(int page) {
//        Map<String, String> map = new LinkedHashMap<>();
//
//        try {
//
//            String myurl = "http://192.168.1.253/userRpm/AccessCtrlHostsListsRpm.htm?Page=" + page;
//            Map<String, String> headers = new LinkedHashMap<>();
//            headers.put("Connection", "keep-alive");
//            headers.put("Upgrade-Insecure-Requests", "1");
//            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
//            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            headers.put("Referer", "http://192.168.1.253/userRpm/SystemStatisticRpm.htm?contType=1&sortType=2&autoRefresh=2&Num_per_page=10&Goto_page=1");
//            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//            headers.put("Cookie", "tLargeScreenP=1; subType=pcSub; tPlatform=1; ChgPwdSubTag=; TPLoginTimes=0; Authorization=Basic%20YWRtaW46bHpjMTIxMDI4");
//
//            String ret = HttpUtil.get(myurl, headers);
//            if (ret == null) {
//                return Collections.EMPTY_MAP;
//            }
//
//
//            String rowarr[] = null;
//
//            String regex = "new Array\\((.*?)\\)\\;"; // 这里的(.*?) 会发现有个问号?，它的作用就是懒惰匹配
//            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL); //多行匹配
//            Matcher matcher = pattern.matcher(ret);
//            if (matcher.find()) {
//                //System.out.println("matcher.group()=" + matcher.group());
//                System.out.println("matcher.group(1)=" + matcher.group(1));
//
//                rowarr = dataProcess(matcher.group(1)).split("\n");
//            }
//
//            if (rowarr == null || rowarr.length == 0) {
//                return Collections.EMPTY_MAP;
//            }
//
//            //0, "ali15", "", "", "38-F9-D3-2E-B6-DF",
//            //
//            //0, "ali6s", "", "", "70-48-0F-52-ED-C1",
//            //
//            //0, "leshi", "", "", "B8-FC-9A-3E-6A-DC",
//            //
//            //0, "ali11", "", "", "80-0C-67-1F-69-F7",
//            //
//            //0, "switch", "", "", "78-0F-77-62-47-E0",
//            //
//            //0, "hl", "", "", "48-3C-0C-74-9B-F0",
//            //
//            //0,0
//            for (int i = 0; i < rowarr.length; i++) {
//                String colarr[] = rowarr[i].split(",");
//                if (colarr == null || colarr.length != 5) {
//                    continue;
//                }
//                map.putIfAbsent(dataProcess(colarr[4]), dataProcess(colarr[1]));
//            }
//
//        } catch (Exception e) {
//            log.error("获取主机数据时出现异常", e);
//        }
//
//        log.info("主机数据：{}", map);
//
//
//        return map;
//    }
//
//
//    private Map<String, Integer> getAccessCtrlMap() {
//        Map<String, Integer> map = new LinkedHashMap<>();
//
//        map.putAll(getAccessCtrlMap(1));
//        map.putAll(getAccessCtrlMap(2));
//
//        return map;
//    }
//
//
//    private Map<String, Integer> getAccessCtrlMap(int page) {
//        Map<String, Integer> map = new LinkedHashMap<>();
//
//        try {
//
//            String myurl = "http://192.168.1.253/userRpm/AccessCtrlAccessRulesRpm.htm?Page=" + page;
//            Map<String, String> headers = new LinkedHashMap<>();
//            headers.put("Connection", "keep-alive");
//            headers.put("Upgrade-Insecure-Requests", "1");
//            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
//            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            headers.put("Referer", "http://192.168.1.253/userRpm/SystemStatisticRpm.htm?contType=1&sortType=2&autoRefresh=2&Num_per_page=10&Goto_page=1");
//            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//            headers.put("Cookie", "tLargeScreenP=1; subType=pcSub; tPlatform=1; ChgPwdSubTag=; TPLoginTimes=0; Authorization=Basic%20YWRtaW46bHpjMTIxMDI4");
//
//            String ret = HttpUtil.get(myurl, headers);
//            if (ret == null) {
//                return Collections.EMPTY_MAP;
//            }
//
//
//            String rowarr[] = null;
//
//            String regex = "new Array\\((.*?)\\)\\;"; // 这里的(.*?) 会发现有个问号?，它的作用就是懒惰匹配
//            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL); //多行匹配
//            Matcher matcher = pattern.matcher(ret);
//            if (matcher.find()) {
//                //System.out.println("matcher.group()=" + matcher.group());
//                System.out.println("matcher.group(1)=" + matcher.group(1));
//
//                rowarr = dataProcess(matcher.group(1)).split("\n");
//            }
//
//            if (rowarr == null) {
//                return Collections.EMPTY_MAP;
//            }
//
//            //"ali15上网开关", 0, 255, 255, "ali15", "NA", "NA", 0,
//            //
//            //"乐视上网开关", 2, 255, 255, "leshi", "NA", "NA", 0,
//            //
//            //"乐视晚上9点自动断网", 2, 255, 0, "leshi", "NA", "晚上9点后不让上网", 1,
//            //
//            //0,0
//            for (int i = 0; i < rowarr.length; i++) {
//                String colarr[] = rowarr[i].split(",");
//                if (colarr == null || colarr.length != 8) {
//                    continue;
//                }
//                if (!colarr[6].equals("NA")) {
//                    continue;
//                }
//                map.putIfAbsent(colarr[4], Integer.parseInt(colarr[7]));
//            }
//
//        } catch (Exception e) {
//            log.error("获取控制数据时出现异常", e);
//        }
//
//        log.info("控制数据：{}", map);
//
//
//        return map;
//    }
//
//
//    public boolean setNetWorkSwitch(FlowStatisticDto dto) {
//        Map<String, Integer> ctrlmap = new HashMap<>();
//        ctrlmap.put("ali15", 0);
//        ctrlmap.put("leshi", 1);
//        ctrlmap.put("hlm", 3);
//        ctrlmap.put("ali11", 4);
//        ctrlmap.put("ali6s", 5);
//
//
//        try {
//            Integer ctrlId = ctrlmap.get(dto.getAlias());
//            Assert.notNull(ctrlId, dto.getAlias() + ":不可以进行网络控制");
//
//            String myurl = "http://192.168.1.253/userRpm/AccessCtrlAccessRulesRpm.htm?enable=" + dto.getSwitchCtrl() + "&enableId=" + ctrlId + "&Page=1";
//            Map<String, String> headers = new LinkedHashMap<>();
//            headers.put("Connection", "keep-alive");
//            headers.put("Upgrade-Insecure-Requests", "1");
//            headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
//            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            headers.put("Referer", "http://192.168.1.253/userRpm/SystemStatisticRpm.htm?contType=1&sortType=2&autoRefresh=2&Num_per_page=10&Goto_page=1");
//            headers.put("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
//            headers.put("Cookie", "tLargeScreenP=1; subType=pcSub; tPlatform=1; ChgPwdSubTag=; TPLoginTimes=0; Authorization=Basic%20YWRtaW46bHpjMTIxMDI4");
//
//            String ret = HttpUtil.get(myurl, headers);
//            Assert.notNull(ret, dto.getAlias() + ":网络控制时出现异常");
//
//
//            String rowarr[] = null;
//
//            String regex = "new Array\\((.*?)\\)\\;"; // 这里的(.*?) 会发现有个问号?，它的作用就是懒惰匹配
//            Pattern pattern = Pattern.compile(regex, Pattern.DOTALL); //多行匹配
//            Matcher matcher = pattern.matcher(ret);
//            if (matcher.find()) {
//                // System.out.println("matcher.group()=" + matcher.group());
//                System.out.println("matcher.group(1)=" + matcher.group(1));
//
//                rowarr = dataProcess(matcher.group(1)).split("\n");
//            }
//
//            if (rowarr == null || rowarr.length == 0) {
//                Assert.notNull(ret, dto.getAlias() + ":网络控制时出现异常");
//            }
//
//            Map<String, Integer> map = new HashMap<>();
//
//
//            //"ali15上网开关", 0, 255, 255, "ali15", "NA", "NA", 0,
//            //
//            //"乐视上网开关", 2, 255, 255, "leshi", "NA", "NA", 0,
//            //
//            //"乐视晚上9点自动断网", 2, 255, 0, "leshi", "NA", "晚上9点后不让上网", 1,
//            //
//            //0,0
//            for (int i = 0; i < rowarr.length; i++) {
//                String colarr[] = rowarr[i].split(",");
//                if (colarr == null || colarr.length != 8) {
//                    continue;
//                }
//                if (!colarr[6].equals("NA")) {
//                    continue;
//                }
//                map.putIfAbsent(colarr[4], Integer.parseInt(colarr[7]));
//            }
//
//
//            Integer switchCtrl = map.get(dto.getAlias());
//            Assert.notNull(switchCtrl, dto.getAlias() + ":网络控制时出现异常");
//
//            Assert.isTrue(dto.getSwitchCtrl() == switchCtrl, dto.getAlias() + ":网络控制时出现异常,未达到预期");
//
//
//        } catch (Exception e) {
//            log.error("获取统计数据时出现异常", e);
//            return false;
//        }
//
////        log.info("统计数据：{}", ctrlmap);
//        return true;
//
//    }
//
//
//    /**
//     * 去掉空格，去掉双引号 ，示例 0" -> 0
//     *
//     * @param str
//     * @return
//     */
//    private String dataProcess(String str) {
//        if (null == str) {
//            return null;
//        }
//        str = str.replaceAll("[\"| ]", "");
//        return str;
//    }
//
//    private void mergeMap(Map<String, FlowStatisticDto> statisticDtoMap, Map<String, String> hostMap, Map<String, Integer> accessCtrlMap) {
//
//        statisticDtoMap.forEach((mac, v) -> {
//            String alias = hostMap.get(mac);
//            v.setAlias(alias);
//            v.setSwitchCtrl(accessCtrlMap.get(alias));
//        });
//    }
//}
