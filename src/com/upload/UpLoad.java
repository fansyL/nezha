package com.upload;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.activation.MimetypesFileTypeMap;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifDirectory;

import net.sf.json.JSONObject;

public class UpLoad {

	/**
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//图片路径
		String filepath = "E:\\";
		//图片请求接口地址
		String urlStr = "http://ptpf.host/uploadImg";
		//图片内容请求接口地址
		String urlStr2 = "http://ptpf.host/api/saveImgInfo";

		upImage(filepath, urlStr, urlStr2);
	}

	private static void upImage(String filepath, String urlStr, String urlStr2) {

		File file = new File(filepath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File[] fileChild = files[i].listFiles();
				if (fileChild.length > 1) {
					List<PictureBean> list = new ArrayList<>();
					String time = "";
					String[] txtC = null;
					for (int j = 0; j < fileChild.length; j++) {

						String str = fileChild[j].getAbsoluteFile() + "";
						if (str.contains(".jpg")) {
							System.out.println(str);
							time = qKong(getImageDate(str));

							time = getTime(time);

							Map<String, String> fileMap = new HashMap<String, String>();
							fileMap.put("images", str);

							String ret = formUpload(urlStr, null, fileMap);
							System.out.println(fileChild[j].getAbsolutePath() + " " + ret);
							JSONObject jsonObject = JSONObject.fromObject(ret);

							ResponseBean response = (ResponseBean) jsonObject.toBean(jsonObject, ResponseBean.class);
							
							list.add(new PictureBean(response.getSrc(), ""));
						} else if (str.contains(".txt")) {
							txtC = txt2String(fileChild[j].getAbsoluteFile());
						}
					}
					if (txtC != null && txtC.length == 2) {
						RequestBean request = new RequestBean(txtC[0], txtC[1], 99, 99, "99", "5", list, "999", time);

						JSONObject json = JSONObject.fromObject(request);

						Map<String, String> textMap = new HashMap<String, String>();
						textMap.put("json", json.toString());
						String ret1 = formJson(urlStr2, textMap, null);
//						System.out.println(files[i].getAbsolutePath() + " " + ret1);
						System.out.println(ret1);
					}

				}

			}

		}

	}

	private static String getTime(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		try {
			Date date_util = sdf.parse(time);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return formatter.format(date_util);

		} catch (ParseException e) {
			Date currentTime = new Date();
    	     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    String dateString = formatter.format(currentTime);
    	    return dateString;
		} // 转换为util.date
	}

	/**
	 * 去掉字符串开头的空格
	 * 
	 * @param s
	 * @return
	 */
	public static String qKong(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != ' ') {
				s = s.substring(i, s.length());
				break;
			}
		}
		return s;
	}

	/**
	 * 遍历文件夹
	 * 
	 * @param path
	 */
	public static void traverseFolder(String path) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						System.out.println("文件夹:" + file2.getAbsolutePath());
						traverseFolder(file2.getAbsolutePath());
					} else {
						System.out.println("文件:" + file2.getAbsolutePath());
						if (file2.getAbsolutePath().contains(".txt")) {
							System.out.println(txt2String(file2));
						}
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}

	/**
	 * 获取图片拍摄时间
	 * 
	 * @return
	 */
	public static String getImageDate(String filepath) {
		String date = "";
		Metadata metadata;
		try {
			metadata = JpegMetadataReader.readMetadata(new File(filepath));
			Directory exif = metadata.getDirectory(ExifDirectory.class);

			Iterator tags = exif.getTagIterator();
			// System.out.println(tags.next());
			while (tags.hasNext()) {
				String str = tags.next() + "";

				if (str.contains("Date/Time")) {
					// String str = tag.toString();
					System.out.println();
					return str.split("-")[1];
				}
			}
		} catch (JpegProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;

	}

	public static String[] txt2String(File file) {
		StringBuilder result = new StringBuilder();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "gbk");
			BufferedReader br = new BufferedReader(read);// 构造一个BufferedReader类来读取文件
			String s = null;
			int i = 0;
			while ((s = br.readLine()) != null && i < 2) {// 使用readLine方法，一次读一行
				if (i == 1) {
					result.append(System.lineSeparator() + s);
				}
				i++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(result.toString());

		String str = result.toString();

		String[] arr = str.split("\\s+");

		System.out.println(arr[arr.length - 2]);
		String[] txtC = new String[2];
		txtC[0] = arr[arr.length - 2];
		txtC[1] = arr[arr.length - 1].substring(1, arr[arr.length - 1].length());
		System.out.println(txtC[1]);
		return txtC;
	}

	/**
	 * 上传图片
	 * 
	 * @param urlStr
	 * @param textMap
	 * @param fileMap
	 * @return
	 */
	public static String formUpload(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(300000);
			conn.setReadTimeout(300000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			// // text
			if (textMap != null) {

				String outstr = "json=" + textMap.get("json");
				System.out.println(outstr);
				out.write(outstr.getBytes());
				out.flush();
				out.close();
			}

			// // file
			if (fileMap != null) {
				Iterator iter = fileMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
					File file = new File(inputValue);

					String filename = file.getName();

					String contentType = new MimetypesFileTypeMap().getContentType(file);
					if (filename.endsWith(".png")) {
						contentType = "image/png";
					} else if (filename.endsWith(".jpg")) {
						contentType = "image/jpeg";
					}
					if (contentType == null || contentType.equals("")) {
						contentType = "application/octet-stream";
					}

					StringBuffer strBuf = new StringBuffer();
					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename
							+ "\"\r\n");
					strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
					System.out.println(strBuf.toString());
					out.write(strBuf.toString().getBytes());

					DataInputStream in = new DataInputStream(new FileInputStream(file));
					int bytes = 0;
					byte[] bufferOut = new byte[10240];
					while ((bytes = in.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					in.close();
				}
				// out.write(fileMap.toString().getBytes());
				byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
				out.write(endData);
				out.flush();
				out.close();
			}

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			InputStreamReader aa = new InputStreamReader(conn.getInputStream());
			BufferedReader reader = new BufferedReader(aa);
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			res = "{\"error_code\":0,\"message\":\"fail\"}";
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}

	public static String formJson(String urlStr, Map<String, String> textMap, Map<String, String> fileMap) {
		String res = "";
		HttpURLConnection conn = null;
		String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(300000);
			conn.setReadTimeout(300000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
//			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			// // text
			if (textMap != null) {

				String outstr = "json=" + textMap.get("json");
				System.out.println(outstr);
				out.write(outstr.getBytes());
				out.flush();
				out.close();
			}

			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			res = "{\"error_code\":0,\"message\":\"fail\"}";
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		
		return res;
	}
}
