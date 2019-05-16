package com.sundaysunday.theredtie.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sundaysunday.theredtie.Assets;

public class Floor3at0 extends Entity
{
    private static TextureRegion tile = new TextureRegion(Assets.floor2at1);
    public Floor3at0(int x, int y, int width, int height)
    {
        super(new Vector2(x,y),new Rectangle(0,0,width,height),"solid");
        hitboxColor = Color.GREEN;
    }

    @Override
    public void draw(Batch batch)
    {
        batch.draw(tile, position.x, position.y,0, 0,52,15,1,1,-10);
    }
}
