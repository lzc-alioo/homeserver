package com.alioo.monitor.homeserver;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;

@Slf4j
public class DirectoryTest {

    String homePath = "/Users/alioo/nfs/";

    @Test
    public void testCreateDirectory() {

        mkdir16(homePath);
    }


    private void mkdir16(String path) {
        if(path.substring(homePath.length()).split("/").length==3){
            return;
        }

        for (int i = 0; i < 16; i++) {
            String subPath = Integer.toHexString(i);
            String newPath = path + subPath + "/";
            mkdir(newPath);

            mkdir16(newPath);
        }
    }

    private void mkdir(String path) {
        boolean flag=new File(path).mkdirs();
        log.info("mkdir "+path+":"+flag);
    }

}
