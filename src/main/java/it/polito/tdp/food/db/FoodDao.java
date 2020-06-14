package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Adiacenze;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	//vertici
	public List<String> getTipoPorzione(Integer calorie){
		String sql="SELECT distinct portion_display_name " + 
				"FROM `portion` " +
				"WHERE calories<? "+
				"ORDER BY portion_display_name ASC";

		List<String> result= new ArrayList<String>();
		
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calorie);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				result.add(res.getString("portion_display_name"));
			}
			
			conn.close();
			return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
			
	}
	
	//archi --> esiste almeno un cibo servito come tipo e altro tipo?
	public List<Adiacenze> getAdiacenze(){
		String sql="SELECT p1.portion_display_name AS n1, p2.portion_display_name AS n2, COUNT(DISTINCT(p1.food_code)) AS peso " + 
				"FROM `portion` AS p1, `portion` AS p2 " + 
				"WHERE p1.food_code=p2.food_code AND p1.portion_id>p2.portion_id " + 
				"GROUP BY n1,n2 " ;
		
		List<Adiacenze> result= new ArrayList<Adiacenze>();
		
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Adiacenze ad= new Adiacenze(res.getString("n1"), res.getString("n2"), res.getDouble("peso"));
				result.add(ad);
			}
			
			conn.close();
			return result;
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

}
