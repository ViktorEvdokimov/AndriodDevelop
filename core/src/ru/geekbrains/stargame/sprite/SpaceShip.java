package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class SpaceShip extends Sprite {


    private Vector2 purpose = new Vector2();
    private Vector2 direction = new Vector2();
    private Vector2 temp = new Vector2();
    private float speed;
    private TextureRegion texture;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private Rect worldBounds;

    public SpaceShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        setBottom(0f);
        setRight(0f);
        setHeightProportional(0.2f);
        regions[frame].setRegionWidth(regions[frame].getRegionWidth()/2);
        setWidth(getWidth()*0.5f);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {

        purpose.set(touch);

        return false;
    }

    @Override
    public void update(float speed) {
        if (upPressed) purpose.add(0, 0.003f+speed);
        if (downPressed) purpose.add(0, -(0.003f+speed));
        if (rightPressed) purpose.add(0.003f+speed, 0);
        if (leftPressed) purpose.add(-(0.003f+speed), 0);
        temp.set(purpose);
        direction.set(purpose);
        direction.sub(pos);
        direction.setLength(0.003f+speed);
        if(temp.sub(pos).len()<=direction.len()) {
            pos.set(purpose);
        }
        else pos.add(direction);
        if (getTop() > worldBounds.getTop())
            purpose.set(purpose.x, worldBounds.getTop()-getHalfHeight());
        if (getBottom() < worldBounds.getBottom())
            purpose.set(purpose.x, worldBounds.getBottom()+getHalfHeight());
        if (getLeft() < worldBounds.getLeft())
            purpose.set(worldBounds.getLeft()+getHalfWidth(), purpose.y);
        if (getRight() > worldBounds.getRight())
            purpose.set(worldBounds.getRight()-getHalfWidth(), purpose.y);

    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    @Override
    public void draw(SpriteBatch batch) {

        super.draw(batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if (keycode==19 || keycode==51) upPressed=true;
        if (keycode==20 || keycode==47) downPressed=true;
        if (keycode==21 || keycode==29) leftPressed=true;
        if (keycode==22 || keycode==32) rightPressed=true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
            if (keycode==19 || keycode==51) upPressed=false;
            if (keycode==20 || keycode==47) downPressed=false;
            if (keycode==21 || keycode==29) leftPressed=false;
            if (keycode==22 || keycode==32) rightPressed=false;
        return false;
    }
}
