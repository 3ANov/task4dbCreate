import org.apache.commons.text.RandomStringGenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Task4dbCreate {
    public static void main(String[] args) {
        char [][] pairs = {{'a','z'},{'A','Z'}};
        List<String> uniqueRandomStringList = new ArrayList<String>();

        String DB_URL = "jdbc:postgresql://127.0.0.1:5432/task2db";
        String USER = "postgres";
        String PASS = "gfhjkmjb";

        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange(pairs).build();
        for (int i = 0; i < 100; i++) {
            //System.out.println(generator.generate(6));
            uniqueRandomStringList.add(generator.generate(6));
        }

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.




        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }



        HashSet hs = new HashSet();
        hs.addAll(uniqueRandomStringList);
        uniqueRandomStringList.clear();
        uniqueRandomStringList.addAll(hs);





    }
}
