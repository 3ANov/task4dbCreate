import org.apache.commons.text.RandomStringGenerator;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.stream.Stream;

public class Task4dbCreate {
    public static void main(String[] args) throws SQLException{
        char [][] pairs = {{'a','z'},{'A','Z'}};
        List<String> uniqueRandomStringList = new ArrayList<String>();
        
        /*List<String> uniqueRandomSpacecraftID = new ArrayList<String>();
        List<String> uniqueRandomPlanetID = new ArrayList<String>();
        List<String> uniqueRandomComanderID = new ArrayList<String>();

         */
        

        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/test_db_stepik_course";
        String USER = "testuser";
        String PASS = "testuser";

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

            String sql = "CREATE TABLE Spacecraft(\n" +
                    "  id SERIAL PRIMARY KEY,\n" +
                    "  name TEXT UNIQUE,\n" +
                    "  service_life INT DEFAULT 1000,\n" +
                    "  birth_year INT CHECK(birth_year > 0)\n" +
                    ");" +
                    "CREATE TABLE Planet(\n" +
                    "  id SERIAL PRIMARY KEY,\n" +
                    "  name TEXT UNIQUE,\n" +
                    "  distance NUMERIC(5,2),\n" +
                    "  galaxy INT CHECK(galaxy > 0)\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE Commander(\n" +
                    "  id SERIAL PRIMARY KEY,\n" +
                    "  name TEXT\n" +
                    ");\n" +
                    "\n" +
                    "CREATE TABLE Flight(\n" +
                    "  id INT PRIMARY KEY,\n" +
                    "  spacecraft_id INT REFERENCES Spacecraft,\n"+
                    "  planet_id INT REFERENCES Planet,\n" +
                    "  commander_id INT REFERENCES Commander,\n" +
                    "  start_date DATE,\n" +
                    "  UNIQUE(spacecraft_id, start_date),\n"+
                    "  UNIQUE(commander_id, start_date)\n" +
                    ");\n";
            Statement createStatement = connection.createStatement();
            createStatement.execute(sql);





            List<String> tableNames = new ArrayList<String>();
            tableNames.add("Spacecraft");
            tableNames.add("Commander");
            tableNames.add("Flight");
            tableNames.add("Planet");




            sql = "";
            for (String string:tableNames) {
                sql+="TRUNCATE "+string+" CASCADE;\n";
            }




            PreparedStatement deleteStatement = connection.prepareStatement(sql);

            //deleteStatement.setString(1,"Spacecraft");
            System.out.println(deleteStatement.executeUpdate());





            /*
            for(String string:tableNames){
                System.out.println(string);
                System.out.printf("DELETE FROM %s\n",string);
                deleteStatement.setString(1,string);
                System.out.printf("Таблица %s очищена, удалено %d строк \n",string,deleteStatement.executeUpdate());
            }

             */


            sql = "INSERT INTO Spacecraft (id,name,service_life,birth_year) Values (?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(sql);

            for(int i = 0;i < 20;i++){

                insertStatement.setInt(1,i+1);
                insertStatement.setString(2,uniqueRandomStringList.remove(i));
                insertStatement.setInt(3,new Random().nextInt(10));
                insertStatement.setInt(4,new Random().nextInt(30)+2000);
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

            }

            sql = "INSERT INTO Planet (id,name,distance,galaxy) Values (?, ?, ?, ?)";

            insertStatement = connection.prepareStatement(sql);


            for(int i = 0;i < 10;i++){

                insertStatement.setInt(1,i+1);
                insertStatement.setString(2,uniqueRandomStringList.remove(i));
                insertStatement.setFloat(3,(float) Math.random()*1000);
                insertStatement.setInt(4,new Random().nextInt(10)+1);
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

            }

            sql = "INSERT INTO Commander (id,name) Values (?, ?)";

            insertStatement = connection.prepareStatement(sql);



            for(int i = 0;i < 10;i++){

                insertStatement.setInt(1,i+1);
                insertStatement.setString(2,uniqueRandomStringList.remove(i));
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

            }


            sql = "INSERT INTO Flight (id,spacecraft_id,planet_id,commander_id,start_date) Values (?, ?, ?, ?, ?)";

            insertStatement = connection.prepareStatement(sql);



            for(int i = 0;i < 30;i++){

                insertStatement.setInt(1,i+1);
                insertStatement.setInt(2,new Random().nextInt(20)+1);
                insertStatement.setInt(3,new Random().nextInt(10)+1);
                insertStatement.setInt(4,new Random().nextInt(10)+1);
                java.sql.Date startDate = new java.sql.Date(new Random().nextInt(2000)+40,new Random().nextInt(12)+1,new Random().nextInt(31)+1);
                insertStatement.setDate(5, startDate);
                insertStatement.executeUpdate();

                //System.out.printf("%d rows added \n", rows);

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
