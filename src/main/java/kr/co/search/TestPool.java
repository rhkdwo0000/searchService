package kr.co.search;

import org.apache.commons.pool.PoolableObjectFactory;

public class TestPool implements PoolableObjectFactory<String>{

	public static void main(String[] args) {

//		new GenericObjectPool
//		
//		
//		PoolableObjectFactory<T>() {
//		};
		
	}

	@Override
	public String makeObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyObject(String obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean validateObject(String obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void activateObject(String obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void passivateObject(String obj) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
