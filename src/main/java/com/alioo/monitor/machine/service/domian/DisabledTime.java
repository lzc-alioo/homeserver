package com.alioo.monitor.machine.service.domian;

import com.alioo.monitor.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor                //有参构造
public class DisabledTime implements Comparable<DisabledTime> {

    private String startTimeStr;
    private String endTimeStr;
    /**
     * true:生效；false：不生效
     */
    private boolean checked;

    private long startTime;
    private long endTime;


    public DisabledTime(String startTimeStr, String endTimeStr,String checkedStr) {

        this.startTimeStr = startTimeStr;
        this.endTimeStr = endTimeStr;
        this.checked = "1".equals(checkedStr) ? true : false;;

        startTime = DateTimeUtil.toDateFromStr(DateTimeUtil.getDateString() + " " + startTimeStr + ":00").getTime();
        endTime = DateTimeUtil.toDateFromStr(DateTimeUtil.getDateString() + " " + endTimeStr + ":00").getTime();

    }

    public String getCheckedStr(){
        return checked ? "1" : "0";
    }

    @Override
    public int compareTo(DisabledTime o) {
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
                ", checked=" + checked +
                '}';
    }
}
