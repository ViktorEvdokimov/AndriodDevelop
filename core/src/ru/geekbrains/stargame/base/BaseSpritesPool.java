package ru.geekbrains.stargame.base;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSpritesPool<T extends Sprite> {

    private final List<T> activeObjects = new ArrayList<>();
    private final List<T> freeObjects = new ArrayList<>();

    protected abstract T newSprite ();

    public T obtain(){
        T sprite;
        if (freeObjects.isEmpty()) {
            sprite = newSprite();
        }
        else {
            sprite = freeObjects.remove(freeObjects.size()-1);
        }
        activeObjects.add(sprite);
        System.out.println(getClass().getName() +" active/free : " + activeObjects.size() + "/" + freeObjects.size());
        return sprite;
    }

    public void updateActiveSprites (int level, float delta){
        for (Sprite sprite : activeObjects){
            if (!sprite.destroyed) sprite.update(level, delta);
        }
    }

    public void drawActiveSprites (SpriteBatch spriteBatch){
        for (Sprite sprite : activeObjects){
            if (!sprite.destroyed) sprite.draw(spriteBatch);
        }
    }

    public void freeAllDestroyedActiveSprites(){
        for (int i=0; i<activeObjects.size(); i++){
            T sprite = activeObjects.get(i);
            if (sprite.destroyed) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    private void free (T sprite){
        if (activeObjects.remove(sprite)) {
            freeObjects.add(sprite);
            System.out.println(getClass().getName() +" active/free : " + activeObjects.size() + "/" + freeObjects.size());
        }
    }

    public void cleanAllActiveElements (){
        for (int i=0; i<activeObjects.size(); i++){
            T sprite = activeObjects.get(i);
                free(sprite);
                i--;
                sprite.flushDestroy();
        }
    }
}
