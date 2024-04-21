<%@ page import="java.sql.*"%>

<%
try{
	Class.forName("com.mysql.jdbc.Driver");
String url = "jdbc:mysql://localhost:3306/college";
Connection con = DriverManager.getConnection(url,"root","");
Statement stmt = con.createStatement();
ResultSet rs = stmt.executeQuery("Select * from employee");
rs.next();
out.print(rs.getInt("salary"));
}catch(Exception e){
	out.print(e.getMessage());
}
%>