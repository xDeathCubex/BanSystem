package com.xdeathcubex.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.UUID;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.xdeathcubex.utils.TimeUnit;

public class MySQL {

    static BoneCP connectionPool = null;
    static Connection connection = null;


    public MySQL(String host, String user, String pw, String db) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try {
            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl("jdbc:mysql://" + host + "/" + db + "?autoReconnect=true");
            config.setUsername(user);
            config.setPassword(pw);
            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            config.setDisableConnectionTracking(true);
            connectionPool = new BoneCP(config);
            connection = connectionPool.getConnection();
            if (connection != null) {
                System.out.println("MySQL connected successful!");
                Statement st = connection.createStatement();
                st.executeUpdate("CREATE TABLE IF NOT EXISTS CurrentBans(UUID Varchar(255) PRIMARY KEY, bannedBy VARCHAR(255), Reason VARCHAR(255), startTime VARCHAR(255), endTime VARCHAR(255))");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS PastBans(UUID Varchar(255), bannedBy VARCHAR(255), Reason VARCHAR(255), startTime VARCHAR(255), endTime VARCHAR(255))");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS CurrentMutes(UUID Varchar(255) PRIMARY KEY, mutedBy VARCHAR(255), Reason VARCHAR(255), startTime VARCHAR(255), endTime VARCHAR(255))");
                st.executeUpdate("CREATE TABLE IF NOT EXISTS PastMutes(UUID Varchar(255), mutedBy VARCHAR(255), Reason VARCHAR(255), startTime VARCHAR(255), endTime VARCHAR(255))");



            }
        } catch (SQLException e) {
            e.printStackTrace();
            connectionPool.shutdown();
        }
    }


    public static String getCurrentBan(String what, String uuid){
        String toget = null;
        if(connection != null) {
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM CurrentBans WHERE UUID = '" + uuid + "'");
                while (rs.next()) {
                    toget = rs.getString(what);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return toget;
    }

    public static String getCurrentMute(String what, String uuid){
        String toget = null;
        if(connection != null) {
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM CurrentMutes WHERE UUID = '" + uuid + "'");
                while (rs.next()) {
                    toget = rs.getString(what);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return toget;
    }

    public static ArrayList<String> getPastBans(String what, String uuid){
        ArrayList<String> toget = new ArrayList<String>();
        if(connection != null) {
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM PastBans WHERE UUID = '" + uuid + "'");
                while (rs.next()) {
                    toget.add(rs.getString(what));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return toget;
    }

    public static ArrayList<String> getPastMutes(String what, String uuid){
        ArrayList<String> toget = new ArrayList<String>();
        if(connection != null) {
            try {
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM PastMutes WHERE UUID = '" + uuid + "'");
                while (rs.next()) {
                    toget.add(rs.getString(what));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return toget;
    }

    public static void banUser(String uuid, String fromWho, String reason, long startTime){
        if(connection != null) {
                try {
                    Statement st = connection.createStatement();
                    st.executeUpdate("INSERT INTO `CurrentBans`(`UUID`, `bannedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','permanent')");
                    st.executeUpdate("INSERT INTO `PastBans`(`UUID`, `bannedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','permanent')");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    public static void tempbanUser(String uuid, String fromWho, String reason, long startTime, String endTime){
        if(connection != null) {
                try {
                    Statement st = connection.createStatement();
                    st.executeUpdate("INSERT INTO `CurrentBans`(`UUID`, `bannedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','"+endTime+"')");
                    st.executeUpdate("INSERT INTO `PastBans`(`UUID`, `bannedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','"+endTime+"')");
                } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
    }

    public static void muteUser(String uuid, String fromWho, String reason, long startTime){
        if(connection != null) {
                try {
                    Statement st = connection.createStatement();
                    st.executeUpdate("INSERT INTO `CurrentMutes`(`UUID`, `mutedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','permanent')");
                    st.executeUpdate("INSERT INTO `PastMutes`(`UUID`, `mutedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','permanent')");
                } catch (SQLException e) {
                    e.printStackTrace();
            }
        }
    }
    public static void tempmuteUser(String uuid, String fromWho, String reason, long startTime, String endTime){
        if(connection != null) {
                try {
                    Statement st = connection.createStatement();
                    st.executeUpdate("INSERT INTO `CurrentMutes`(`UUID`, `mutedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','"+endTime+"')");
                    st.executeUpdate("INSERT INTO `PastMutes`(`UUID`, `mutedBy`, `Reason`, `startTime`, `endTime`) VALUES ('" + uuid + "','"+fromWho +"','"+reason+"','"+startTime +"','"+endTime+"')");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void unbanUser(String uuid){
        if(connection != null) {
            try {
                Statement st = connection.createStatement();
                    st.executeUpdate("DELETE FROM CurrentBans WHERE UUID = '" + uuid + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void unmuteUser(String uuid){
        if(connection != null) {
            try {
                    Statement st = connection.createStatement();
                    st.executeUpdate("DELETE FROM CurrentMutes WHERE UUID = '" + uuid + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isCurrentlyBanned(String uuid){
        return TimeUnit.checkBan(uuid);
    }
    public static boolean isCurrentlyMuted(String uuid){
        return TimeUnit.checkMute(uuid);
    }


    public static String getRank(String uuid){
        String toget = null;
        if(connection != null){
            try{
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM RankSystem WHERE UUID = '" + uuid + "'");
                while(rs.next()){
                    toget = rs.getString("rank");
                }
            }catch (SQLException e){ e.printStackTrace(); }
        }
        return toget;
    }
    public static void changeBanReason(String uuid, String newReason){
        if(connection != null){
            try{
                Statement st = connection.createStatement();
                st.executeUpdate("UPDATE CurrentBans SET `Reason`='"+newReason+"' WHERE UUID = '" + uuid + "'");
            }catch(SQLException e){ e.printStackTrace(); }
        }
    }    public static void changeMuteReason(String uuid, String newReason){
        if(connection != null){
            try{
                Statement st = connection.createStatement();
                st.executeUpdate("UPDATE CurrentMutes SET `Reason`='"+newReason+"' WHERE UUID = '" + uuid + "'");
            }catch(SQLException e){ e.printStackTrace(); }
        }
    }

}
