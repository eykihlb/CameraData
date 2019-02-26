package com.mydao.kkjk.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import java.text.SimpleDateFormat;

import com.mydao.kkjk.config.FTPConfig;
import com.mydao.kkjk.sdk.HvDeviceDataType;
import com.mydao.kkjk.sdk.HvDeviceSDK;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import org.springframework.stereotype.Component;


@Component
public class Utils {

	/**保存一体机大图 */
	public static String SaveBigImage(int image_type, String dir, HvDeviceDataType.CImageInfo image, Date current_time, String plate_no) throws IOException {
		//创建存储目录
		CreatePicDir(dir);
		String ftppath = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		//图片文件名
		String file_name = sdf.format(current_time) + "_" + Integer.toString(image_type) + ".jpg";
		//文件全路径
		String file_path = dir + file_name;

		if(image.pbData != null
				&& image.dwDataLen > 0
				&& image.wWidth > 0
				&& image.wHeigth > 0) {

			byte[] data = image.pbData.getByteArray(0, image.dwDataLen);
			File dest_file = new File(file_path);
			try {
				//本地文件存储
				FileOutputStream out_stream = new FileOutputStream(dest_file);
				out_stream.write(data);
				out_stream.close();
			}catch (Exception e){
				System.out.println("一体机大图文件存储到本地失败！" + e.getMessage());
			}
			/*try {
				//FTP文件服务器存储
				InputStream input = new FileInputStream(dest_file);
				ftppath = FTPUtil.uploadFile(file_name, input,true);
				input.close();
			}catch (Exception e){
				System.out.println("一体机大图文件上传到FTP失败！" + e.getMessage());
			}*/
		} else {
			System.out.println("一体机大图数据异常！");
		}

		return FTPUtil.getPath(file_name,sdf.format(current_time));
	}

	/**保存车型大图(车头/车身/车尾)*/
	/*public static String SaveBigImage2(int image_type, String dir, HvDeviceDataType.CImageInfo image, long dw64TimeMS, int dwCarID) throws IOException {
		//创建存储目录
		CreatePicDir(dir);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date(dw64TimeMS * 1000);
		//图片文件名
		String file_name = sdf.format(dt) + "_" + Integer.toString(image_type) + "_" + Integer.toString(dwCarID) + ".jpg";
		//文件全路径
		String file_path = dir + file_name;
		String ftpPath = "";
		if(image.pbData != null
				&& image.dwDataLen > 0
				&& image.wWidth > 0
				&& image.wHeigth > 0) {

			byte[] data = image.pbData.getByteArray(0, image.dwDataLen);
			File dest_file = new File(file_path);
			try {
				FileOutputStream out_stream = new FileOutputStream(dest_file);
				out_stream.write(data);
				out_stream.close();
			}catch (Exception e){
				System.out.println("车型大图本地存储失败！" + e.getMessage());
			}
			try {
				InputStream input = new FileInputStream(dest_file);
				ftpPath = FTPUtil.uploadFile(file_name, input,true);
				input.close();
			}catch (Exception e){
				System.out.println("车型大图上传到FTP服务器失败！" + e.getMessage());
			}
		} else {
			System.out.println("车型大图数据异常！");
		}

		//return file_path;
		return ftpPath;
	}*/

	/**保存车牌小图*/
	public static String SaveSmallImage(String dir, HvDeviceDataType.CImageInfo image, Date current_time, String plate_no) throws IOException {
		//创建存储目录
		CreatePicDir(dir);
		String ftppath = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String file_name = sdf.format(current_time) + ".bmp";
		String file_path = dir + file_name;
		boolean save_status = false;
		if(image.pbData != null
				&& image.dwDataLen > 0
				&& image.wWidth > 0
				&& image.wHeigth > 0) {

			int len = 1024*500;
			Memory menory = new Memory(len);
			IntByReference r = new IntByReference(len);
			if(HvDeviceSDK._sdk1.HVAPIUTILS_SmallImageToBitmapEx(image.pbData, image.wWidth, image.wHeigth, menory, r) == 0) {

				int size = r.getValue();
				if(size > 0 && size < len) {
					byte[] data=new byte[size];
					menory.read(0, data, 0, size);

					BufferedImage buffer_image = null;
					try{
						buffer_image = ImageIO.read(new ByteArrayInputStream(data));
						if(ImageIO.write(buffer_image, "bmp", new File(file_path))) {
							save_status = true;
						}else{
							System.out.println("车牌小图本地存储失败！");
						}
					} catch(IOException e) {
						System.out.println("小图保存失败！"+e.getMessage());
					}
				}
			}
		}
		if(!save_status){
			System.out.println("小图数据异常！");
		}
		return FTPUtil.getPath(file_name,sdf.format(current_time));
	}

	/**字符串是否是IP*/
	public static boolean IsIP(String ip) {
		if(ip.length() < 7 || ip.length() > 15 || "".equals(ip)) {
			return false;
		}

		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pat = Pattern.compile(rexp);
		Matcher mat = pat.matcher(ip);
		return mat.find();
	}

	/**创建存储图片文件夹 ,不存在就创建*/
	public static void CreatePicDir(String path) {
		String paths[] = {""};

		try {
			String tempPath = new File(path).getCanonicalPath();
			paths = tempPath.split("\\\\");
			if(paths.length==1){
				paths = tempPath.split("/");
			}
		}
		catch (IOException e) {
			System.out.println("切割路径错误");
			e.printStackTrace();
		}

		//判断是否有后缀
		boolean hasType = false;
		if(paths.length>0){
			String tempPath = paths[paths.length-1];
			if(tempPath.length()>0){
				if(tempPath.indexOf(".")>0){
					hasType=true;
				}
			}
		}

		//创建文件夹
		String dir = paths[0];
		for (int i = 0; i < paths.length - (hasType?2:1); i++) {
			try {
				dir = dir + "/" + paths[i + 1];
				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
			}
			catch (Exception e) {
				System.err.println("文件夹创建发生异常");
				e.printStackTrace();
			}
		}
	}

}
