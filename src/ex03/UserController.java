package ex03;

@Controller
public class UserController {
	
	// uri가 /login이 들어오면 login() 메소드 실행
	@RequestMapping(uri = "/login")
	public void login() {
		System.out.println("login() 호출됨");
	}
	
	// uri가 /join이 들어오면 join() 메소드 실행
	@RequestMapping(uri = "/join")
	public void join() {
		System.out.println("join() 호출됨");
	}
}
