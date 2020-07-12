package com.alioo.monitor.router.dto;

import com.alioo.monitor.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor                //有参构造
public class UnavailableTimeDto {

    private String startTimeStr;
    private String endTimeStr;

    private long startTime;
    private long endTime;


    public UnavailableTimeDto(String startTimeStr, String endTimeStr) {

        this.startTimeStr=startTimeStr;
        this.endTimeStr=endTimeStr;

        startTime=DateTimeUtil.toDateFromStr(DateTimeUtil.getDateString()+" "+startTimeStr+":00").getTime();
        endTime=DateTimeUtil.toDateFromStr(DateTimeUtil.getDateString()+" "+endTimeStr+":00").getTime();

    }
}
