package databases;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 함의진
 * @version 1.0.0
 * MySQL 데이터베이스 연결을 위한 싱글턴 클래스
 * Jul-21-2017
 */
public class DBMSManager extends DBMSConstManager {

    private static DBMSManager instance;

    public static DBMSManager getInstance(){
        if(instance == null) instance = new DBMSManager();
        return instance;
    }
    
    private DBMSManager(){
        try {
//            Class.forName("com.databases.jdbc.Driver");
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
        } catch (SQLException se1) {
            se1.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) st.close();
            } catch (SQLException se2) {}
            try {
                if (connection != null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}