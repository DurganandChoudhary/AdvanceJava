import java.sql.*;
import java.util.*;

public class Bank {
    public static void main(String[] args) 
    {
        try{
            String url="jdbc:mysql://localhost:3306/BankingManagementSystem";
            Connection con=DriverManager.getConnection(url,"root","");
            System.out.println("I for Insert, D for Delete, U for Update, S for select, stop for exit");
            Scanner sc = new Scanner(System.in);
            String status= sc.next();
            int customer_no;
            String name;
            Statement st = con.createStatement();
            while(!status.equals("stop"))
            {
                switch (status) {
                    case "I":
                    System.out.println("Enter customer_no and name");
                    id=sc.nextInt();
                    name=sc.next();
                    st.executeUpdate("insert into demo values("+customer_no+",'"+name+"')");
                        
                    break;
                    case "U":
                    System.out.println("Enter Id for Update");
                    customer_no=sc.nextInt();
                    System.out.println("Enter name of "+customer_no+" to update");
                    name = sc.next();
                    st.executeUpdate("Update demo set name ='"+ name + "' where id="+id);
                    break;

                    case "D":
                    System.out.println("Enter id for delete");
                    id=sc.nextInt();
                    st.executeUpdate("delete from demo where id="+id);
                    break;

                    case "S":
                    ResultSet rs = st.executeQuery("Select * from demo");
                    while(rs.next()){
                        System.out.println(rs.getInt(1)+" | "+rs.getString(2));
                        System.out.println("-----------------------");
                    }
                    break;

                    default:
                        break;
                }
                System.out.println("I for Insert, D for Delete, U for Update, S for select , Stop for exit");
                status = sc.next();
            }
            System.out.println("Exit..............");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
}
