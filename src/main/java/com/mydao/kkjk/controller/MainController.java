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
            dsvo.setDvrName(ds.getDvrName());
            dsvo.setCreateTime(ds.getCreateTime());
            dsvo.setId(ds.getId());
            dsvo.setNetSiteNo(ds.getNetSiteNo());
            return om.writeValueAsString(dsvo);
        }else{
            return om.writeValueAsString("NoData!");
        }
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

}
