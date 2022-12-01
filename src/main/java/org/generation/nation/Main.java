package org.generation.nation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {
	
	private static final String URL = "jdbc:mysql://localhost:3306/nations";
	private static final String USER = "root";
	private static final String PASSWORD = "root";
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci il nome della nazione: ");
		String nationName = sc.next();
		sc.close();
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD)){
			
			 String sql = "SELECT countries.country_id, countries.name, regions.name, continents.name"
					 + " FROM countries "
					 + " JOIN regions "
					 + " ON countries.region_id = regions.region_id "
					 + " JOIN continents "
					 + " ON regions.continent_id = continents.continent_id "
					 + " WHERE countries.name LIKE ? "
					 + " ORDER BY countries.name ";
			
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				
				ps.setString(1,"%" + nationName + "%");
				
				try (ResultSet rs = ps.executeQuery()) {
					while(rs.next()) {
						
						final int id = rs.getInt(1);
						final String countryName = rs.getString(2);					
						final String regionName = rs.getString(3);
						final String continentName = rs.getString(4);
						
						
						System.out.println("ID: " + id + "\n" 
							+ "COUNTRY: " + countryName + "\n"
							+ "REGION: " + regionName + "\n"
							+ "CONTINENT: " + continentName
						);
					}
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
}