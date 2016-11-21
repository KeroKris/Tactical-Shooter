package helpers;

import static helpers.LevelManager.loadMap;

import data.CharacterEditor;
import data.Editor;
import data.Game;
import data.MainMenu;

public class StateManager {

    public enum GameState{
        MAINMENU, GAME, EDITOR, CHARACTEREDITOR

    }
    public static GameState gameState = GameState.MAINMENU;
    public static MainMenu mainMenu;
    public static Game game;
    public static Editor editor;
    public static CharacterEditor characterEditor;

    private static long nextSecond = System.currentTimeMillis() + 1000;
    private static int framesInLastSecond = 0;
    private static int framesInCurrentSecond = 0;

    public static void update(){

        switch (gameState) {
            case MAINMENU:
                if(mainMenu == null){
                    mainMenu = new MainMenu();
                }
                mainMenu.update();
                break;

            case GAME:
                if(game==null){
                    game = new Game(loadMap("mapTest2"));
                }
                game.update();
                break;

            case EDITOR:
                if(editor == null) {
                    editor = new Editor();
                }
                editor.update();

                break;

            case CHARACTEREDITOR:
                if(characterEditor == null){
                    characterEditor = new CharacterEditor();
                }
                characterEditor.update();
                break;
        }

        //Fps counter
        long currentTime = System.currentTimeMillis();
        if(currentTime > nextSecond){
            nextSecond += 1000;
            framesInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0;
        }
        framesInCurrentSecond++;
    }


    public static void setGameState(GameState gameState) {
        StateManager.gameState = gameState;
    }

    public static long getFps(){
        return framesInLastSecond;
    }




}
