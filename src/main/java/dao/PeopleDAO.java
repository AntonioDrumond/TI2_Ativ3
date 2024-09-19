package dao;

import model.People;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class PeopleDAO extends DAO {	
	public PeopleDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(People people) {
		boolean status = false;
		try {
			String sql = "INSERT INTO people (id, name, age, work) "
		               + "VALUES ('" + people.getName() + "', "
		               + people.getAge() + ", " + people.getWork() + ");";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public People get(int id) {
		People people = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM people WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 people = new People(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), 
	                				   rs.getString("work"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return people;
	}
	
	
	public List<People> get() {
		return get("");
	}

	
	public List<People> getOrderByID() {
		return get("id");		
	}
	
	
	public List<People> getOrderByName() {
		return get("name");		
	}
	
	public List<People> getOrderByAge() {
		return get("age");		
	}
	
	
	public List<People> getOrderByWork() {
		return get("work");		
	}
	
	
	private List<People> get(String orderBy) {
		List<People> people = new ArrayList<People>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM people" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	People p = new People(rs.getInt("id"), rs.getString("name"), rs.getInt("age"), 
	        			                rs.getString("work"));
	            people.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return people;
	}
	
	
	public boolean update(People people) {
		boolean status = false;
		try {  
			String sql = "UPDATE people SET nome = '" + people.getName() + "', "
					   + "age = " + people.getAge() + ","
					   + "work = ? WHERE id = " + people.getWork();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM people WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}
