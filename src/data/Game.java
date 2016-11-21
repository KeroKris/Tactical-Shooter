package data;

import helpers.Artist;
import helpers.StateManager;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;
import ui.Button;
import ui.UI;

import static helpers.Artist.*;


public class Game {

    private static TileGrid grid;
    private Player player;
    private WaveManager waveManager;
    private UI gameUI;
    private UI.Menu soldierMenu;
    public static int soldierPick = 0;
    private boolean mouseClicked;
    private Texture gameMenuBackground;


    public Game(TileGrid grid) {
        this.grid = grid;
        Tile startTile = grid.getTile(120, 120);
        int enemyHealth = 1500;
        int enemySpeed = 20;

        int enemyWidth = TILE_SIZE / 2;
        int enemyHeight = enemyWidth *2;
        int enemiesPerWave = 30;
        int timeBetweenEnemies = 3;
        waveManager = new WaveManager(new Enemy(quickLoad("enemy2"), startTile, enemyWidth, enemyHeight, enemySpeed, grid, enemyHealth), timeBetweenEnemies, enemiesPerWave);
        player = new Player(grid, waveManager);
        gameMenuBackground = quickLoad("gameMenuBackground");
        setupUI();


    }

    private void setupUI(){
        gameUI = new UI();
        //gameUI.addButton("Soldier", "soldier", getWidth() - 165, 85);
        gameUI.createMenu("SoldierPicker", getWidth() - 180, 130);
        soldierMenu = gameUI.getMenu("SoldierPicker");
        soldierMenu.quickAddButton("Soldier1", "SoldierBackground");
        soldierMenu.quickAddButton("Soldier2", "SoldierBackground");
        soldierMenu.quickAddButton("Soldier3", "SoldierBackground");
        soldierMenu.quickAddButton("Soldier4", "SoldierBackground");
        soldierMenu.quickAddButton("Soldier5", "SoldierBackground");
    }


    private void updateUI(){

        gameUI.draw();

        if(Mouse.next()){

            mouseClicked = Mouse.isButtonDown(0);
            if(mouseClicked && soldierPick < 5 && !player.isHoldingSoldier()){

                if(soldierMenu.isButtonClicked("Soldier1")){
                    player.pickSoldier(player.getTowerWithId(1));
                }if(soldierMenu.isButtonClicked("Soldier2")){
                    player.pickSoldier(player.getTowerWithId(2));
                }if(soldierMenu.isButtonClicked("Soldier3")){
                    player.pickSoldier(player.getTowerWithId(3));
                }if(soldierMenu.isButtonClicked("Soldier4")){
                    player.pickSoldier(player.getTowerWithId(4));
                }if(soldierMenu.isButtonClicked("Soldier5")){
                    player.pickSoldier(player.getTowerWithId(5));
                }
            }
        }
    }

    public void update(){
        grid.draw();
        Artist.drawQuadTex(gameMenuBackground, 1280, 0, 260, 1680);
        updateUI();

        waveManager.update();
        player.update();

        int projectileNumber = 0;

        for (Tower t :
                player.getTowerList()) {
            projectileNumber += t.getProjectiles().size();
        }
        
        gameUI.drawString(80, 4,"Wave: " + waveManager.getWaveNumber() + "        fps: " + StateManager.getFps());
        if(player.getSelectedTower() != null)
            gameUI.drawString(80, 32, "Selected Soldier: " + player.getSelectedTower().getId());
        gameUI.drawString(80, HEIGHT - 60,"Number of enemies: " + waveManager.getCurrentWave().getEnemyAmount());
        gameUI.drawString(80, HEIGHT - 75, "Number of projectiles: " + projectileNumber);
        gameUI.drawString(80, HEIGHT - 35,"Enemies left to spawn: " + (waveManager.getCurrentWave().getEnemiesPerWave() - waveManager.getCurrentWave().getEnemiesSpawned())
                + "/" +  waveManager.getCurrentWave().getEnemiesPerWave());
    }


    static TileGrid getGrid() {
        return grid;
    }
}
