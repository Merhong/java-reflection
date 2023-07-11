package ex03;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class App {
	
	// 패키지명을 매개변수로 받고 Set에 Object 인스턴스들을 담아서 반환함.
	public static Set<Object> componentScan(String pkg) throws Exception {
		// 현재 실행중인 스레드의 컨텍스트 클래스 로더를 가져옴
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// instances에 Hashset을 생성후 클래스 인스턴스 저장할 준비
		Set<Object> instances = new HashSet<>();
		
		// 해당 패키지의 URL을 찾기 (프로토콜이 적용된 URL)
		URL packageUrl = classLoader.getResource(pkg);
		
		// 해당 패키지를 자바 파일 객체로 변환 (파일객체는 디렉토리가 될수도 있고, 파일이 될수도 있다)
		File packageDirectory = new File(packageUrl.toURI());
		
		// 해당 패키지내부의 모든 파일 찾아서 Set<Class>에 담기
		for (File file : packageDirectory.listFiles()) {
			if (file.getName().endsWith(".class")) { // 확장자명 .class면 실행
				// 확장자명 .class를 제거한 후 전체 클래스 이름을 할당.
				String className = pkg + "." + file.getName().replace(".class", "");
				// className을 사용하여 동적으로 cls에 클래스 할당
				Class cls = Class.forName(className);
				// cls가  @Controller 어노테이션을 가지고 있으면 실행
				if (cls.isAnnotationPresent(Controller.class)) {
					// cls의 인스턴스를 생성하고 instances에 추가
					instances.add(cls.newInstance());
				}
			}
		}
		// for문이 끝난 후 instances 리턴
		return instances;
	}
	
	// findUri 메소드 : 위의 instances와 문자열 uri를 매개변수로 받는다.
	public static void findUri(Set<Object> instances, String uri) throws Exception {
		boolean isFind = false;
		// instances 집합의 객체를 반복 확인
		for (Object obj : instances) {
			// 현재 객체에 선언된 모든 메소드들을 가져옴
			Method[] methods = obj.getClass().getDeclaredMethods();
			
			// 가져온 메소드들을 반복 확인
			for (Method mt : methods) {
				// 메소드에 적용된 @RequestMapping 어노테이션을 가져옴
				Annotation anno = mt.getDeclaredAnnotation(RequestMapping.class);
				// 어노테이션 객체를 다운캐스팅하여 사용
				RequestMapping rm = (RequestMapping) anno;
				// @RequestMapping 어노테이션에 정의된 URI와 입력으로 받은 uri가 같은지 확인
				if (rm.uri().equals(uri)) {
					isFind = true;  // uri를 찾았으므로 true
					mt.invoke(obj); // 객체의 해당 메소드를 호출
				}
			}
		}
		// URI를 찾지 못했을때
		if (isFind == false) {
			System.out.println("404 Not Found");
		}
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);    // 키보드 스캐너
		String uri = sc.nextLine();             // 한줄 읽어서 uri에 저장
		
		// componentScan() 메소드 호출하여 ex03 패키지를 분석하고 인스턴스들을 가져온다.
		Set<Object> instances = componentScan("ex03");
		// 분석하고 가져온 인스턴스들과 uri를 findUri에 넣고 실행
		findUri(instances, uri);
	}
}