package data;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import helpers.DatabaseHandler;
import helpers.StateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import helpers.Clock;

import static helpers.Artist.*;

public class Player {

    private TileGrid grid;
    private TileType[] types;
    private WaveManager waveManager;
    private ArrayList<Tower> towerList, startList;
    private boolean leftMouseButtonDown, rightMouseButtonDown, holdingSoldier;
    private CopyOnWriteArrayList<Enemy> enemyList;
    private Tower tower1, tower3, tower4, tower5, tower2;
    private Tower selectedTower;
    private ArrayList<Character> characterList;

    int towerRange = 250;
    private int towerCount;

    public Player(TileGrid grid, WaveManager waveManager) {
        this.grid = grid;
        this.types = new TileType[3];
        this.types[0] = TileType.Floor;
        this.types[1] = TileType.Wall;
        this.types[2] = TileType.Pit;
        this.waveManager = waveManager;
        this.towerList = new ArrayList<>();
        this.startList = new ArrayList<>();
        this.characterList = new ArrayList<>();
        this.leftMouseButtonDown = false;
        this.holdingSoldier = false;

        enemyList = waveManager.getCurrentWave().getEnemyList();


        for (Character c :
                DatabaseHandler.getCharacters()) {
            System.out.printf("Character %d is: %s, speed: %d, range: %d, weaponID: %d\n", c.getId(), c.getName(), c.getSpeed(), c.getRange(), c.getWeaponID());
        }

        tower1 = new TowerSoldier(TowerType.SOLDIER, Weapon.machineGun, enemyList, 1);
        tower2 = new TowerSoldier(TowerType.SOLDIER, Weapon.rifle ,enemyList, 2);
        tower3 = new TowerSoldier(TowerType.SOLDIER, Weapon.shotgun, enemyList, 3);
        tower4 = new TowerSoldier(TowerType.SOLDIER, Weapon.rifle, enemyList, 4);
        tower5 = new TowerSoldier(TowerType.SOLDIER, Weapon.shotgun, enemyList, 5);
//		tower5 = new TowerCannonBlue(TowerType.TANK, enemyList, 5);

        startList.add(tower1);
        startList.add(tower2);
        startList.add(tower3);
        startList.add(tower4);
        startList.add(tower5);

    }

    public void update() {
        enemyList = waveManager.getCurrentWave().getEnemyList();

        if(holdingSoldier){
            TowerSoldier tempSoldier = new TowerSoldier(TowerType.SOLDIER, Weapon.rifle,enemyList,99);
            tempSoldier.setX(Mouse.getX() - TILE_SIZE/2);
            tempSoldier.setY(HEIGHT - Mouse.getY() - TILE_SIZE/2);
            tempSoldier.draw();
        }


        updateTowersAndProjectiles();

        // Handle Mouse Input
        Tower mouseOverTower = null;
        for (Tower t : towerList) {
            if (checkCollision(Mouse.getX(), HEIGHT - Mouse.getY(), 5, 5, t.getX(), t.getY(), t.getWidth(),
                    t.getHeight())) {
                mouseOverTower = t;
            }
        }

        MoveCheckPoint mouseOverCheckPoint = null;

        if (selectedTower != null) {
            for (MoveCheckPoint t : selectedTower.checkPointQueue) {
                if (checkCollision(Mouse.getX(), HEIGHT - Mouse.getY(), 5, 5, t.getX(), t.getY(), t.getWidth(),
                        t.getHeight())) {
                    mouseOverCheckPoint = t;
                }
            }
        }
        if (Mouse.isButtonDown(0) && !leftMouseButtonDown) {

            //if the spot is free, places a tower at mouse position
            if (mouseOverTower == null && towerList.size() < 5 && holdingSoldier) {
                if(grid.getTile(Mouse.getX(), HEIGHT - Mouse.getY()).getType().walkable){
                    placeSoldier(getSelectedTower());
                }
            } else {
                //else selects the tower at mouse position
                selectedTower = mouseOverTower;
                if(selectedTower != null){
                    TowerSoldier.setSelectedTowerID(selectedTower.getId());
                    //if no tower at position, selects tower -1
                }else TowerSoldier.setSelectedTowerID(-1);

            }
        }

        leftMouseButtonDown = Mouse.isButtonDown(0);

        if (Mouse.isButtonDown(1) && !rightMouseButtonDown) {
            if (mouseOverCheckPoint != null) {
                selectedTower.checkPointQueue.remove(mouseOverCheckPoint);
            } else {
                if (selectedTower != null) {
                    selectedTower.addCheckPoint(new MoveCheckPoint(Mouse.getX() - 15, (HEIGHT - Mouse.getY() - 16)));
                }
            }
        }
        rightMouseButtonDown = Mouse.isButtonDown(1);

        // ===================Drawing range indicators and target indicators when mouseover the selected soldier.

        if (selectedTower != null && mouseOverTower == selectedTower) {
            drawQuadTex(quickLoad("range2"), selectedTower.getCenterX() - (selectedTower.getRange()),
                    selectedTower.getCenterY() - selectedTower.getRange(), selectedTower.getRange() * 2,
                    selectedTower.getRange() * 2);
            if (selectedTower.getTarget() != null) {
                drawQuadTex(quickLoad("range2"), ( selectedTower.getTarget()).getCenterX() - 64 / 2,
                        selectedTower.getTarget().getCenterY() - 64 / 2, 64, 64);
            }
        }

        keyboardInputs();

    }

