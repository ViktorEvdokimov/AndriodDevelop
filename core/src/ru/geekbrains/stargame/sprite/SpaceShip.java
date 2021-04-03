package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;

public class SpaceShip extends Sprite {


    Vector2 purpose = new Vector2();
    Vector2 direction = new Vector2();
    Vector2 temp = new Vector2();

    public SpaceShip(Texture texture) {
        super(new TextureRegion(texture));
        setBottom(0f);
        setRight(0f);
        setHeightProportional(0.2f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        purpose.set(touch);
        direction.set(touch);
        direction.sub(pos);
        direction.setLength(0.003f);
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        temp.set(purpose);
        if(temp.sub(pos).len()<=direction.len()) pos.set(purpose);
        else pos.add(direction);
        super.draw(batch);
    }
}
