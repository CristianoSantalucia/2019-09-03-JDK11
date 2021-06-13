package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao
{
	public List<Food> listAllFoods()
	{
		String sql = "SELECT * FROM food";
		try
		{
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Food> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					list.add(new Food(res.getInt("food_code"), res.getString("display_name")));
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public List<Condiment> listAllCondiments()
	{
		String sql = "SELECT * FROM condiment";
		try
		{
			Connection conn = DBConnect.getConnection();

			PreparedStatement st = conn.prepareStatement(sql);

			List<Condiment> list = new ArrayList<>();

			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					list.add(new Condiment(res.getInt("condiment_code"), res.getString("display_name"),
							res.getDouble("condiment_calories"), res.getDouble("condiment_saturated_fats")));
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}

			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public List<Portion> listAllPortions()
	{
		String sql = "SELECT * FROM portion";
		List<Portion> list = new ArrayList<>();
		try
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					list.add(new Portion(res.getInt("portion_id"), res.getDouble("portion_amount"),
							res.getString("portion_display_name"), res.getDouble("calories"),
							res.getDouble("saturated_fats"), res.getInt("food_code")));
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}
			conn.close();
			return list;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}

	}

	public void getVertici(Map<Integer, Portion> vertici, int calorie)
	{
		String sql = "SELECT p.* "
				+ "FROM food_pyramid_mod.portion p "
				+ "WHERE p.calories < ? "
				+ "GROUP BY p.portion_display_name ";
		try
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, calorie);
			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					Portion p = new Portion(res.getInt("portion_id"), res.getDouble("portion_amount"),
							res.getString("portion_display_name"), res.getDouble("calories"),
							res.getDouble("saturated_fats"), res.getInt("food_code"));
					vertici.putIfAbsent(p.getPortion_id(), p); 
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}
			conn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	public List<Adiacenza> getAdiacenze(Map<Integer, Portion> vertici, int calorie)
	{
		String sql = "SELECT p1.portion_id id1, p2.portion_id id2, COUNT(DISTINCT(p1.food_code)) peso "
					+ "FROM food_pyramid_mod.portion p1, "
					+ "	  food_pyramid_mod.portion p2 "
					+ "WHERE p1.portion_display_name < p2.portion_display_name "
					+ "		AND p1.food_code = p2.food_code "
					+ "		AND p1.calories < ? "
					+ "		AND p2.calories < ? "
					+ "GROUP BY p1.portion_display_name, p1.portion_display_name " ; 
		
		List<Adiacenza> list = new ArrayList<>();
		try
		{
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, calorie);
			st.setInt(2, calorie);
			ResultSet res = st.executeQuery();

			while (res.next())
			{
				try
				{
					Portion p1 = vertici.get(res.getInt("id1"));
					Portion p2 = vertici.get(res.getInt("id2"));
					Integer peso = res.getInt("peso");
					if(p1 != null && p2 != null)
					{
						Adiacenza a = new Adiacenza(p1, p2, peso);
						list.add(a);
					}
				}
				catch (Throwable t)
				{
					t.printStackTrace();
				}
			}
			conn.close();
			return list; 
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return null; 
		}
	}
}
