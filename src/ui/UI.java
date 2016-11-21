package ui;


import java.util.ArrayList;

import data.Tile;
import org.lwjgl.input.Mouse;
import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import static helpers.Artist.*;

public class UI {


    private ArrayList<Button> buttonList;
    private ArrayList<String> stringList;
    private TrueTypeFont font;
    private ArrayList<Menu> menuList;
    private Font awtFont;

    public UI(){


        buttonList = new ArrayList<Button>();
        stringList = new ArrayList<String>();
        menuList = new ArrayList<>();
        awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
    }


    public void drawString(int x, int y, String text){

        font.drawString(x, y, text);
    }

    public void addButton(String name, String textureName, int x, int y){
        buttonList.add(new Button(name, quickLoad(textureName), x, y));
    }

    public void addString(String text, int x, int t ){
        stringList.add(text);
    }

    public boolean isButtonClicked(String buttonName){
        Button b = getButton(buttonName);
        float mouseY = HEIGHT - Mouse.getY() -1;
        if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
                mouseY > b.getY() && mouseY < b.getY() + b.getHeight())	{
            return true;
        }
        return false;
    }

    private Button getButton(String buttonName){
        for(Button b: buttonList){
            if(b.getName().equals(buttonName)){
                return b;
            }
        }
        return null;
    }

    public void createMenu(String name, int x, int y){

        menuList.add(new Menu(name, x, y));
    }

    public Menu getMenu(String name){
        for (Menu m :
                menuList) {
            if (name.equals(m.getName()))
                return m;
        }
        return null;
    }

    public void draw(){
        for (Button button : buttonList) {
            drawQuadTex(button.getTexture(), button.getX(), button.getY(), button.getWidth(), button.getHeight());
        }
        menuList.forEach(Menu::draw);
    }


	/*
	 * Menu Class
	 */

    public class Menu {

        String name;
        private ArrayList<Button> menuButtons;
        private int x, y, buttonAmount;

        public Menu(String name, int x, int y){
            this.name = name;
            this.x = x;
            this.y = y;
            this.buttonAmount = 0;
            this.menuButtons = new ArrayList<>();
        }

        public void addButton(Button b) {

            b.setX(x);
            b.setY(y + buttonAmount * (TILE_SIZE + 10));
            buttonAmount++;
            menuButtons.add(b);
        }

        public void quickAddButton(String buttonName, String buttonTextureName){
            addButton(new Button(buttonName, quickLoad(buttonTextureName), 0, 0));
        }

        private Button getButton(String buttonName){
            for(Button b: menuButtons){
                if(b.getName().equals(buttonName)){
                    return b;
                }
            }
            return null;
        }

        public boolean isButtonClicked(String buttonName){
            Button b = getButton(buttonName);
            float mouseY = HEIGHT - Mouse.getY() -1;
            if(Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() &&
                    mouseY > b.getY() && mouseY < b.getY() + b.getHeight())	{
                return true;
            }
            return false;
        }

        public void draw(){

            drawQuadTex(quickLoad("soldierMenuBackground"), x, y, 165, HEIGHT );

            for (Button b :
                    menuButtons) {
                drawQuadTex(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }

        public String getName() {
            return name;
        }
    }




}
