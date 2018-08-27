package dao;


import java.util.List;

public interface IGenericDAO<T,U,V> {
	
	public List<T> list(V connName) throws Exception;
	
	public T find(V connName,U id) throws Exception;
	
	public void remove(V connName,U id) throws Exception;
	
	public void removeRelacionated(V connName,U id) throws Exception;
	
	public void update(V connName,T objeto) throws Exception;
	
	public void save(V connName,T objeto) throws Exception;
	
	public void saveRelacionated(V connName,T objeto) throws Exception;

	public int findNextId(V connName) throws Exception;
}