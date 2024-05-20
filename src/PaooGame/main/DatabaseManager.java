package PaooGame.main;

import PaooGame.Game;
import PaooGame.objects.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String databaseName;
    Game gp;

    public DatabaseManager(String databaseName, Game gp) {
        this.databaseName = databaseName;
        this.gp = gp;
    }

    public void createTable(String tableName, ArrayList<String> fields) {
        conn = null;
        stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
            for (int i = 0; i < fields.size(); i += 2) {
                sql += fields.get(i) + " " + fields.get(i + 1) + " NOT NULL, ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ");";
            stmt.execute(sql);
            stmt.close();
            conn.close();
            System.out.println("Tabelul " + tableName + " a fost creat cu succes.");
        } catch (ClassNotFoundException e) {
            System.out.println("Eroare: Class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Eroare: Eroare la crearea tabelului.");
            e.printStackTrace();
        }
    }

    public void insertTable(String tableName, ArrayList<String> fields, ArrayList<String> values) {
        conn = null;
        stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "INSERT OR IGNORE INTO " + tableName + " (";
            for(int i = 0; i < fields.size(); ++i) {
                sql += "\"" + fields.get(i) + "\"" + ", ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += ") VALUES (";
            for (int i = 0; i < values.size(); ++i) {
                sql += "\"" + values.get(i) + "\"" + ", ";
            }
            sql = sql.substring(0, sql.length() - 2);
            sql += "); UPDATE " + tableName + " SET ";
            for(int i = 0; i < fields.size(); ++i) {
                sql += "\"" + fields.get(i) + "\"" + "=" + "\"" + values.get(i) + "\"" + ", ";
            }

            sql = sql.substring(0, sql.length() - 2);
            stmt.executeUpdate(sql);
            conn.commit();
            stmt.close();
            conn.close();
            System.out.println("Update la tabelul " + tableName + " cu succes.");
        } catch (ClassNotFoundException e) {
            System.out.println("Eroare: Class not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Eroare: Eroare la inserarea in tabel.");
            e.printStackTrace();
        }
    }
    public void selectTable(String tableName){
        conn = null;
        stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + tableName + ";");

            // Variables
            int playerX = 0;
            int playerY = 0;
            int currentMap = 0;
            String direction = "";
            int life = 0;
            int spawnedDoors = 0;
            int openedDoors = 0;
            int destroyedStone = 0;
            boolean level2HeadInteraction = false;
            boolean level3Eliminated = false;
            int level1Score = 0;
            int level2Score = 0;
            int level3Score = 0;
            int levelScore = 0;
            int levelCounter = 0;
            String inventory = "";
            String monsters = "";
            String npc = "";
            String objects = "";
            int music = 0;
            int fx = 0;
            int key = 0;

            while(rs.next()) {
                playerX = Integer.parseInt(rs.getString("PLAYERPOSX"));
                playerY = Integer.parseInt(rs.getString("PLAYERPOSY"));
                currentMap = rs.getInt("CURRENTMAP");
                direction = rs.getString("DIRECTION");
                life = rs.getInt("LIFE");
                spawnedDoors = rs.getInt("SPAWNEDDOORS");
                openedDoors = rs.getInt("OPENEDDOORs");
                destroyedStone = rs.getInt("DESTROYEDSTONE");
                level2HeadInteraction = rs.getBoolean("LEVEL2HEADINTERACTION");
                level3Eliminated = rs.getBoolean("LEVEL3ELIMINATED");
                level1Score = rs.getInt("LEVEL1SCORE");
                level2Score = rs.getInt("LEVEL2SCORE");
                level3Score = rs.getInt("LEVEL3SCORE");
                levelScore = rs.getInt("LEVELSCORE");
                levelCounter = rs.getInt("LEVELCOUNTER");
                inventory = rs.getString("INVENTORY");
                monsters = rs.getString("MONSTERS");
                npc = rs.getString("NPC");
                objects = rs.getString("OBJECTS");
                music = rs.getInt("MUSIC");
                fx = rs.getInt("FX");
                key = rs.getInt("KEYS");
            }
            gp.player.worldX = playerX;
            gp.player.worldY = playerY;
            gp.currentMap = currentMap;
            gp.player.direction = direction;
            gp.player.life = life;
            gp.spawnedDoors = spawnedDoors;
            gp.openedDoors = openedDoors;
            gp.destroyedStone = destroyedStone;
            gp.level2HeadInteraction = level2HeadInteraction;
            gp.level3Eliminated = level3Eliminated;
            gp.level1Score = level1Score;
            gp.level2Score = level2Score;
            gp.level3Score = level3Score;
            gp.levelScore = levelScore;
            gp.levelCounter = levelCounter;
            gp.music.volumeScale = music;
            gp.fx.volumeScale = fx;
            gp.player.keyNumber = key;

            // Parse
            String[] arrayInventory = inventory.split(", ");
            for(int i = 0; i < arrayInventory.length; ++i) {
                switch(arrayInventory[i]) {
                    case "Electron":
                        gp.player.inventory.add(new OBJ_Electron(gp));
                        break;
                    case "HealPotion":
                        gp.player.inventory.add(new OBJ_HealPotion(gp));
                        break;
                    case "KTPY":
                        gp.player.inventory.add(new OBJ_KTPY(gp));
                        break;
                    case "Skargun":
                        gp.player.inventory.add(new OBJ_Skargun(gp));
                        break;
                    case "Snaipa":
                        gp.player.inventory.add(new OBJ_Snaipa(gp));
                        break;
                }
            }
            String[] arrayMonsters = monsters.split(", ");
            int k = 0;
            for(int i = 0; i < gp.mst.length; ++i) {
                for(int j = 0; j < gp.mst[i].length; ++j) {
                    if(gp.mst[i][j] != null) {
                        gp.mst[i][j].worldX = Integer.parseInt(arrayMonsters[k]);
                        gp.mst[i][j].worldY = Integer.parseInt(arrayMonsters[k + 1]);
                        gp.mst[i][j].life = Integer.parseInt(arrayMonsters[k + 2]);
                    }
                    k += 3;
                }
            }
            String[] arrayNPC = npc.split(", ");
            k = 0;
            for(int i = 0; i < gp.NPC.length; ++i) {
                for(int j = 0; j < gp.NPC[i].length; ++j) {
                    if(gp.NPC[i][j] != null) {
                        gp.NPC[i][j].worldX = Integer.parseInt(arrayNPC[k]);
                        gp.NPC[i][j].worldY = Integer.parseInt(arrayNPC[k + 1]);
                    }
                    k += 2;
                }
            }
            String[] arrayObjects = objects.split(", ");
            k = 0;
            for(int i = 0; i < gp.obj.length; ++i) {
                for(int j = 0; j < gp.obj[i].length; ++j) {
                    if(gp.obj[i][j] != null) {
                        gp.obj[i][j].worldX = Integer.parseInt(arrayObjects[k]);
                        gp.obj[i][j].worldY = Integer.parseInt(arrayObjects[k + 1]);
                    }
                    k += 2;
                }
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch(ClassNotFoundException e) {
            System.out.println("Eroare: Class not found.");
            e.printStackTrace();
        } catch(SQLException e) {
            System.out.println("Eroare: Eroare la extragerea datelor din tabel.");
            e.printStackTrace();
        }
    }
}


















