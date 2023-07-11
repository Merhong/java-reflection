package ex03;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {
	
	public static Set<Object> componentScan(String pkg) throws Exception {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Set<Object> instances = new HashSet<>();
		
		// 해당 패키지의 URL을 찾기 (프로토콜이 적용된 URL)
		URL packageUrl = classLoader.getResource(pkg);
		
		// 해당 패키지를 자바 파일 객체로 변환 (파일객체는 디렉토리가 될수도 있고, 파일이 될수도 있다)
		File packageDirectory = new File(packageUrl.toURI());
		
		// 해당 패키지내부의 모든 파일 찾아서 Set<Class>에 담기
		for (File file : packageDirectory.listFiles()) {
			if (file.getName().endsWith(".class")) {
				String className = pkg + "." + file.getName().replace(".class", "");
				Class cls = Class.forName(className);
				if (cls.isAnnotationPresent(Controller.class)) {
					instances.add(cls.newInstance());
				}
			}
		}
		return instances;
	}
	
	public static void findUri(Set<Object> instances, String uri) throws Exception {
		boolean isFind = false;
		for (Object obj : instances) {
			Method[] methods = obj.getClass().getDeclaredMethods();
			
			for (Method mt : methods) {
				Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);
				RequestMapping rm = (RequestMapping) anno;
				if (rm.uri().equals(uri)) {
					isFind = true;
					mt.invoke(obj);
				}
				
			}
		}
		if (isFind == false) {
			System.out.println("404 Not Found");
		}
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String uri = sc.nextLine();
		
		Set<Object> instances = componentScan("ex03");
		findUri(instances, uri);
	}
	
}