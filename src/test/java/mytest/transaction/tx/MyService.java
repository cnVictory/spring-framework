package mytest.transaction.tx;

import mytest.domain.Visitor;

public interface MyService {

	int insert(Visitor visitor);

	int update(String name, Visitor visitor);

	Visitor select(String name);

	int delete(String name);
}
