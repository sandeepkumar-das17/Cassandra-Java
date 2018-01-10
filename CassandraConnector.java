package com.cassandra.utility;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;


public class CassandraConnector {   
    
    public static void main(String[] args) {

    	Cluster cluster;
    	Session session;
    	
		try{
    		// Connect to the cluster and keyspace "demo"
    		//cluster = Cluster.builder().addContactPoint("localhost").build();
    		cluster = Cluster.builder().withPort(9042).addContactPoint("localhost").withProtocolVersion(ProtocolVersion.V4).build();
    		System.out.println("Cluster setup completed");
    	
    		session = cluster.connect("demo");
    		System.out.println("Session created");
    	
    		// Insert one record into the users table
    		session.execute("INSERT INTO users (user_name,birth_year,gender,password,state) VALUES ('tusharm',1988,'M','ijk2017','OD');");
    	
    	
			// Use select to get the user we just entered
			ResultSet results = session.execute("SELECT * FROM users WHERE state='OD' ALLOW FILTERING");
			for (Row row : results) {
			System.out.format("%s | %d\n", row.getString("user_name"), row.getLong("birth_year"));
			}
	
			// Update the same user with a new state
			session.execute("update users set state='TN' where user_name = 'tusharm'");
	
			// Select and show the change
			results = session.execute("select * from users where user_name = 'tusharm'");
			for (Row row : results) {
			System.out.format("%s %d\n", row.getString("user_name"), row.getString("state"));

			}
	
	
			// Delete the user from the users table
			session.execute("DELETE FROM users WHERE user_name = 'tusharm'");
			// Show that the user is gone
			results = session.execute("SELECT * FROM users");
			for (Row row : results) {
			System.out.format("%s %d %s %s %s\n", row.getString("user_name"), row.getLong("birth_year"),  row.getString("gender"), row.getString("password"), row.getString("state"));
			}
    	
    	}catch (Exception e){
    		e.printStackTrace();
    		}
    	finally(){
    		// Clean up the connection by closing it
    		cluster.close();
    	}

    }       
}
