package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import data.Tile;
import data.TileGrid;
import data.TileType;

public class LevelManager {

    public static void saveMap(String mapName, TileGrid grid) {
        String mapData = "";
        for (int i = 0; i < grid.getTilesWide(); i++) {
            for (int j = 0; j < grid.getTilesHigh(); j++) {
                mapData += getTileID(grid.getTile(i * 64, j * 64));
            }
        }

        try {
            File file = new File(mapName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static TileGrid loadMap(String mapName) {

        TileGrid grid = new TileGrid();

        try {
            BufferedReader br = new BufferedReader(new FileReader(mapName));
            String data = br.readLine();

            for (int i = 0; i < grid.getTilesWide(); i++) {
                for (int j = 0; j < grid.getTilesHigh(); j++) {
                    grid.setTile(i, j,
                            getTileType(data.substring(i * grid.getTilesHigh() + j, i * grid.getTilesHigh() + j + 1)));
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grid;
    }

    public static TileType getTileType(String ID) {
        TileType type = TileType.NULL;

        switch (ID) {
            case "F":
                type = TileType.Floor;
                break;
            case "W":
                type = TileType.Wall;
                break;
            case "P":
                type = TileType.Pit;
                break;
            case "0":
                type = TileType.NULL;
                break;
        }
        return type;
    }

    public static String getTileID(Tile t) {
        String ID = "E";

        switch (t.getType()) {
            case Floor:
                ID = "F";
                break;
            case Wall:
                ID = "W";
                break;
            case Pit:
                ID = "P";
                break;
            case NULL:
                ID = "0";
                break;

        }

        return ID;
    }
}
