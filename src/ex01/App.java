package ex01;

import java.util.Scanner;

// 홍길동 개발자
public class App {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);    // 키보드 스캐너
		String path = sc.nextLine();            // 입력받은 문자열 할당
		
		// path = "/login" -> uc.login()
		// path = "/join" -> uc.join();
		UserController uc = new UserController();   // UC 객체 인스턴스 생성
		
		if (path.equals("/login")) {        // /login 입력시
			uc.login();                     // login()메소드 호출
		} else if (path.equals("/join")) {
			uc.join();
		} else if (path.equals("/check")) {
			uc.check();
		}
	}
}
