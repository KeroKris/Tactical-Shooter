package data;

import static helpers.Artist.*;
import static helpers.LevelManager.loadMap;
import static helpers.LevelManager.saveMap;

import helpers.Artist;
import helpers.StateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import ui.UI;

public class Editor {

    private TileGrid grid;
    private int index;
    private TileType[] types;
    private UI editorUI;
    private UI.Menu tileMenu;
    private boolean mapSaved;
    private Texture gameMenuBackground;

    public Editor(){

        this.grid = loadMap("mapTest2");
        this.index = 0;

        this.types = new TileType[3];
        this.types[0] = TileType.Floor;
        this.types[1] = TileType.Wall;
        this.types[2] = TileType.Pit;
        gameMenuBackground = quickLoad("gameMenuBackground");
        setupUI();

    }


    private void setupUI(){
        editorUI = new UI();

        editorUI.createMenu("TileMenu", getWidth() - 180, 130);
        tileMenu = editorUI.getMenu("TileMenu");
//		tileMenu.addButton(new Button("Floor", quickLoad("floortile"), 0, 0));
        tileMenu.quickAddButton("Floor", "floortile");
        tileMenu.quickAddButton("Wall", "walltile");
        tileMenu.quickAddButton("Pit", "pittile");
        tileMenu.quickAddButton("SaveButton", "soldierBackground");
//		tileMenu.addButton(new Button("Wall", quickLoad("walltile"), 0, 0));
//		tileMenu.addButton(new Button("Pit", quickLoad("pittile"), 0, 0));
//		tileMenu.addButton(new Button("Soldier4", quickLoad("soldierBackground"), 0, 0));
//		tileMenu.addButton(new Button("Soldier5", quickLoad("soldierBackground"), 0, 0));


    }

    public void update() {
        grid.draw();

        updateUI();



        boolean leftMouseButtonDown = false;
        boolean rightMouseButtonDown = false;


        /**
         *
         * Draws tiles on the Map
         */
        if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {
            if(Mouse.getX() < TileGrid.MAPWIDTH && Mouse.getY() < TileGrid.MAPHEIGHT)
                if(grid.getTile(Mouse.getX(), Mouse.getY()).getType() != TileType.NULL){

                    setTile();
                    mapSaved = false;
                }
        }
        leftMouseButtonDown = Mouse.isButtonDown(0);

        if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
        }
        rightMouseButtonDown = Mouse.isButtonDown(0);

        /**
         * Handles Keyboard input
         */
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
                moveTileIndex();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_TAB && Keyboard.getEventKeyState()) {
                moveTileIndex();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
                setTileIndex(0);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
                setTileIndex(1);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
                setTileIndex(2);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_S && Keyboard.getEventKeyState()) {
                saveMap("mapTest2", grid);
                mapSaved = true;
            }
            if(Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()){
                StateManager.setGameState(StateManager.GameState.MAINMENU);
            }
        }
        if(mapSaved){
            editorUI.drawString(100, HEIGHT - 35,"Map saved");
        }
    }

    private void updateUI() {
        Artist.drawQuadTex(gameMenuBackground, 1280, 0, 260, 1680);
        editorUI.draw();

        drawQuadTex(quickLoad(types[index].textureName),grid.getTile(Mouse.getX(), Mouse.getY()).getX(), HEIGHT - grid.getTile(Mouse.getX() , Mouse.getY()+64).getY(),60,60);

        if(Mouse.next()){

            boolean mouseClicked = Mouse.isButtonDown(0);
            if(mouseClicked){
                if(tileMenu.isButtonClicked("Floor")){
                    setTileIndex(0);
                }
                if(tileMenu.isButtonClicked("Wall")){
                    setTileIndex(1);
                }
                if(tileMenu.isButtonClicked("Pit")){
                    setTileIndex(2);
                }
                if(tileMenu.isButtonClicked("SaveButton")){
                    saveMap("mapTest2", grid);
                    mapSaved = true;
                }
                if(tileMenu.isButtonClicked("Floor")){

                }
            }
        }
    }


    /**
     * Iterates through the different Tile Types.
     */
    private void moveTileIndex() {
        index++;
        index %= types.length;
    }

    public void setTileIndex(int index) {
        this.index = index;
    }

    private void setTile() {
        grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 64),
                types[index]);
    }

}
