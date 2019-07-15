import org.apache.commons.text.RandomStringGenerator;

import java.sql.*;
import java.util.*;

public class Task4dbCreate {
    public static void main(String[] args) throws SQLException{
        char [][] pairs = {{'a','z'},{'A','Z'}};
        List<String> uniqueRandomStringList = new ArrayList<String>();

        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/task4db";
        String USER = "postgres";
        String PASS = "gfhjkmjb";

        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();



        for (int i = 0; i < 100; i++) {
            //System.out.println(generator.generate(6));
            uniqueRandomStringList.add(generator.generate(6));
        }

        HashSet hs = new HashSet();
        hs.addAll(uniqueRandomStringList);
        uniqueRandomStringList.clear();
        uniqueRandomStringList.addAll(hs);

        //System.out.println(uniqueRandomStringList.size());
        /*
        for (String s:uniqueRandomStringList) {
            //System.out.println(s);

        }

         */


        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            /*
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM conference");

            while (rs.next()) {
                //System.out.println(rs.getRow() + ". " + rs.getString("value"));
            }

             */





            List<String> tableNames = new ArrayList<String>();
            tableNames.add("Spacecraft");
            tableNames.add("Commander");
            tableNames.add("Flight");
            tableNames.add("Planet");



            String sql = "TRUNCATE TABLE ?";
            PreparedStatement deleteStatement = connection.prepareStatement(sql);
            deleteStatement.setString(1,"Spacecraft");
            deleteStatement.executeUpdate();

            /*
            for(String string:tableNames){
                System.out.println(string);
                System.out.printf("DELETE FROM %s\n",string);
                deleteStatement.setString(1,string);
                System.out.printf("Таблица %s очищена, удалено %d строк \n",string,deleteStatement.executeUpdate());
            }

             */


            sql = "INSERT INTO Spacecraft (id,name,service_life,birth_year) Values (?, ?, ?, ?)";
            PreparedStatement isertStatement = connection.prepareStatement(sql);









            for(int i = 0;i < 20;i++){

                isertStatement.setInt(1,i+1);
                isertStatement.setString(2,uniqueRandomStringList.remove(i));
                isertStatement.setInt(3,new Random().nextInt(10));
                isertStatement.setInt(4,new Random().nextInt(30)+2000);
                int rows = isertStatement.executeUpdate();

                System.out.printf("%d rows added \n", rows);

            }




        } catch (SQLException e) {
            //System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }









    }
}
