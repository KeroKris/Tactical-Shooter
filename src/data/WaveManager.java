package data;

public class WaveManager {
    private float timeSinceLastWave, timeBetweenEnemies;
    private int waveNumber, enemiesPerWave;
    private Enemy enemyType;
    private Wave currentWave;



    public WaveManager(Enemy enemyType, float timeBetweenEnemies, int enemiesPerWave){
        this.enemyType = enemyType;
        this.enemiesPerWave = enemiesPerWave;
        this.timeBetweenEnemies = timeBetweenEnemies;
        this.timeSinceLastWave = 0;
        this.waveNumber = 0;

        this.currentWave = null;

        newWave();
    }

    public void update(){
        if(!currentWave.isWaveCompleted()){
            currentWave.update();
        } else {
            newWave();
        }
    }

    public void newWave(){
        currentWave = new Wave(enemyType, timeBetweenEnemies, enemiesPerWave);
        waveNumber++;
        System.out.println("New wave added: " + waveNumber);
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public Wave getCurrentWave() {
        return currentWave;
    }
}
