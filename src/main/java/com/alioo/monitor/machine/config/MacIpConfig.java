package com.alioo.monitor.machine.config;

import com.alibaba.fastjson.JSON;
import com.alioo.monitor.machine.service.domian.Machine;
import com.alioo.monitor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
@Component
public class MacIpConfig {


    @Value("${app.macIpFile}")
    private String macIpFile;


    public static Map<String, Machine> MAC_MACHINE_MAP = new LinkedHashMap<>();
    public static Map<String, Machine> IP_MACHINE_MAP = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        List<String> tmpList = FileUtil.readFile2List(macIpFile);
        log.info("readFile file:" + macIpFile, "tmpList=" + tmpList);
        if (ObjectUtils.isEmpty(tmpList)) {
            return;
        }

        AtomicInteger idx = new AtomicInteger(1);
        MAC_MACHINE_MAP = tmpList.stream().map(str -> {
                    String[] arr = str.trim().split(",");
                    if (arr.length < 2) {
                        return null;
                    }
                    if (arr.length == 2) {
                        return new Machine(arr[0], arr[1], idx.getAndIncrement());
                    } else {
                        return new Machine(arr[0], arr[1], idx.getAndIncrement(), arr[2]);
                    }

                }).filter(obj -> obj != null)
                .collect(Collectors.toMap(Machine::getMac, obj -> obj, (k1, k2) -> k1, LinkedHashMap::new));

        IP_MACHINE_MAP = MAC_MACHINE_MAP.values().stream()
                .collect(Collectors.toMap(Machine::getIp, obj -> obj, (k1, k2) -> k1, LinkedHashMap::new));

        log.info("MACHINE_MAP:{}", JSON.toJSONString(MAC_MACHINE_MAP.entrySet()));
        log.info("IP_MACHINE_MAP:{}", JSON.toJSONString(IP_MACHINE_MAP.entrySet()));
    }


    public static String getNameAliasByMac(String mac, String defaultName) {
        if (!StringUtils.isEmpty(MAC_MACHINE_MAP.get(mac))) {
            return MAC_MACHINE_MAP.get(mac).getNameAlias();
        }
        if (!StringUtils.isEmpty(defaultName)) {
            return defaultName;
        }

        return mac.replaceAll("\\:", "");
    }

    public static int getOrder(String mac, int defaultOrder) {

        return Optional.ofNullable(MAC_MACHINE_MAP.get(mac)).map(machine -> machine.getOrder()).orElse(defaultOrder);
    }

    public static String getNameAliasByIp(String ip, String defaultName) {
        Machine machine = IP_MACHINE_MAP.get(ip);
        if (machine != null) {
            return machine.getNameAlias();
        }
        if (!StringUtils.isEmpty(defaultName)) {
            return defaultName;
        }

        return ip;
    }


}
