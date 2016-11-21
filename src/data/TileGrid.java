package data;


import static helpers.Artist.TILE_SIZE;

public class TileGrid {
    public Tile[][] map;
    private int tilesWide, tilesHigh;
    public static final int MAPWIDTH = 20*TILE_SIZE;
    public static final int MAPHEIGHT = 15*TILE_SIZE;


    public TileGrid() {

        this.tilesWide = 20;
        this.tilesHigh = 15;
        map = new Tile[20][15];


        for(int i = 0; i<map.length; i++){
            for(int j = 0; j<map[i].length; j++){
                map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Floor);

            }
        }

    }

    public TileGrid(int[][] newMap){

        this.tilesWide = newMap[0].length;
        this.tilesHigh = newMap.length;

        map = new Tile[20][15];
        for(int i = 0; i<map.length; i++){
            for(int j = 0; j<map[i].length; j++){

                switch(newMap[j][i]){
                    case 0:
                        map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Floor);
                        break;
                    case 1:
                        map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Wall);
                        break;
                    case 2:
                        map[i][j] = new Tile(i*TILE_SIZE, j*TILE_SIZE, TILE_SIZE, TILE_SIZE, TileType.Pit);
                        break;

                }

            }
        }
    }


    public void setTile(int xCoord, int yCoord, TileType type) {

        map[xCoord][yCoord] = new Tile(xCoord*TILE_SIZE, yCoord*TILE_SIZE, TILE_SIZE, TILE_SIZE, type);
    }



    public Tile getTile(int xCoord, int yCoord) {
        xCoord /= TILE_SIZE;
        yCoord /= TILE_SIZE;

        if(xCoord >= 0 && xCoord < tilesWide  && yCoord < tilesHigh && yCoord >= 0 ){

            return map[xCoord][yCoord];
        }
        else{
            return new Tile(0, 0, 0, 0, TileType.NULL);
        }
    }

    public boolean isTileSolid(Tile t){
        if (t.getType().solid) return true;
        return false;
    }

    public void draw(){
        for(int i = 0; i<map.length; i++){
            for(int j = 0; j<map[i].length; j++){
                map[i][j].draw();

            }
        }


    }

    public int getTilesWide() {
        return tilesWide;
    }

    public void setTilesWide(int tilesWide) {
        this.tilesWide = tilesWide;
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public void setTilesHigh(int tilesHigh) {
        this.tilesHigh = tilesHigh;
    }




}
