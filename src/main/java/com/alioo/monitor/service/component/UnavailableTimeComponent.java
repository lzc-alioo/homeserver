package com.alioo.monitor.service.component;

import com.alioo.monitor.service.dto.UnavailableTimeDto;
import com.alioo.monitor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UnavailableTimeComponent {


    @Value("${app.timepath}")
    private String timepath;

    private List<UnavailableTimeDto> list=new ArrayList<>();


    public List<UnavailableTimeDto> getUnavailableTimeList() {

        if (list != null && !list.isEmpty()) {
            return list;
        }

        List<String> tmplist = FileUtil.readFile2List(timepath);
        log.info("readfile timepath:" + timepath, "tmplist=" + tmplist);

        List<UnavailableTimeDto> unavailableTimeDtoList = tmplist.stream()
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
                .sorted()
                .collect(Collectors.toList());

        this.list.clear();
        list.addAll(unavailableTimeDtoList);

        return list;

    }


    public int updateUnavailableTimeList(List<UnavailableTimeDto> list) {

        this.list.clear();
        this.list.addAll(list);

        List<String> tmplist = list.stream()
                .map(dto -> {
                    return dto.getStartTimeStr() + "," + dto.getEndTimeStr();
                })
                .filter(dto -> dto != null)
                .sorted()
                .collect(Collectors.toList());


        FileUtil.writeFile(timepath, tmplist,false);
        log.info("writefile timepath:" + timepath, "tmplist=" + tmplist);

        return tmplist.size();

    }


}
