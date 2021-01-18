package mytest.transaction.tx;

import mytest.domain.Visitor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyServiceImpl implements MyService {

//	@Autowired
//	private MyDaoImpl myDaoImpl;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insert(Visitor visitor) {
		System.out.println("== insert ==");
		return 1;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int update(String name, Visitor visitor) {
		System.out.println("== update ==");
		return 1;
	}

	@Override
	public Visitor select(String name) {
		System.out.println("== select ==");
		Visitor visitor = new Visitor("张三", 15, true);
		return visitor;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delete(String name) {
		System.out.println("== delete ==");
		return 1;
	}
}
