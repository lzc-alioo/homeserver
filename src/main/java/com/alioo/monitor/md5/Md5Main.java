package com.alioo.monitor.md5;

import com.alioo.monitor.util.DateTimeUtil;
import com.alioo.monitor.util.FileUtil;
import com.alioo.monitor.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Value("${app.md5sleep:10}")
    private long md5sleep;

    private static final String number = "0123456789";
    private static final String lower = "abcdefghijklmnopqrstuvwxyz";
    private static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String special = "!@#$%^&*()_-=+";


    @PostConstruct
    public void init() {
        //原始字符串
        String lower_number = lower + number;

        //独立线程处理md5计算逻辑
        new Thread("md5thread") {
            @Override
            public void run() {
                process(lower_number);
            }
        }.start();

    }


//    public void process2(String lower_number) {
//        log.info("md5_start_data");
//
//        List<String> cursorList = FileUtil.readFile2List(md5path + "cursor.txt");
//        int cursor[] = new int[8];
//        int newCursor[] = new int[8];
//
//
//        if (!ObjectUtils.isEmpty(cursorList) && !ObjectUtils.isEmpty(cursorList.get(0))) {
//            String[] cursorStr = cursorList.get(0).split(",");
//            for (int i = 0; i < cursorStr.length; i++) {
//                cursor[i] = Integer.parseInt(cursorStr[i]);
//                newCursor[i] = Integer.parseInt(cursorStr[i]);
//            }
//        }
//
//        log.info("md5_data_cursor:{}", Arrays.toString(cursor));
//
//
//        long tmp = 0;
//        int len = lower_number.length();
//
//
//        while (true) {
//
//
//            if (newCursor[0] < cursor[0]) {
//                continue;
//            }
//            if (newCursor[7] < len) {
//                doOne(lower_number, tmp, newCursor);
//            } else {
//                newCursor[7] = 0;
//                continue;
//            }
//
//
//        }
//
//
//        for (  int i = 0;  i < len; i++) {
//            if (i < cursor[0]) {
//                continue;
//            }
//
//            for (int j = 0; j < len; j++) {
//                if (i <= cursor[0] && j < cursor[1]) {
//                    continue;
//                }
//
//                for (int k = 0; k < len; k++) {
//                    if (i <= cursor[0] && j <= cursor[1] && k < cursor[2]) {
//                        continue;
//                    }
//
//                    for (int l = 0; l < len; l++) {
//                        if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l < cursor[3]) {
//                            continue;
//                        }
//
//                        for (int m = 0; m < len; m++) {
//                            if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m < cursor[4]) {
//                                continue;
//                            }
//
//                            for (int n = 0; n < len; n++) {
//                                if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m <= cursor[4] && n < cursor[5]) {
//                                    continue;
//                                }
//
//                                for (int o = 0; o < len; o++) {
//                                    if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m <= cursor[4] && n <= cursor[5] && o < cursor[6]) {
//                                        continue;
//                                    }
//
//                                    for (int p = 0; p < len; p++) {
//                                        if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m <= cursor[4] && n <= cursor[5] && o <= cursor[6] && p < cursor[7]) {
//                                            continue;
//                                        }
//                                        int newCursor[] = new int[8];
//                                        int idx = 0;
//                                        newCursor[idx++] = i;
//                                        newCursor[idx++] = j;
//                                        newCursor[idx++] = k;
//                                        newCursor[idx++] = l;
//                                        newCursor[idx++] = m;
//                                        newCursor[idx++] = n;
//                                        newCursor[idx++] = o;
//                                        newCursor[idx++] = p;
//
//                                        doOne(lower_number, tmp, newCursor);
//
//                                        tmp++;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        log.info("md5_end_data");
//    }

    public void process(String lower_number) {
        log.info("md5_start_data");

        List<String> cursorList = FileUtil.readFile2List(md5path + "cursor.txt");
        int cursor[] = new int[8];


        if (!ObjectUtils.isEmpty(cursorList) && !ObjectUtils.isEmpty(cursorList.get(0))) {
            String[] cursorStr = cursorList.get(0).split(",");
            for (int i = 0; i < cursorStr.length; i++) {
                cursor[i] = Integer.parseInt(cursorStr[i]);
            }
        }

        log.info("md5_data_cursor:{}", Arrays.toString(cursor));

        long tmp = 0;
        int len = lower_number.length();

        for (int i = 0; i < len; i++) {
            if (i < cursor[0]) {
                continue;
            }

            for (int j = 0; j < len; j++) {
                if (i <= cursor[0] && j < cursor[1]) {
                    continue;
                }

                for (int k = 0; k < len; k++) {
                    if (i <= cursor[0] && j <= cursor[1] && k < cursor[2]) {
                        continue;
                    }

                    for (int l = 0; l < len; l++) {
                        if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l < cursor[3]) {
                            continue;
                        }

                        for (int m = 0; m < len; m++) {
                            if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m < cursor[4]) {
                                continue;
                            }

                            for (int n = 0; n < len; n++) {
                                if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m <= cursor[4] && n < cursor[5]) {
                                    continue;
                                }

                                for (int o = 0; o < len; o++) {
                                    if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m <= cursor[4] && n <= cursor[5] && o < cursor[6]) {
                                        continue;
                                    }

                                    for (int p = 0; p < len; p++) {
                                        if (i <= cursor[0] && j <= cursor[1] && k <= cursor[2] && l <= cursor[3] && m <= cursor[4] && n <= cursor[5] && o <= cursor[6] && p < cursor[7]) {
                                            continue;
                                        }
                                        int newCursor[] = new int[8];
                                        int idx = 0;
                                        newCursor[idx++] = i;
                                        newCursor[idx++] = j;
                                        newCursor[idx++] = k;
                                        newCursor[idx++] = l;
                                        newCursor[idx++] = m;
                                        newCursor[idx++] = n;
                                        newCursor[idx++] = o;
                                        newCursor[idx++] = p;

                                        doOne(lower_number, tmp, newCursor);

                                        tmp++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        log.info("md5_end_data");
    }

    private void doOne(String lower_number, long tmp, int[] newCursor) {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < newCursor.length; i++) {
            str.append(lower_number.charAt(newCursor[i]));
        }
        String md5 = MD5Util.md5(str.toString());

        if (md5.startsWith(md5pattern)) {
            log.info("md5_data:{},val:{},cursor:{}", str, md5, Arrays.toString(newCursor));
            FileUtil.writeFile(md5path + "md5.txt", Arrays.asList(md5 + "," + str + "," + DateTimeUtil.getDateTimeString("yyyyMMddHHmmss")), true);
        }


        if (tmp % 1_000_000 == 0) {
            log.info("md5_temp_data:{},val:{},cursor:{}", str, md5, Arrays.toString(newCursor));

            FileUtil.writeFile(md5path + "cursor.txt", Arrays.asList(StringUtils.join(newCursor, ',')), false);
            try {
                Thread.sleep(md5sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
