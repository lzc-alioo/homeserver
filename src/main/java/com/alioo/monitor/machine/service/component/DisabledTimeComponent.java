package com.alioo.monitor.machine.service.component;

import com.alioo.monitor.machine.service.domian.DisabledTime;
import com.alioo.monitor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DisabledTimeComponent {


    @Value("${app.timepath}")
    private String timepath;

    //    private List<DisabledTime> list = new ArrayList<>();
    private Map<String, List<DisabledTime>> map = new HashMap<>();


    public List<DisabledTime> getDisabledTimeList(String group) {

        if (!ObjectUtils.isEmpty(map)) {
            List<DisabledTime> list = map.getOrDefault(group, Collections.EMPTY_LIST);
            if (!ObjectUtils.isEmpty(list)) {
                return list;
            }
        }

//        tv.txt example
//        19:40,20:00,1
//        20:01,20:03,0
//        21:23,23:59,1
        String file = timepath + group + ".txt";
        List<String> tmpList = FileUtil.readFile2List(file);
        log.info("readFile file:" + file, "tmpList=" + tmpList);

        List<DisabledTime> disabledTimeList = tmpList.stream().map(str -> {
            String[] arr = str.split(",");
            if (arr.length != 3) {
                return null;
            }
            String startTimeStr = arr[0];
            String endTimeStr = arr[1];
            String checkedStr = arr[2];

            return new DisabledTime(startTimeStr, endTimeStr, checkedStr);

        }).filter(dto -> dto != null).sorted().collect(Collectors.toList());

//        this.list.clear();
//        list.addAll(disabledTimeList);
        map.put(group, disabledTimeList);

        return disabledTimeList;

    }


    public int updateUnavailableTimeList(String machine, List<DisabledTime> list) {

//        this.list.clear();
//        this.list.addAll(list);
        map.put(machine, list);

        List<String> tmpList = list.stream().map(dto -> {
            return dto.getStartTimeStr() + "," + dto.getEndTimeStr() + "," + dto.getCheckedStr();
        }).filter(dto -> dto != null).sorted().collect(Collectors.toList());

        String file = timepath + File.separator + machine + ".txt";
        FileUtil.writeFile(file, tmpList, false);
        log.info("writeFile file:" + file, "tmpList=" + tmpList);

        return tmpList.size();

    }


}
