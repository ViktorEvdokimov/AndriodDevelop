package ru.geekbrains.stargame.pool;

import ru.geekbrains.stargame.base.BaseSpritesPool;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.sprite.Bullet;

public class BulletPool  extends BaseSpritesPool<Bullet> {

    private boolean isHit;

    @Override
    protected Bullet newSprite() {
        return new Bullet();
    }

    public boolean isHit (Sprite sprite){
        isHit=false;
        for (Bullet bullet : getActiveObjects()) {
            if(!bullet.isOutside(sprite)) {
                bullet.destroy();
                isHit = true;
            }
        }
        return isHit;
    }
}


