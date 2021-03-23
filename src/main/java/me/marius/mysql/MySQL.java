package me.marius.mysql;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.internal.entities.UserById;

import java.nio.channels.Channel;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MySQL {

    private static Connection con;
    private String host = "localhost";
    private int port = 3306;
    private String database = "discordbot";
    private String username = "root";
    private String password = "pP8nt2J9t5xpdUiN";

    private boolean isRunningCreateNewPlayer;
    private boolean isRunningUpdatePlayer;
    private boolean isRunningGetPoints;

    public HashMap<Integer, String> ranking = new HashMap<>();

    public boolean connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,username,password);
            System.out.println("[BaumbalabungaBot] Die Verbindung zur MySQL-Datenbank wurde hergestellt!");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("[BaumbalabungaBot] Die Verbindung zur MySQL-Datenbank wurde nicht hergestellt!");
        }
        return false;
    }

    public boolean isConnected() { return (con == null ? false : true); }

    public void disconnect() {
        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public boolean userIsExisting(String userID){
        if(!isConnected())
            if(!connect())
                return false;

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Ranking WHERE UserID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("UserID").equals(userID))
                    return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void createTable(){
        if(!isConnected())
            return;

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS Ranking (UserID VARCHAR(100), Username VARCHAR(100), Punkte INT)");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createNewPlayer(String userID, String userName, int punkte){

        isRunningCreateNewPlayer = !isRunningCreateNewPlayer;

        new Thread(() -> {
            while (isRunningCreateNewPlayer){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!isConnected())
                    if(!userIsExisting(userID))
                        return;

                try {
                    PreparedStatement ps = con.prepareStatement("INSERT INTO ranking (UserID,Username,Punkte) VALUES (?,?,?)");
                    ps.setString(1, userID);
                    ps.setString(2, userName);
                    ps.setInt(3, punkte);
                    ps.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                isRunningCreateNewPlayer = false;
            }
        }).start();
    }

    public void updatePlayer(String userID, String username, int punkte){

        isRunningUpdatePlayer = !isRunningUpdatePlayer;

        new Thread(() -> {
            while(isRunningUpdatePlayer){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!isConnected())
                    if(!userIsExisting(userID))
                        return;

                try {
                    PreparedStatement ps = con.prepareStatement("UPDATE ranking SET Punkte = ? WHERE UserID = ?");
                    ps.setInt(1, getPoints(userID)+punkte);
                    ps.setString(2, userID);
                    ps.executeUpdate();

                    PreparedStatement ps1 = con.prepareStatement("UPDATE ranking SET Username = ? WHERE UserID = ?");
                    ps1.setString(1, username);
                    ps1.setString(2, userID);
                    ps1.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                isRunningUpdatePlayer = false;
            }
        }).start();
    }

    public int getPoints(String userID){

        try {
            PreparedStatement ps = con.prepareStatement("SELECT Punkte FROM ranking WHERE UserID = ?");
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                return rs.getInt("Punkte");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public ArrayList<String> getRanking(){
        if(!isConnected())
            if(!connect())
                return null;

        ArrayList<String> ranking = new ArrayList<>();
        ranking.clear();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM ranking ORDER BY Punkte DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ranking.add(rs.getString("UserID"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ranking;
    }
}
