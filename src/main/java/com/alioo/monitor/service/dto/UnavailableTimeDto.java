package com.alioo.monitor.service.dto;

import com.alioo.monitor.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor                //有参构造
public class UnavailableTimeDto implements Comparable<UnavailableTimeDto> {

    private String startTimeStr;
    private String endTimeStr;

    private long startTime;
    private long endTime;


    public UnavailableTimeDto(String startTimeStr, String endTimeStr) {

        this.startTimeStr = startTimeStr;
        this.endTimeStr = endTimeStr;

        startTime = DateTimeUtil.toDateFromStr(DateTimeUtil.getDateString() + " " + startTimeStr + ":00").getTime();
        endTime = DateTimeUtil.toDateFromStr(DateTimeUtil.getDateString() + " " + endTimeStr + ":00").getTime();

    }

    @Override
    public int compareTo(UnavailableTimeDto o) {
        int tmp = this.getStartTimeStr().compareTo(o.getStartTimeStr());
        if (tmp != 0) {
            return tmp;
        } else {
            return this.getEndTimeStr().compareTo(o.getEndTimeStr());
        }


    }

    @Override
    public String toString() {
        return "{" +
                "startTimeStr='" + startTimeStr + '\'' +
                ", endTimeStr='" + endTimeStr + '\'' +
                '}';
    }
}
