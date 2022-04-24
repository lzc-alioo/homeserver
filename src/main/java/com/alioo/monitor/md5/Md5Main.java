package com.alioo.monitor.md5;

import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.FileUtil;
import com.alioo.monitor.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class Md5Main {

    @Value("${app.md5path}")
    private String md5path;

    @Value("${app.md5pattern:0000}")
    private String md5pattern;

    private static final String number = "0123456789";
    private static final String lower = "abcdefghijklmnopqrstuvwxyz";
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String special = "!@#$%^&*()_-=+";

    private static final String lower_number = lower + number;


    @PostConstruct
    public void process() {
        log.info("md5_start_data");

        List<String> cursorList = FileUtil.readFile2List(md5path + "cursor.txt");
        int i_old = 0;
        int j_old = 0;
        int k_old = 0;
        int l_old = 0;
        int m_old = 0;
        int n_old = 0;
        int o_old = 0;
        if (!ObjectUtils.isEmpty(cursorList) && !ObjectUtils.isEmpty(cursorList.get(0))) {
            String[] cursor = cursorList.get(0).split(",");
            i_old = Integer.parseInt(cursor[0]);
            j_old = Integer.parseInt(cursor[1]);
            k_old = Integer.parseInt(cursor[2]);
            l_old = Integer.parseInt(cursor[3]);
            m_old = Integer.parseInt(cursor[4]);
            n_old = Integer.parseInt(cursor[5]);
            o_old = Integer.parseInt(cursor[6]);
        }
        String cursor = i_old + "," + j_old + "," + k_old + "," + l_old + "," + m_old + "," + n_old + "," + o_old;
        log.info("md5_data_cursor:{}", cursor);

        long tmp = 0;
        int len = lower_number.length();

        for (int i = 0; i < len; i++) {
            if (i < i_old) {
                continue;
            }

            for (int j = 0; j < len; j++) {
                if (i <= i_old && j < j_old) {
                    continue;
                }

                for (int k = 0; k < len; k++) {
                    if (i <= i_old && j <= j_old && k < k_old) {
                        continue;
                    }

                    for (int l = 0; l < len; l++) {
                        if (i <= i_old && j <= j_old && k <= k_old && l < l_old) {
                            continue;
                        }

                        for (int m = 0; m < len; m++) {
                            if (i <= i_old && j <= j_old && k <= k_old && l <= l_old && m < m_old) {
                                continue;
                            }

                            for (int n = 0; n < len; n++) {
                                if (i <= i_old && j <= j_old && k <= k_old && l <= l_old && m <= m_old && n < n_old) {
                                    continue;
                                }

                                for (int o = 0; o < len; o++) {
                                    if (i <= i_old && j <= j_old && k <= k_old && l <= l_old && m <= m_old && n <= n_old && o < o_old) {
                                        continue;
                                    }

                                    doOne(tmp, i, j, k, l, m, n, o);

                                    tmp++;
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("md5_end_data");
    }

    private void doOne(long tmp, int i, int j, int k, int l, int m, int n, int o) {
        String str = new StringBuffer().append(lower_number.charAt(i)).append(lower_number.charAt(j)).append(lower_number.charAt(k)).append(lower_number.charAt(l)).append(lower_number.charAt(m)).append(lower_number.charAt(n)).append(lower_number.charAt(o)).toString();
        String md5 = MD5Util.md5(str);

        if (md5.startsWith(md5pattern)) {
            String cursor = i + "," + j + "," + k + "," + l + "," + m + "," + n + "," + o;
            log.info("md5_data:{},val:{},cursor:{}", str, md5, cursor);

            FileUtil.writeFile(md5path + "md5.txt", Arrays.asList(md5+","+str+","+ DateTimeUtil.getDateTimeString("yyyyMMddHHmmss")), true);
        }


        if (tmp % 1_000_000 == 0) {
            String cursor = i + "," + j + "," + k + "," + l + "," + m + "," + n + "," + o;
            log.info("md5_temp_data:{},val:{},cursor:{}", str, md5, cursor);

            FileUtil.writeFile(md5path + "cursor.txt", Arrays.asList(cursor), false);
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
