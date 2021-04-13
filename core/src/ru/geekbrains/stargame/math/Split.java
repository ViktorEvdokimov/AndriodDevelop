package ru.geekbrains.stargame.math;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Split {
    public static TextureRegion[] split
            (TextureRegion textureRegion, int rows, int cols, int quantity) {

        int x = textureRegion.getRegionX();
        int y = textureRegion.getRegionY();
        int width = textureRegion.getRegionWidth();
        int height = textureRegion.getRegionHeight();
        int tileWidth = width / cols;
        int tileHeight = height / rows;

//        int rows = height / tileHeight;
//        int cols = width / tileWidth;

        int startX = x;
        TextureRegion[] tiles = new TextureRegion[quantity];
        for (int row = 0; row < rows; row++, y += tileHeight) {
            x = startX;
            for (int col = 0; col < cols; col++, x += tileWidth) {
                int count = row * col + col;
                if ((count)<quantity) {
                    tiles[count] = new TextureRegion(textureRegion.getTexture(), x, y, tileWidth, tileHeight);
                }
            }
        }
        return tiles;
    }
}
