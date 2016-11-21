package data;

import helpers.Artist;
import helpers.StateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import ui.UI;

import static helpers.Artist.HEIGHT;
import static helpers.Artist.WIDTH;

/**
 * Character Editor for game, made initially to set stats for the characters available to be used as soldiers in game.
 * In future supposed to handle lvl-ups and experience allocation as well as choosing which of the different characters
 * one wishes to use in an upcoming game. Might develop it into injuries that need to be healed, etc.
 *
 * Work in progress.
 *
 * Created by Kristoffer on 2016-09-06.
 *
 */
public class CharacterEditor {

    private Texture background;
    private UI menuUI;

    public CharacterEditor(){

        background = Artist.quickLoad("characterEditorBG");
        menuUI = new UI();
        menuUI.addButton("Play", "playButton", WIDTH /2 - 500, (int) (HEIGHT * 0.60f));
        menuUI.addButton("Editor", "editorButton", WIDTH / 2 - 500, (int) (HEIGHT * 0.50f));

    }

    private void updateButtons(){
        if(Mouse.isButtonDown(0)){
            if(menuUI.isButtonClicked("Play")){
                StateManager.setGameState(StateManager.GameState.GAME);
            }
            if(menuUI.isButtonClicked("Editor")){
                StateManager.setGameState(StateManager.GameState.EDITOR);
            }
        }

        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                StateManager.setGameState(StateManager.GameState.MAINMENU);
            }
        }
    }

    public void update() {
        Artist.drawQuadTex(background, 0, 0, 2048, 1024);
        menuUI.draw();
        updateButtons();
    }
}
