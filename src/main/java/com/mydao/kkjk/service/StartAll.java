package com.mydao.kkjk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @program: kkjk
 * @description: remarl
 * @author: Eyki
 * @create: 2019-01-25 16:11
 **/
@Component
public class StartAll {

    @Autowired
    private Program program;

    @PostConstruct
    public void startAll(){
        //开始采集数据
        program.start();
        //文件数据入库
        program.wjcl();
        //下载文件
        program.ylwj();
    }
}
