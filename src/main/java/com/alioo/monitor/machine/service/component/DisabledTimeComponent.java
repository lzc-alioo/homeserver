package com.alioo.monitor.machine.service.component;

import com.alioo.monitor.machine.service.domian.DisabledTime;
import com.alioo.monitor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DisabledTimeComponent {


    @Value("${app.timepath}")
    private String timepath;

    private List<DisabledTime> list = new ArrayList<>();


    public List<DisabledTime> getDisabledTimeList() {

        if (list != null && !list.isEmpty()) {
            return list;
        }

//        time.txt example
//        19:40,20:00,1
//        20:01,20:03,0
//        21:23,23:59,1
        List<String> tmplist = FileUtil.readFile2List(timepath);
        log.info("readfile timepath:" + timepath, "tmplist=" + tmplist);

        List<DisabledTime> disabledTimeList = tmplist.stream()
                .map(str -> {
                    String[] arr = str.split(",");
                    if (arr.length != 3) {
                        return null;
                    }
                    String startTimeStr = arr[0];
                    String endTimeStr = arr[1];
                    String checkedStr = arr[2];

                    return new DisabledTime(startTimeStr, endTimeStr,checkedStr);

                })
                .filter(dto -> dto != null)
                .sorted()
                .collect(Collectors.toList());

        this.list.clear();
        list.addAll(disabledTimeList);

        return list;

    }


    public int updateUnavailableTimeList(List<DisabledTime> list) {

        this.list.clear();
        this.list.addAll(list);

        List<String> tmplist = list.stream()
                .map(dto -> {
                    return dto.getStartTimeStr() + "," + dto.getEndTimeStr()+","+ dto.getCheckedStr();
                })
                .filter(dto -> dto != null)
                .sorted()
                .collect(Collectors.toList());


        FileUtil.writeFile(timepath, tmplist, false);
        log.info("writefile timepath:" + timepath, "tmplist=" + tmplist);

        return tmplist.size();

    }


}
