package data;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;
import static helpers.Artist.drawQuadTex;
import static helpers.Artist.quickLoad;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import helpers.StateManager;
import helpers.StateManager.GameState;
import ui.UI;

public class MainMenu {

    private Texture background;
    private UI menuUI;
    public MainMenu(){
        background = quickLoad("mainmenu2");
        menuUI = new UI();
        menuUI.addButton("Play", "playButton", WIDTH /2 - 286, (int) (HEIGHT * 0.41f));
        menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 286, (int) (HEIGHT * 0.51f));
        menuUI.addButton("Characters", "characterButton", WIDTH/2 - 286, (int) (HEIGHT * 0.61f));
        menuUI.addButton("Quit", "quitButton", WIDTH /2 - 286, (int) (HEIGHT * 0.71f));
    }

    private void updateButtons(){
        if(Mouse.isButtonDown(0)){
            if(menuUI.isButtonClicked("Play")){
                StateManager.setGameState(GameState.GAME);
            }
            if(menuUI.isButtonClicked("Editor")){
                StateManager.setGameState(GameState.EDITOR);
            }
            if(menuUI.isButtonClicked("Characters")){
                StateManager.setGameState(GameState.CHARACTEREDITOR);
            }
            if(menuUI.isButtonClicked("Quit")) {
                System.exit(0);
            }

        }


        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                System.exit(0);
            }
        }
    }

    public void update() {
        drawQuadTex(background, 0, 0, 2048, 1024);
        menuUI.draw();
        updateButtons();
    }

}