//
//    public void saveData(String tableName, ArrayList<String> fields, ArrayList<String> values) {
//        try {
//            Statement stmt = conn.createStatement();
//            conn.setAutoCommit(false); // Start a transaction (optional)
//
//            // Check if table exists
//            if (!tableExists(tableName)) {
//                createTable(tableName, fields); // Create table if it doesn't exist
//            }
//
//            String sql = "INSERT INTO " + tableName + " (" + String.join(", ", fields) + ") VALUES (" + String.join(", ", values) + ")";
//            System.out.println(sql); // Print the constructed SQL for debugging
//
//            stmt.executeUpdate(sql);
//            conn.commit(); // Commit the transaction (if started)
//            stmt.close();
//            System.out.println("Datele au fost salvate cu succes in tabela " + tableName);
//        } catch (SQLException e) {
//            System.out.println("Eroare la salvarea datelor in tabela " + tableName + ".");
//            e.printStackTrace();
//            try {
//                conn.rollback(); // Rollback the transaction if an error occurs
//            } catch (SQLException ex) {
//                System.out.println("Eroare la anularea tranzactiei.");
//                ex.printStackTrace();
//            }
//        }
//    }
//    private ArrayList<String> getTableColumns(String tableName) throws SQLException {
//        ArrayList<String> columns = new ArrayList<>();
//        String sql = "PRAGMA table_info(?)";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setString(1, tableName);
//        ResultSet res = stmt.executeQuery();
//        while (res.next()) {
//            columns.add(res.getString("name"));
//            columns.add(res.getString("type")); // You might want to use this to infer data types for creating the table
//        }
//        res.close();
//        stmt.close();
//        return columns;
//    }
//    public ArrayList<ArrayList<String>> getData(String tableName) {
//        ArrayList<ArrayList<String>> rows = new ArrayList<>();
//        try {
//            Statement stmt = conn.createStatement();
//            conn.setAutoCommit(false); // Start a transaction (optional)
//
//            // Check if table exists
//            if (!tableExists(tableName)) {
//                System.out.println("Tabela " + tableName + " nu exista. Se creeaza tabela.");
//                createTable(tableName, getTableColumns(tableName)); // Create table with default columns if it doesn't exist
//            }
//
//            String sql = "SELECT * FROM " + tableName;
//            ResultSet res = stmt.executeQuery(sql);
//
//            while (res.next()) {
//                ArrayList<String> row = new ArrayList<>();
//                for (int i = 1; i <= res.getMetaData().getColumnCount(); i++) {
//                    String columnName = res.getMetaData().getColumnName(i);
//                    String columnValue = res.getString(i);
//                    row.add(columnName + ": " + columnValue);
//                }
//                rows.add(row);
//            }
//
//            res.close();
//            stmt.close();
//            System.out.println("Datele au fost extrase cu succes din tabela " + tableName);
//        } catch (SQLException e) {
//            System.out.println("Eroare la extragerea datelor din tabela " + tableName + ".");
//            e.printStackTrace();
//            try {
//                conn.rollback(); // Rollback the transaction if an error occurs
//            } catch (SQLException ex) {
//                System.out.println("Eroare la anularea tranzactiei.");
//                ex.printStackTrace();
//            }
//        }
//        return rows;
//    }
//
//    private boolean tableExists(String tableName) throws SQLException {
//        String sql = "SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?";
//        PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setString(1, tableName);
//        ResultSet res = stmt.executeQuery();
//        boolean exists = res.next();
//        res.close();
//        stmt.close();
//        return exists;
//    }
//
//    private void createTable(String tableName, ArrayList<String> fields) throws SQLException {
//        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
//        for (int i = 0; i < fields.size(); i += 2) {
//            sql += fields.get(i) + " " + fields.get(i + 1) + ", ";
//        }
//        // Remove the trailing comma and space
//        sql = sql.substring(0, sql.length() - 2) + ");";
//        System.out.println("Tabela " + tableName + " creata cu succes.");
//        stmt.executeUpdate(sql);
//    }
//}