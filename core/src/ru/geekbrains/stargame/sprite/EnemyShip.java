package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.units.EnemyEmitter;

public class EnemyShip extends SpaceShip{

    private Vector2 startV;
    private  BulletPool bulletPool;
    private  Rect worldBounds;
    private Sound sound;
    private  SpaceShip playerShip;
    private  Vector2 v;
    private float defaultSpeed;
    private  Vector2 tmp;
    private  TextureRegion bulletRegion;
    private Vector2 bulletV;
    private float bulletHeight;
    private int damage;
    private int hp;
    private  EnemyEmitter enemyEmitter;


    public EnemyShip(BulletPool bulletPool, Rect worldBounds,
                     Sound sound, SpaceShip playerShip, EnemyEmitter enemyEmitter) {
        this.bulletPool=bulletPool;
        this.worldBounds=worldBounds;
        this.sound=sound;
        this.playerShip = playerShip;
        this.sound = sound;
        this.enemyEmitter = enemyEmitter;
        v = new Vector2();
        tmp = new Vector2();
        startV = new Vector2(0, -0.007f);
    }

    @Override
    public void update(float worldSpeed, float delta) {
        if (getTop()>worldBounds.getTop()){
            v.set(startV);
        } else {
            reloadTimer += delta;
            if (reloadTimer > 0) {
                reloadTimer = -reloadInterval;
                shoot();
            }
            if (pos.x - playerShip.getPos().x > defaultSpeed) {
                v.set(-defaultSpeed, 0);
            } else if (pos.x - playerShip.getPos().x < -defaultSpeed) {
                v.set(defaultSpeed, 0);
            } else v.set(0, 0);
            v.add(0, -defaultSpeed - worldSpeed);
        }
        pos.add(v);
        if (getTop() < worldBounds.getBottom()) {
            destroy();
            enemyEmitter.shipOut();
        }
    }

    public void set (
            TextureRegion[] regions,
            float defaultSpeed,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletSpeed,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.defaultSpeed = defaultSpeed;
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV = bulletSpeed;
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportional(height);
        this.hp = hp;
    }

    @Override
    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        tmp.set(pos.x, getBottom());
        sound.play(0.1f);
        bullet.set(this, bulletRegion, this.tmp, bulletV, worldBounds, damage, bulletHeight);
    }
}
