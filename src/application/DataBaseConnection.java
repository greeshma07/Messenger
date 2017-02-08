package application;
import java.sql.*;

public class DataBaseConnection {
	
	static Main main = new Main();
	
	public static Connection getConnection(){
		
		try {
			String driver = "com.mysql.jdbc.Driver"; // specify the software driver to translate java
														//into database driver
			String url = "jdbc:mysql://127.0.0.1:3306/MyFirstDB";//specify the IP address of the
																	//database and the schema
			String userName = "root";
			String password = "admin";
			Class.forName(driver); //convert a driver into a java class, a translator
			Connection conn = DriverManager.getConnection(url,userName,password);
			//load the MySQL driver and then send request to the server
			System.out.println("Connection established successfully");
			return conn;	//the connection class is similar to the socket in client-server communication
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public static void createTable()throws SQLException{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String sql = "CREATE TABLE IF NOT EXISTS info (id INTEGER NOT NULL,"
				+ "firstName VARCHAR(255),"
				+ "lastName VARCHAR(255),"
				+ "gender CHAR,"
				+ "dob VARCHAR(255),"
				+ "hobby VARCHAR(10),"
				+ "image VARCHAR(255),"
				+ "PRIMARY KEY(id))";			
		stmt.executeUpdate(sql);
		System.out.println("Table created successfully");
		stmt.close();
		conn.close();
	}
	
	public static void insertRecord(String firstName,String lastName,char gender,String dob,
			String hobby,String imageUrl) throws Exception{
		
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String url="";
		if(imageUrl!=null)
			url = imageUrl.replace("\\", "\\\\");   
		String sql ="insert into info values(100,'"+firstName+"','"+lastName
		+"','"+gender+"','"+dob+"','"+hobby+"','"+url+"')";
		System.out.println(sql);
		stmt.executeUpdate(sql);
		System.out.println("Inserted Record successfully");
		stmt.close();
		conn.close();
	}
	
	public static void updateRecord(String firstName,String lastName,char gender,String dob,
			String hobby,String imageUrl) throws Exception{
		
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String url = imageUrl.replace("\\", "\\\\");
		String sql = "update info set firstName='"+firstName+"',lastName='"+lastName
				+"',gender='"+gender+"',dob='"+dob+"',hobby='"+hobby+"',image='"+url+"' "
						+ "where id = 100";
		System.out.println(sql);
		stmt.executeUpdate(sql);
		System.out.println("Updated Record successfully");
		stmt.close();
		conn.close();
	}
	
	public static boolean isTableExists() throws Exception{
		Connection conn = getConnection();
		DatabaseMetaData dbm = conn.getMetaData();
		ResultSet tables = dbm.getTables(null, null, "info", null);
		if (tables.next()) {
		  // Table exists
			conn.close();
			boolean flag = hasRecords();
			if(flag)
				return true;
			else
				return false;			
		}
		else {
		  // Table does not exist
			conn.close();
			return false;
		}
	}
	
	public static boolean hasRecords()throws Exception{
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM info where id =100";			
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()){
			stmt.close();
			conn.close();
			return true;
		}
		else{
			stmt.close();
			conn.close();
			return false;
		}
	}
	
	public static void getRecords()throws Exception{
		Connection conn = getConnection();		
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM info where id=100";			
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()){
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			char gender = rs.getString("gender").charAt(0);
			String dob = rs.getString("dob");
			String hobby = rs.getString("hobby");
			String imageUrl = rs.getString("image");
			main.updateFields(firstName,lastName,gender,dob,hobby,imageUrl);
		}
		stmt.close();
		conn.close();
		System.out.println("Retrived Record successfully");		
	}
	
	public static String getImage()throws Exception{
		String url = null;
		Connection conn = getConnection();		
		Statement stmt = conn.createStatement();
		String sql = "SELECT image FROM info where id =100";			
		ResultSet rs = stmt.executeQuery(sql);
		if(rs.next()){
			url = rs.getString("image");			
		}
		stmt.close();
		conn.close();
		System.out.println("Retrived image successfully");	
		return url;
	}
}
