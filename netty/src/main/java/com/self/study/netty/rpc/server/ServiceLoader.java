package com.self.study.netty.rpc.server;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ServiceLoader
 * 
 * 
 */
public class ServiceLoader {
	/**
	 * 实例化所有rpc服务类，也可用于暴露服务信息到注册中心。
	 * @param clazz 服务类所在包名，多个用英文逗号隔开
	 * @return
	 */
	public Map<String,Object> getService(String clazz) {
		try {
			Map<String, Object> services = new HashMap<String, Object>();
			// 获取所有服务类
			String[] clazzes = clazz.split(",");
			List<Class<?>> classes = new ArrayList<Class<?>>();
			for(String cl : clazzes){
				List<Class<?>> classList = getClasses(cl);
				classes.addAll(classList);
			}
			// 循环实例化
			for(Class<?> cla : classes) {
				Object obj = cla.newInstance();
				services.put(cla.getAnnotation(Service.class).value().getName(), obj);
			}
			return services;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取包下所有有@Sercive注解的类
	 * @param pckgname
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> getClasses(String pckgname) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		// 找到指定的包目录
		File directory = null;
		try {
			ClassLoader cld = Thread.currentThread().getContextClassLoader();
			if (cld == null)
				throw new ClassNotFoundException("无法获取到ClassLoader");
			String path = pckgname.replace('.', '/');
			URL resource = cld.getResource(path);
			if (resource == null)
				throw new ClassNotFoundException("没有这样的资源：" + path);
			System.out.println(resource.toURI());
			directory = Paths.get(resource.toURI()).toFile();
		} catch (NullPointerException x) {
			throw new ClassNotFoundException(pckgname + " (" + directory + ") 不是一个有效的资源");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (directory.exists()) {
			// 获取包目录下的所有文件
			String[] files = directory.list();
			File[] fileList = directory.listFiles();
			// 获取包目录下的所有文件
			for (int i = 0; fileList != null && i < fileList.length; i++) {
				File file = fileList[i];
				//判断是否是Class文件
				if (file.isFile() && file.getName().endsWith(".class")) {
					Class<?> clazz = Class.forName(pckgname + '.' + files[i].substring(0, files[i].length() - 6));
					if(clazz.getAnnotation(Service.class) != null){
						classes.add(clazz);
					}
				}else if(file.isDirectory()){ //如果是目录，递归查找
					List<Class<?>> result = getClasses(pckgname+"."+file.getName());
					if(result != null && result.size() != 0){
						classes.addAll(result);
					}
				}
			}
		} else{
			throw new ClassNotFoundException(pckgname + "不是一个有效的包名");
		}
		return classes;
	}
}

