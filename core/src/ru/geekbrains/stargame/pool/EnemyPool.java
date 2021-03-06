package ru.geekbrains.stargame.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.stargame.base.BaseSpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.PlayerShip;
import ru.geekbrains.stargame.sprite.SpaceShip;
import ru.geekbrains.stargame.units.EnemyEmitter;

public class EnemyPool extends BaseSpritesPool<EnemyShip> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;
    private final Sound sound;
    private final SpaceShip playerShip;
    private EnemyEmitter enemyEmitter;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds, Sound sound,
                     SpaceShip playerShip) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.sound = sound;
        this.playerShip = playerShip;
    }

    public void setEnemyEmitter(EnemyEmitter enemyEmitter) {
        this.enemyEmitter = enemyEmitter;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(bulletPool, worldBounds, sound, playerShip, enemyEmitter);
    }
}