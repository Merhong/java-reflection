package ex02;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Scanner;

public class App {
	
	
	public static void findUri(UserController uc, String uri) throws Exception {
		boolean isFind = false;
		Method[] methods = uc.getClass().getDeclaredMethods();  // 리플렉션 내용을 받을 변수
		
		// 메소드 개수만큼 반복
		for (Method mt : methods) {
			// getDeclaredAnnotation() : 해당 클래스 또는 메서드에 적용된 어노테이션을 가져오는 메서드
			// RequestMapping.class : @RequestMapping 어노테이션의 클래스 객체를 나타냄
			// 즉, @RequestMapping 어노테이션이 적용된 정보를 가져온다.
			Annotation anno = mt.getDeclaredAnnotation(
				RequestMapping.class); //  anno에 @RequestMapping 할당됨.
			
			/**
			 * 원래 객체의 실제 타입에 접근하기 위해서 rm에 anno를 다운캐스팅하여 할당.
			 * (어노테이션 객체에 접근하기 위해서는 해당 어노테이션의 실제 타입으로 캐스팅해야 함.)
			 */
			RequestMapping rm = (RequestMapping) anno;
			// RequestMapping 어노테이션에 정의된 URI와 입력으로 받은 uri가 같은지 확인
			if (rm.uri().equals(uri)) {
				isFind = true;  // uri를 찾았으므로 true
				mt.invoke(uc);  // 메소드에 담긴 uc를 호출
			}
		}
		if (isFind == false) {
			System.out.println("404 Not Found");
		}
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);    // 키보드 스캐너
		String uri = sc.nextLine();             // 입력 받은값을 uri에 넣는다.
		
		findUri(new UserController(), uri);     // findUri 메소드 호출
		
	}
	
}
