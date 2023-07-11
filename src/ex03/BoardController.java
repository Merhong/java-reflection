package ex03;

@Controller
public class BoardController {
	
	// uri에 /save가 들어오면 save()메소드 실행
	@RequestMapping(uri = "/save")
	public void save() {
		System.out.println("save 호출됨");
	}
}
