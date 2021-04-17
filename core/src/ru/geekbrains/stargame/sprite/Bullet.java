package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class Bullet extends Sprite {

        private Rect worldBounds;
        private Vector2 v;
        private int damage;
        private Sprite owner;
        private Vector2 tmp;

        public Bullet() {
            regions = new TextureRegion[1];
            v = new Vector2();
            tmp = new Vector2();
        }


    public void set(
                Sprite owner,
                TextureRegion region,
                Vector2 pos,
                Vector2 v,
                Rect worldBounds,
                int damage,
                float height
        ) {
            this.owner = owner;
            this.regions[0] = region;
            this.pos.set(pos);
            this.v.set(v);
            this.worldBounds = worldBounds;
            this.damage = damage;
            setHeightProportional(height);
        }

        @Override
        public void update(float worldSpeed, float delta) {
            tmp.set(v.x, v.y*worldSpeed);
            pos.mulAdd(tmp, delta);
            if (isOutside(worldBounds)) {
                destroy();
            }
        }

        public int getDamage() {
            return damage;
        }

        public Sprite getOwner() {
            return owner;
        }
    }