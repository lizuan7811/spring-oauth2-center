package oauth2ResourcesServer.smltool;

import org.apache.logging.log4j.util.Strings;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InsertDatasSourceJDBC {

    private String filePath = "E://lizdbtabledatas.sql";

    public void inssertData() {
        Connection conn = null;
        PreparedStatement prep = null;

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            List<String> sqlList = Files.readAllLines(Paths.get(filePath));

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver://192.168.49.128:31433;databaseName=lizdb;encrypt=true;trustServerCertificate=true;", "sa", "Mars@@@7811");
            int i = 0;
            String sqlStr = Strings.EMPTY;
            while ((sqlStr = br.readLine()) != null) {
                System.out.println(">>>\t" + sqlStr);
                prep = conn.prepareStatement(sqlStr);
                prep.execute();
            }
        }catch(IOException | ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }finally{
                try {
                    if (prep != null) {
                        prep.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
}
