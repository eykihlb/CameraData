package com.mydao.kkjk.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydao.kkjk.dao.CodeVideoCameraMapper;
import com.mydao.kkjk.dao.DataSnapLaneMapper;
import com.mydao.kkjk.dao.DataSnapMapper;
import com.mydao.kkjk.device.HvDevice;
import com.mydao.kkjk.entity.CodeVideoCamera;
import com.mydao.kkjk.entity.DataSnap;
import com.mydao.kkjk.entity.DataSnapLane;
import com.mydao.kkjk.utils.CharsetUtil;
import com.mydao.kkjk.vo.DSVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @program: kkjk
 * @description: 摄像机管理
 * @author: Eyki
 * @create: 2019-01-28 10:09
 **/
@RestController
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private DataSnapMapper dataSnapMapper;

    @Autowired
    private DataSnapLaneMapper dataSnapLaneMapper;

    @Autowired
    private CodeVideoCameraMapper codeVideoCameraMapper;

    ObjectMapper om = new ObjectMapper();

    /**
     * 根据车牌号查询记录
     * @param plateNo
     * @return
     * @throws Exception
     */
    @GetMapping("/detail")
    public String detail(@RequestParam String plateNo) throws Exception{
        //plateNo = new String(plateNo .getBytes("UTF-8"),"GB2312");
        //log.info(plateNo);
        DataSnapLane ds = dataSnapLaneMapper.selectByPlateNo(plateNo);
        log.info(plateNo);
        DSVO dsvo = new DSVO();
        if (ds!=null){
            dsvo.setDvrName(new String(ds.getDvrName().getBytes("GBK"), "GB2312"));
            dsvo.setCreateTime(ds.getCreateTime());
            dsvo.setId(ds.getId());
            dsvo.setNetSiteNo(ds.getNetSiteNo());
            return om.writeValueAsString(dsvo);
        }else{
            return om.writeValueAsString("NoData！");
        }
    }

    @PostMapping("/test")
    public String test(@RequestParam String param){
        log.info(param);
        return param;
    }
    /**
     * 更新过车记录
     * @param id
     * @return
     */
    @PostMapping("/update")
    public String update(@RequestParam String id) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        DataSnapLane ds = new DataSnapLane();
        ds.setId(id);
        ds.setState("1");
        if (dataSnapLaneMapper.updateByPrimaryKeySelective(ds)>0){
            resultMap.put("status","1");
            resultMap.put("msg","success");
            return om.writeValueAsString(resultMap);
        }
        resultMap.put("status","0");
        resultMap.put("msg","error");
        return om.writeValueAsString(resultMap);
    }

    /**
     * 添加摄像机
     * @param codeVideoCamera
     * @return
     */
    @PostMapping("/insertCamera")
    public String insert(@RequestBody CodeVideoCamera codeVideoCamera) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        codeVideoCamera.setId(UUID.randomUUID().toString().replaceAll("-",""));
        //codeVideoCamera.setState("1");
        if (codeVideoCameraMapper.insertSelective(codeVideoCamera)>0){
            resultMap.put("status","1");
            resultMap.put("msg","success");
            return om.writeValueAsString(resultMap);
        }
        resultMap.put("status","0");
        resultMap.put("msg","error");
        return om.writeValueAsString(resultMap);
    }

    /**
     * 更新摄像机状态
     * @param codeVideoCamera
     * @return
     * @throws Exception
     */
    @PostMapping("/updateCameraState")
    public String updateCamera(@RequestBody CodeVideoCamera codeVideoCamera) throws  Exception{
        Map<String,Object> resultMap = new HashMap<>();
        if (codeVideoCameraMapper.updateByPrimaryKeySelective(codeVideoCamera)>0){
            resultMap.put("status","1");
            resultMap.put("msg","success");
            return om.writeValueAsString(resultMap);
        }
        resultMap.put("status","0");
        resultMap.put("msg","error");
        return om.writeValueAsString(resultMap);
    }

    @PostMapping("/getList")
    public String getList(@RequestBody CodeVideoCamera codeVideoCamera) throws Exception{
        Map<String,Object> resultMap = new HashMap<>();
        codeVideoCamera.setBeginRow(codeVideoCamera.getPageIndex()*codeVideoCamera.getPageSize()-codeVideoCamera.getPageSize()+1);
        codeVideoCamera.setEndRow(codeVideoCamera.getBeginRow()+codeVideoCamera.getPageSize());
        List<CodeVideoCamera> lc = codeVideoCameraMapper.selectList(codeVideoCamera);
        Integer count = codeVideoCameraMapper.count(codeVideoCamera);
        resultMap.put("list",lc);
        resultMap.put("count",count);
        resultMap.put("status","1");
        resultMap.put("msg","success");
        return om.writeValueAsString(resultMap);
    }


    public static String gb2312ToUtf8(String str) {

        String urlEncode ="";

        try {

            urlEncode = URLEncoder.encode (str, "UTF-8" );

        } catch (Exception e) {

            e.printStackTrace();

        }

        return urlEncode;

    }

    public static String utf8Togb2312(String str){

        StringBuffer sb = new StringBuffer();

        for ( int i=0; i<str.length(); i++) {

            char c = str.charAt(i);

            switch (c) {

               case 'c':

                sb.append(" " );

                break ;

               case '%':

                try {

                    sb.append(( char )Integer.parseInt (

                            str.substring(i+1,i+3),16));

                }

                catch (NumberFormatException e) {

                    throw new IllegalArgumentException();

                }

                i += 2;

                break ;

                default :

                    sb.append(c);

                    break ;

            }

        }

        String result = sb.toString();

        String res= null ;

        try {

            byte [] inputBytes = result.getBytes( "8859_1" );

            res= new String(inputBytes, "UTF-8");

        }

        catch (Exception e){}

        return res;

    }

    public static void main(String[] args) throws Exception{
        String gStr= new String("大萨达".getBytes("GBK"), "GB2312");
        String gStr1= new String("大萨达".getBytes("GB2312"), "GBK");
        System.out.println(gStr);
        System.out.println(gStr1);
    }


}
