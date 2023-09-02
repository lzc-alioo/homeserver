package com.alioo.monitor.md5;

import com.alioo.monitor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * scp pi@raspberrypi:/home/pi/work/build/md5/md5.txt  /Users/alioo/work/gitstudy/homeserver/doc
 */
@Slf4j
public class Md5Check {
    public static void main(String[] args) {
        String md5path = "/Users/alioo/work/gitstudy/homeserver/doc/md5.txt";

        List<String> list = FileUtil.readFile2List(md5path);
        log.info("list size:{}", list.size());


        Set<String> newSet = new HashSet<>();
        list.forEach(str -> {
            if (str == null || str.isEmpty()) {
                return;
            }
            String arr[] = str.split(",");
            String newStr = arr[0] + ":" + arr[1];

            newSet.add(newStr);
        });
        log.info("newSet size:{}", list.size());


        Set<String> newSet2 = new HashSet<>();
        newSet.forEach(str -> {
            if (str == null || str.isEmpty()) {
                return;
            }
            String arr[] = str.split(",");
            String newStr = arr[0];

            newSet2.add(newStr);
        });
        log.info("newSet2 size:{}", list.size());

    }
}
