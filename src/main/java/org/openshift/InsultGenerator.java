package org.openshift;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class InsultGenerator {
    public String generateInsult() {
        String vowels = "AEIOU";
        String article = "an";
        String theInsult = "";

        String databaseUrl = "jdbc:postgresql://";
        databaseUrl += System.getenv("POSTGRESQL_SERVICE_HOST");
        databaseUrl += "/" + System.getenv("POSTGRESQL_DATABASE");

        String userName = System.getenv("POSTGRESQL_USER");
        String password = System.getenv("PGPASSWORD");

        try (Connection connection = DriverManager.getConnection(databaseUrl, userName, password)) {
            String SQL = "select a.string as first, b.string as second, c.string as noun from short_adjective a, long_adjective b, noun c order by random() limit 1";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                if (vowels.indexOf(rs.getString("first").charAt(0)) == -1) {
                    article = "a";
                }
                theInsult = String.format("Thou are %s %s %s %s", article, rs.getString("first"), rs.getString("second"), rs.getString("noun"));
            }
            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            return "Database Problem!";
        }
        return theInsult;
    }

}
