package mytest.transaction.tx;

import mytest.domain.Visitor;
import org.springframework.beans.factory.annotation.Autowired;

//@Repository
public class MyDaoImpl {

	@Autowired
	private MyService myService;

	public int insert(Visitor visitor) {
		System.out.println("=== dao insert ===");
		myService.insert(visitor);
		System.out.println("=== dao insert end ===");
		return 1;
	}

	public int update(Visitor visitor) {
		System.out.println("=== dao update ===");
		myService.update("Test", visitor);
		System.out.println("=== dao update end ===");
		return 2;
	}
}
