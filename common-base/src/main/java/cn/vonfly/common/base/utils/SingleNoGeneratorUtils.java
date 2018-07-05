package cn.vonfly.common.base.utils;

import com.google.common.collect.Maps;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单号生成器
 */
public class SingleNoGeneratorUtils {
	private static final String ORDER_NO_FLAG = "ORDER_NO_FLAG";
	private static final String TIME_PATTERN = "yyyyMMddHHmmssS";
	private static SingleNoGeneratorUtils instance = new SingleNoGeneratorUtils();

	private long hostPidValue;
	private int maxInteger = 999999;
	private ConcurrentMap<String, AtomicInteger> type = Maps.newConcurrentMap();

	public static SingleNoGeneratorUtils getInstance() {
		return instance;
	}

	private SingleNoGeneratorUtils() {
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String hostName = localHost.getHostAddress();
			localHost.getAddress();
			String name = ManagementFactory.getRuntimeMXBean().getName();
			// get pid
			String pid = name.split("@")[0];
			int i = 0;
			String[] hosts = hostName.split("\\.");
			if (hosts.length > 0) {
				//只取最后一个ip位
				hostPidValue |= Long.parseLong(hosts[hosts.length - 1]) << i * 8;
			} else {
				hostPidValue |= 1 << i * 8;
			}
			i++;
			hostPidValue |= Long.parseLong(pid) << (i * 8);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据指定类型构建
	 * @param typeFlag
	 * @return
	 */
	private String generateByType(String typeFlag) {
		AtomicInteger atomicInteger = type.get(typeFlag);
		if (null == atomicInteger) {
			synchronized (typeFlag) {
				if (null == atomicInteger) {
					atomicInteger = new AtomicInteger();
					type.putIfAbsent(typeFlag, atomicInteger);
				}
			}
		}
		atomicInteger = type.get(typeFlag);
		int increment = atomicInteger.getAndIncrement();
		if (increment > maxInteger) {
			synchronized (typeFlag) {
				atomicInteger.set(0);
				increment = atomicInteger.getAndIncrement();
			}
		}

		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(TIME_PATTERN)) + hostPidValue + String
				.format("%06d", increment);

	}

	/**
	 * 单号生成
	 * @return
	 */
	public static String generateOrderNo() {
		return getInstance().generateByType(ORDER_NO_FLAG);
	}
}
