package data;

import static helpers.Clock.delta;

import java.util.concurrent.CopyOnWriteArrayList;

public class Wave {
    private float timeSinceLastSpawn, spawnTime;
    private Enemy enemyType;
    private int enemyWidth, enemyHeight;
    private CopyOnWriteArrayList<Enemy> enemyList;
    private int enemiesPerWave;
    private boolean waveCompleted;
    private int enemiesSpawned;

    public Wave(Enemy enemyType, float spawnTime, int enemiesPerWave) {
        this.enemyType = enemyType;
        this.enemyWidth = enemyType.getWidth();
        this.enemyHeight = enemyType.getHeight();
        this.spawnTime = spawnTime;
        this.enemiesPerWave = enemiesPerWave;
        this.timeSinceLastSpawn = 0;
        this.enemyList = new CopyOnWriteArrayList<>();
        this.waveCompleted = false;

        spawn();

    }

    public void update() {
        boolean allEnemiesDead = false;
        if(enemiesSpawned == enemiesPerWave) {
            allEnemiesDead = true;
        }
        timeSinceLastSpawn += delta();
        if (enemiesSpawned < enemiesPerWave) {

            if (timeSinceLastSpawn > spawnTime) {
                spawn();
                timeSinceLastSpawn = 0;
            }
        }

        for (Enemy e : enemyList) {
            if (e.isAlive()) {
                allEnemiesDead = false;


                e.update();
                e.draw();
            }
            else {

                enemyList.remove(e);
                getEnemyAmount();
            }
        }
        if(allEnemiesDead){
            waveCompleted = true;
        }
    }

    private void spawn() {
        enemyList.add(new Enemy(enemyType.getTexture(), enemyType.getStartTile(), enemyWidth, enemyHeight, enemyType.getSpeed(),
                enemyType.getTileGrid(), enemyType.getHealth()));
        enemiesSpawned++;
        getEnemyAmount();
//		enemiesPerWave--;
    }

    public int getEnemyAmount(){

        return enemyList.size();
    }

    public int getEnemiesPerWave() {
        return enemiesPerWave;
    }

    public int getEnemiesSpawned() {
        return enemiesSpawned;
    }

    public boolean isWaveCompleted() {
        return waveCompleted;
    }

    public CopyOnWriteArrayList<Enemy> getEnemyList() {
        return enemyList;
    }
}
