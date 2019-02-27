package com.mydao.kkjk.service;


import com.google.gson.Gson;
import com.mydao.kkjk.config.FTPConfig;
import com.mydao.kkjk.dao.CodeVideoCameraMapper;
import com.mydao.kkjk.dao.DataSnapLaneMapper;
import com.mydao.kkjk.dao.DataSnapMapper;
import com.mydao.kkjk.device.HvDevice;
import com.mydao.kkjk.entity.CodeVideoCamera;
import com.mydao.kkjk.entity.DataSnap;
import com.mydao.kkjk.entity.DataSnapLane;
import com.mydao.kkjk.event.MatchInfo;
import com.mydao.kkjk.event.ResultInfo;
import com.mydao.kkjk.ui.frmMain;
import com.mydao.kkjk.utils.FTPUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RestController
public class Program {

	@Autowired
	private FTPConfig ftpConfig;
	@Autowired
	private CodeVideoCameraMapper codeVideoCameraMapper;
	@Autowired
	private DataSnapLaneMapper dataSnapLaneMapper;
	@Autowired
	private DataSnapMapper dataSnapMapper;
	private static frmMain frm;
	HvDevice hd = new HvDevice();
	HvDevice hd1 = new HvDevice();
	HvDevice hd2 = new HvDevice();
	HvDevice hd3 = new HvDevice();
	HvDevice hd4 = new HvDevice();
	@Async
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//frm = new frmMain();
					CodeVideoCamera c = new CodeVideoCamera();
					c.setByRes(ftpConfig.getNetNo());
					c.setState("1");
					List<CodeVideoCamera> list = codeVideoCameraMapper.selectCameraIP(c);
					for (int i = 0;i<list.size();i++){
						switch (i){
							case 0:
								hd.OpenDevice(list.get(i).getDvrIp(), 0);
								hd.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 1:
								hd.OpenDevice(list.get(i).getDvrIp(), 0);
								hd.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 2:
								hd1.OpenDevice(list.get(i).getDvrIp(), 0);
								hd1.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 3:
								hd1.OpenDevice(list.get(i).getDvrIp(), 0);
								hd1.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 4:
								hd2.OpenDevice(list.get(i).getDvrIp(), 0);
								hd2.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 5:
								hd2.OpenDevice(list.get(i).getDvrIp(), 0);
								hd2.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 6:
								hd3.OpenDevice(list.get(i).getDvrIp(), 0);
								hd3.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 7:
								hd3.OpenDevice(list.get(i).getDvrIp(), 0);
								hd3.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 8:
								hd4.OpenDevice(list.get(i).getDvrIp(), 0);
								hd4.StartResult(ftpConfig,dataSnapMapper);
								break;
							case 9:
								hd4.OpenDevice(list.get(i).getDvrIp(), 0);
								hd4.StartResult(ftpConfig,dataSnapMapper);
								break;
							default:
								break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 关闭所有摄像头链接，停止数据接收服务
	 * @return
	 */
	@GetMapping("/closeAll")
	public String closeAll(){
		try {
			hd.StopResult();
			hd.CloseDevice();
			hd1.StopResult();
			hd1.CloseDevice();
			hd2.StopResult();
			hd2.CloseDevice();
			hd3.StopResult();
			hd3.CloseDevice();
			hd4.StopResult();
			hd4.CloseDevice();
		}catch (Exception e){
			return  e.getMessage();
		}
		return "success";
	}

	/**
	 * 从FTP上下载未成功入库的数据
	 */
	@Async
	public void ylwj(){
		System.out.println("开始下载文件！");
		do {
			try {
				//System.out.println(fTPConfig.getFtpHost());
				FTPUtil.downloadFtpFile();
				Thread.sleep(10000);
			}catch (Exception e){
				System.out.println(e.getMessage());
				System.out.println("文件下载失败！");
			}
		}while (true);
	}

	/**
	 * 下载到本地未入库的数据进行入库操作
	 */
	@Async
	public void wjcl(){
		System.out.println("开始文件处理！");
		do {
			try {
				StringBuilder result;
				File file = new File(ftpConfig.getLocalPath());
				File[] files = file.listFiles();
				DataSnap ds;
				DataSnapLane dsl;
				for (File f : files) {
					result = new StringBuilder();
					ds = new DataSnap();
					dsl = new DataSnapLane();
					try{
						Reader r = new FileReader(f);
						BufferedReader br = new BufferedReader(r);//构造一个BufferedReader类来读取文件
						String s = null;
						while((s = br.readLine())!=null){//使用readLine方法，一次读一行
							result.append(System.lineSeparator()+s);
						}
						Gson gson = new Gson();
						Map<String,Object> map = gson.fromJson(result.toString(),Map.class);

						CodeVideoCamera c = new CodeVideoCamera();
						c.setDvrIp(map.get("cameraIp").toString());
						c.setByRes(ftpConfig.getNetNo());
						String a = codeVideoCameraMapper.selectByIp(c);
						if ("1".equals(a)){//车道
							ds.setState("0");
							ds.setPlateNo(map.get("plateNo").toString());
							ds.setPlateColorCode(map.get("plateColorCode").toString());
							ds.setPicUrl(map.get("picUrl").toString());
							ds.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							ds.setCameraIp(map.get("cameraIp").toString());
							ds.setNetSiteNo(ftpConfig.getNetNo());
							ds.setId(UUID.randomUUID().toString().replaceAll("-", ""));
							r.close();
							br.close();
							if (dataSnapMapper.insertSelective(ds)>0){//文件处理完成，删除文件
								f.delete();
							}
						}else{//匝道
							dsl.setState("0");
							ds.setCarId(map.get("carId").toString());
							dsl.setPlateNo(map.get("plateNo").toString());
							dsl.setPlateColorCode(map.get("plateColorCode").toString());
							dsl.setPicUrl(map.get("picUrl").toString());
							dsl.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
							dsl.setCameraIp(map.get("cameraIp").toString());
							dsl.setNetSiteNo(ftpConfig.getNetNo());
							r.close();
							br.close();
							dsl.setId(UUID.randomUUID().toString().replaceAll("-", ""));
							if (dataSnapLaneMapper.insertSelective(dsl)>0){//文件处理完成，删除文件
								f.delete();
							}
						}
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				Thread.sleep(10000);
			}catch (Exception e){
				System.out.println(e.getMessage());
			}
		}while (true);
	}

	public static void ResultInfoDelegate(ResultInfo result) {
		frm.ShowResultInfo(result);
	}

	public static void MatchInfoDelegate(MatchInfo match) {
		frm.ShowMatchInfo(match);
	}
}
