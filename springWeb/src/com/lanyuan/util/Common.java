package com.lanyuan.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateFormatUtils;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.Swap;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lanyuan.controller.ServerInfoController;
import com.lanyuan.entity.ServerStatus;
import com.lanyuan.entity.ServerStatus.CpuInfoVo;
import com.lanyuan.entity.ServerStatus.DiskInfoVo;

public class Common {

	public static boolean isEmpty(String s)
	{
		if(null==s|| "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	/**
	 * 使用率计算
	 * 
	 * @return
	 */
	public static String fromUsage(long free, long total) {
		Double d = new BigDecimal(free * 100 / total).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		return String.valueOf(d);
	}
	
	//返回用户的ip地址
	public static String toIpAddr(HttpServletRequest request)
	{
		String ip =request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
		
	}

	//获取经过认证的用户名
    public static String getAuthenticationUsername()
    {
    	String username = null;
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails)
        {
        	username = ((UserDetails)principal).getUsername();
        }
        else
        {
        	username = principal.toString();
        }
    
        return username;
    }
    
    /**
	 * 用来去掉List中空值和相同项的。
	 * 
	 * @param list
	 * @return
	 */
	public static List<String> removeSameItem(List<String> list) {
		List<String> difList = new ArrayList<String>();
		for (String t : list) {
			if (t != null && !difList.contains(t)) {
				difList.add(t);
			}
		}
		return difList;
	}
	
	/**
	 * 返回服务系统信息
	 * @throws Exception 
	 */
	public static ServerStatus getServerStatus() throws Exception {
		ServerStatus status = new ServerStatus();
		status.setServerTime(DateFormatUtils.format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss"));
		status.setServerName(System.getenv().get("COMPUTERNAME"));

		Runtime rt = Runtime.getRuntime();
		//status.setIp(InetAddress.getLocalHost().getHostAddress());
		status.setJvmTotalMem(rt.totalMemory() / (1024 * 1024));
		status.setJvmFreeMem(rt.freeMemory() / (1024 * 1024));
		status.setJvmMaxMem(rt.maxMemory()/ (1024 * 1024));
		Properties props = System.getProperties();
		status.setServerOs(props.getProperty("os.name") + " " + props.getProperty("os.arch") + " " + props.getProperty("os.version"));
		status.setJavaHome(props.getProperty("java.home"));
		status.setJavaVersion(props.getProperty("java.version"));
		status.setJavaTmpPath(props.getProperty("java.io.tmpdir"));

		Sigar sigar = new Sigar();
		getServerCpuInfo(sigar, status);
		getServerDiskInfo(sigar, status);
		getServerMemoryInfo(sigar, status);
		
		return status;
	}

	public static void getServerCpuInfo(Sigar sigar, ServerStatus status) {
		try {
			CpuInfo infos[] = sigar.getCpuInfoList();
			CpuPerc cpuList[] = sigar.getCpuPercList();
			double totalUse = 0L;
			for (int i = 0; i < infos.length; i++) {
				CpuPerc perc = cpuList[i];
				CpuInfoVo cpuInfo = new CpuInfoVo();
				cpuInfo.setId(Integer.parseInt(infos[i].hashCode() + ""));
				cpuInfo.setCacheSize(infos[i].getCacheSize());
				cpuInfo.setModel(infos[i].getModel());
				cpuInfo.setUsed(CpuPerc.format(perc.getCombined()));
				cpuInfo.setUsedOrigVal(perc.getCombined());
				cpuInfo.setIdle(CpuPerc.format(perc.getIdle()));
				cpuInfo.setTotalMHz(infos[i].getMhz());
				cpuInfo.setVendor(infos[i].getVendor());
				status.getCpuInfos().add(cpuInfo);
				totalUse += perc.getCombined();
			}
			String cpuu = CpuPerc.format(totalUse / status.getCpuInfos().size());
			cpuu = cpuu.substring(0,cpuu.length()-1);
			status.setCpuUsage(cpuu);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}



	public static void getServerMemoryInfo(Sigar sigar, ServerStatus status) {
		try {
			Mem mem = sigar.getMem();
			status.setTotalMem(mem.getTotal() / (1024 * 1024));
			status.setUsedMem(mem.getUsed() / (1024 * 1024));
			status.setFreeMem(mem.getFree() / (1024 * 1024));
			// 交换区
			Swap swap = sigar.getSwap();
			status.setTotalSwap(swap.getTotal() / (1024 * 1024));
			status.setUsedSwap(swap.getUsed() / (1024 * 1024));
			status.setFreeSwap(swap.getFree() / (1024 * 1024));
		} catch (Exception e) {

		}
	}
	
	/**
	 * ， 重新设置CLASSPATH,加入sigar，以支持dll,so等文件的加入与读取
	 */
	private static void resetClasspath() {
		String libPath = System.getProperty("java.library.path");
		String classpath = ServerInfoController.class.getResource("/").getPath();
		System.setProperty("java.library.path", classpath + File.separator + "sigar" + File.pathSeparator + libPath);
	}
	
	
	// 磁盘读写初始数据 用于计算读写速率
	private static Map<String, String> diskWritesAndReadsOnInit = new HashMap<String, String>();
	
	private static long initTime;
	static {
		initTime = System.currentTimeMillis();
		resetClasspath();
		Sigar sigar = null;
		try {

			sigar = new Sigar();
			FileSystem fslist[] = sigar.getFileSystemList();
			FileSystemUsage usage = null;
			for (int i = 0; i < fslist.length; i++) {
				FileSystem fs = fslist[i];
				if (fs.getType() != 2)
					continue;
				usage = sigar.getFileSystemUsage(fs.getDirName());
				diskWritesAndReadsOnInit.put(fs.getDevName(), usage.getDiskReadBytes() + "|" + usage.getDiskWriteBytes());
			}
		} catch (Exception e) {
		} finally {
			if (sigar != null)
				sigar.close();
		}
	}
	
	
	public static void getServerDiskInfo(Sigar sigar, ServerStatus status) {
		try {
			FileSystem fslist[] = sigar.getFileSystemList();
			FileSystemUsage usage = null;
			for (int i = 0; i < fslist.length; i++) {
				FileSystem fs = fslist[i];
				switch (fs.getType()) {
				case 0: // TYPE_UNKNOWN ：未知
				case 1: // TYPE_NONE
				case 3:// TYPE_NETWORK ：网络
				case 4:// TYPE_RAM_DISK ：闪存
				case 5:// TYPE_CDROM ：光驱
				case 6:// TYPE_SWAP ：页面交换
					break;
				case 2: // TYPE_LOCAL_DISK : 本地硬盘
					DiskInfoVo disk = new DiskInfoVo();
					disk.setDevName(fs.getDevName());
					disk.setDirName(fs.getDirName());
					usage = sigar.getFileSystemUsage(fs.getDirName());
					disk.setTotalSize(usage.getTotal() / (1024 * 1024));
					// disk.setFreeSize(usage.getFree()/(1024*1024));
					disk.setAvailSize(usage.getAvail() / (1024 * 1024));
					disk.setUsedSize(usage.getUsed() / (1024 * 1024));
					disk.setUsePercent(usage.getUsePercent() * 100D + "%");
					disk.setTypeName(fs.getTypeName());
					disk.setSysTypeName(fs.getSysTypeName());

					String val = diskWritesAndReadsOnInit.get(fs.getDevName());
					if (val != null) {
						long timePeriod = (System.currentTimeMillis() - initTime) / 1000;
						long origRead = Long.parseLong(val.split("\\|")[0]);
						long origWrite = Long.parseLong(val.split("\\|")[1]);
						disk.setDiskReadRate((usage.getDiskReadBytes() - origRead) / timePeriod);
						disk.setDiskWriteRate((usage.getDiskWriteBytes() - origWrite) / timePeriod);
					}

					status.getDiskInfos().add(disk);

				}
			}
		} catch (Exception e) {

		}
	}
    
}
