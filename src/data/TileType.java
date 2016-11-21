package data;

public enum TileType {

    Floor("floortile", true, false), Wall("walltile", false, true), Pit("pittile2", false, false), NULL("pittile", false, true);

    String textureName;
    public boolean walkable;
    boolean solid;

    TileType(String textureName, boolean walkable, boolean solid){
        this.textureName = textureName;
        this.walkable = walkable;
        this.solid = solid;
    }

}
