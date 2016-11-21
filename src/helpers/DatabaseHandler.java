package helpers;

import data.Character;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Kristoffer on 2016-09-06.
 * DatabaseHandler takes care of connecting to the database as well as
 * providing methods for reading and writing to the database.
 */
public class DatabaseHandler {

    private static Connection myConn = null;
    private static ResultSet rs;
    private static Statement myStmt;
    private static int numSoldiers = 0;
    private static ArrayList<Character> characters = new ArrayList<>();

    /**
     * Establishing the Database for the application as well as printing the info for the time being.
     */
    public static void databaseSession(){
        try {
            System.out.println("Setting up database...\n");
            String jdbcUrl = "jdbc:sqlserver://mssql4.gear.host;user=testusergame;password=tester!;databaseName=kerotestdb";
            //old localhost server url, kept if online server goes down
//            String jdbcUrl = "jdbc:sqlserver://localhost\\(local):1433;user=Test;password=tester1;databaseName=Test";
            myConn = DriverManager.getConnection(jdbcUrl);
            myStmt = myConn.createStatement();

            printInitialInfo();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Selects all the elements in a table for the static field ResultSet.
     * @param table String representation of the wanted table
     * @return returns the wanted ResultSet (Might remove this and make a Setter at a later point) ========***
     */
    private static ResultSet readTableFromDatabase(String table){

//        ResultSet rs = null;
        try {
            rs = getMyStmt().executeQuery("select * from " + table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }


/*    private static Connection getMyConn() {
        return myConn;
    }*/

    /**
     * Closes the connection to the database
     */
    public static void closeDB(){
        try {
            if (myConn != null && !myConn.isClosed()) {
                myConn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Database Connection closed.");
    }

    private static Statement getMyStmt() {
        return myStmt;
    }

    private static void printInitialInfo(){
        /*
              =======PRINTING TABLE========
        */

        rs = readTableFromDatabase("Soldiers");

        System.out.println("===================Tables=====================");
        System.out.println();
        System.out.println();
        System.out.println("ID\t||\t\tName\t||\tSpeed\t||\tRange\t||\tWeaponID");
        System.out.println("=========================================================");
        try {
            while (rs.next()){

                System.out.println(rs.getString(1) + "\t||\t" + rs.getString(2) + " \t||\t " + rs.getString(3) + "  \t||\t " + rs.getString(4) + " \t||\t " + rs.getString(5));
                characters.add(new Character(rs.getInt(1), rs.getString(2).trim(), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
            }

            System.out.println();


            rs = readTableFromDatabase("tblWeapons");
            System.out.println("ID ||  Weapon");
            System.out.println("===================");
            while(rs.next()){
                System.out.println(rs.getString(1) + "  ||  " + rs.getString(2));
            }

            if (myConn != null) {
                System.out.println();
                System.out.println();
                System.out.println("==================_SERVER INFO_======================");

                DatabaseMetaData dm = myConn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                System.out.println("=====================================================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Character> getCharacters() {
        return characters;
    }


    /**
     * Acquires the name from the database with the entered id
     * @return temporary return statement for the time being
     */
    public static String getName(int id){

        rs = readTableFromDatabase("Soldiers");

        String returnName = "Name not loaded";

        try {
            while(rs.next())
                if (Integer.parseInt(rs.getString(1)) == id)
                    returnName = rs.getString(2).trim();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnName;
    }


    public static String getSpeed(int id){

        rs = readTableFromDatabase("Soldiers");

        String returnSpeed = "Speed not loaded";

        try {
            while(rs.next())
                if (Integer.parseInt(rs.getString(1)) == id)
                    returnSpeed = rs.getString(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnSpeed;
    }

    public static String getRange(int id){

        rs = readTableFromDatabase("Soldiers");

        String returnRange = "Range not loaded";

        try {
            while(rs.next())
                if (Integer.parseInt(rs.getString(1)) == id)
                    returnRange = rs.getString(4);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnRange;
    }

    public static String getWeaponID(int id){

        rs = readTableFromDatabase("Soldiers");

        String returnWeaponID = "WeaponID not loaded";

        try {
            while(rs.next())
                if (Integer.parseInt(rs.getString(1)) == id)
                    returnWeaponID = rs.getString(5);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnWeaponID;
    }
}
