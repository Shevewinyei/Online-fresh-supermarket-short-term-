package oline_fresh_supermaket.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtil {
	//1.�����Ա���� DataSource
    private static DataSource ds;
    static {
        try {
            //1.���������ļ�
            Properties pro = new Properties();
            pro.load(JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            //2.��ȡDateSource
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /*
        ��ȡ���ӵķ���
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
 
    /*
        �ͷ���Դ
     */
    public static void close(Statement stmt, Connection conn){
        /*if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();//�黹����
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        close(null,stmt,conn);
    }
    public static void close(ResultSet rs,Statement stmt, Connection conn){
        if (rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null){
            try {
                conn.close();//�黹����
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        ��ȡ���ӳصķ���
     */
    public static DataSource getDataSource(){
        return ds;
    }
}