    private void placeSoldier(Tower soldier) {
        towerCount++;

        getTowerWithId(towerCount).setEnemies(enemyList);
        getTowerWithId(towerCount).setX(Mouse.getX() - getTowerWithId(towerCount).getWidth() / 2);
        getTowerWithId(towerCount)
                .setY((HEIGHT - Mouse.getY() - 1) - getTowerWithId(towerCount).getHeight() / 2);
        towerList.add(soldier);
        holdingSoldier = false;
        soldier.setPlaced(true);
        Game.soldierPick++;
    }

    public void pickSoldier(Tower soldier){

        if(soldier.isPlaced()){
            selectedTower = soldier;
            TowerSoldier.setSelectedTowerID(selectedTower.getId());
        } else {

            selectedTower = soldier;
            TowerSoldier.setSelectedTowerID(selectedTower.getId());
            holdingSoldier = true;
        }


    }

    public Tower getSelectedTower() {
        return selectedTower;
    }

    public CopyOnWriteArrayList<Enemy> getEnemyList() {
        return enemyList;
    }

    private TowerSoldier addTower(TowerSoldier t, float x, float y) {
        return t;
    }

    public Tower getTowerWithId(int id) {

        for (Tower tc : startList) {
            if (tc.getId() == id)
                return tc;
        }
        return null;
    }

    public void updateTowersAndProjectiles(){

        for (Tower t : towerList) {
            t.update();
            t.updateEnemyList(waveManager.getCurrentWave().getEnemyList());
            t.draw();
            t.getProjectiles().forEach(Projectile::update);

            if (t.hasCheckPoint()) {
                t.getMoveCheckPoints().forEach(MoveCheckPoint::update);
            }
        }
    }

    public boolean isHoldingSoldier() {

        return holdingSoldier;
    }



    public void keyboardInputs(){

        // Testing a design pattern Strategy Pattern
        if (selectedTower != null) {
            if (Keyboard.getEventKey() == Keyboard.KEY_LSHIFT && Keyboard.getEventKeyState()) {
                selectedTower.setMoveMode(new RunningMode());
            }else
                selectedTower.setMoveMode(new WalkingMode());
        }

        // Handle Keyboard Input
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_UP && Keyboard.getEventKeyState()) {
                Clock.changeMultiplier(0.2f);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_DOWN && Keyboard.getEventKeyState()) {
                Clock.changeMultiplier(-0.2f);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
                StateManager.setGameState(StateManager.GameState.MAINMENU);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_1 && Keyboard.getEventKeyState()) {
                pickSoldier(getTowerWithId(1));
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_2 && Keyboard.getEventKeyState()) {
                pickSoldier(getTowerWithId(2));
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_3 && Keyboard.getEventKeyState()) {
                pickSoldier(getTowerWithId(3));
//				TowerSoldier.setSelectedTowerID(3);
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_4 && Keyboard.getEventKeyState()) {
                pickSoldier(getTowerWithId(4));
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_5 && Keyboard.getEventKeyState()) {
                pickSoldier(getTowerWithId(5));
            }

            if (Keyboard.getEventKey() == Keyboard.KEY_TAB && Keyboard.getEventKeyState()) {
                //hard coded for now, will clean up later
                if(TowerSoldier.getSelectedTowerID() == -1 || TowerSoldier.getSelectedTowerID() == 5){
                    pickSoldier(getTowerWithId(1));
                } else {

                    pickSoldier(getTowerWithId(selectedTower.getId()+1%5));
                }
            }

            if(Keyboard.getEventKey() == Keyboard.KEY_SPACE && Keyboard.getEventKeyState()){
                Clock.pause();
            }
        }
    }
}
