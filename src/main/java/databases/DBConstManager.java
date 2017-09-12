package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import com.appg.djTalk.common.bean.DataMap;

import databases.exception.NothingToTakeException;

/**
 * @author 함의진
 * @version 1.0.0
 * MySQL 데이터베이스 연결 컨스턴트를 정의하고 연결 작업을 수행하기 위한 클래스
 * Jul-21-2017
 */
public class DBConstManager {

    protected Connection connection = null;
    protected Statement st = null;

    public static final String CONNECTOR = "jdbc";
    public static final String DBMS = "mysql";
    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DBNAME = "djTalk";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "$#@!djtalk";
    public boolean autoConnect = true;

    public String getConnectionInfo(){
        return CONNECTOR + ":" + DBMS + "://" + HOST + ":" + PORT + "/" + DBNAME + "?useUnicode=yes&amp;characterEncoding=UTF-8&amp;autoReconnect=" + autoConnect;
    }

    public boolean setAutoConnect(boolean value){
        this.autoConnect = value;
        return this.autoConnect;
    }

    // TODO PrimaryKey Constraint Violation
    public int getLastInsertId(String table){
        try {
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
            String sql = "SELECT LAST_INSERT_ID() AS number FROM " + table + " LIMIT 1";
            ResultSet rs = st.executeQuery(sql);

            int res = 1;

            while(rs.next()){
                res = rs.getInt("number");
            }

            if(res == 0) res = 1;

            rs.close();
            st.close();

            connection.close();

            return res;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Handled :: getLastInsertId");
            return 1;
        }
    }

    public List<DataMap> getList(String sql){
        List<DataMap> list = new Vector<>();
        try{
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();

            int cols = metaData.getColumnCount();

            while(rs.next()){
                DataMap dataMap = new DataMap();
                for(int i = 1; i <= cols; i++){
                    dataMap.put(metaData.getColumnLabel(i), rs.getObject(i));
                }
                list.add(dataMap);
            }

            rs.close();
            st.close();

        }catch(SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    public DataMap getRow(String sql) throws NothingToTakeException{
        DataMap dataMap = new DataMap();

        try {
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();

            ResultSet rs = st.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();

            int cols = metaData.getColumnCount();

            rs.next();
            for(int i = 1; i <= cols; i++){
                dataMap.put(metaData.getColumnLabel(i), rs.getObject(i));
            }

            rs.close();
            st.close();

            connection.close();

            return dataMap;
        }catch (SQLException e){
            throw new NothingToTakeException();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Handled :: getRow");
            return dataMap;
        }
    }

    public boolean execute(String sql){
        boolean retVal = false;

        try {
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
            retVal = st.execute(sql);
            st.close();
            connection.close();

            return  retVal;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Handled :: execute");
            return false;
        }
    }

    public long getNumber(String sql, String column) throws NothingToTakeException{
        try {
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            long res = 0;

            int isNone = 0;

            while(rs.next()){
                isNone++;
                res = rs.getLong(column);
            }

            if(isNone == 0) throw new NothingToTakeException();

            rs.close();
            st.close();

            connection.close();

            return res;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Handled :: getNumber");
            return 0;
        }
    }

    public List<String> getStrings(String sql, String... column){
        List<String> returnList = new Vector<>();

        try {
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            String res = "";

            while(rs.next()){
                for(String col : column) returnList.add(rs.getString(col));
            }

            rs.close();
            st.close();

            connection.close();

            return returnList;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Handled :: getStrings");
            return returnList;
        }
    }

    public String getString(String sql, String column){
        try {
            connection = DriverManager.getConnection( getConnectionInfo() , USERNAME, PASSWORD);
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);

            String res = "";

            while(rs.next()){
                res = rs.getString(column);
            }

            rs.close();
            st.close();

            connection.close();

            return res;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error Handled :: getString");
            return null;
        }
    }

}
