package com.sundaysunday.theredtie.Entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.sundaysunday.theredtie.Worlds.World;

public class Entity
{
    protected Polygon polygonHitbox;
    protected Circle circleHitbox;

    protected Vector2 position;
    protected float velocityX = 0, velocityY = 0;

    protected World world;

    private String m_nameTag;

    public int[] playerKeys = new int[4];

    public Entity(Vector2 position, int x, int y, int width, int height, String m_nameTag, float rotation)
    {
        this.position = position;
        this.m_nameTag = m_nameTag;

        polygonHitbox = new Polygon(new float[] {
                x, y,
                x, y + height,
                x + width, y + height,
                x + width, y
        });
        polygonHitbox.setOrigin(width/2, height/2);
        polygonHitbox.rotate(rotation);
    }

    public Entity(Vector2 position, Circle circle, String m_nameTag)
    {
        this.position = position;
        this.circleHitbox = circle;
        this.m_nameTag = m_nameTag;
    }

    public void Update(float deltaTime)
    {
        position.x += velocityX * deltaTime;
        position.y += velocityY * deltaTime;
    }

    public void DrawHitbox(ShapeRenderer renderer)
    {
        renderer.end();

        renderer.setColor(Color.GREEN);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        if(m_nameTag.equals("player") || m_nameTag.equals("demolitionBall") || m_nameTag.equals("villain"))
        {
            renderer.circle(position.x, position.y, circleHitbox.radius);
        }
        else
        {
            renderer.polygon(polygonHitbox.getTransformedVertices());
        }

        renderer.end();
    }

    public boolean CollideBetweenPOLYGONandCIRCLE(Entity m_polygon, Entity m_circle)
    {
        Polygon polygon = m_polygon.getPolygonHitbox();
        Circle circle = m_circle.getCircleHitbox();

        float[] vertices = polygon.getTransformedVertices();
        Vector2 center = new Vector2(m_circle.getPositionX(), m_circle.getPositionY());

        float squareRadius = circle.radius * circle.radius;

        for (int i = 0; i < vertices.length; i += 2)
        {
            if (i == 0)
            {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                {
                    return true;
                }
            }
            else
            {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean CollideBetweenPOLYGONandCIRCLE(Entity m_polygon, Entity m_circle, float velocityY, float deltaTime) {

        Polygon polygon = m_polygon.getPolygonHitbox();
        Circle circle = m_circle.getCircleHitbox();

        float[] vertices = polygon.getTransformedVertices();
        Vector2 center = new Vector2(m_circle.getPositionX(), (m_circle.getPositionY()+(velocityY * deltaTime)));

        float squareRadius = circle.radius * circle.radius;

        for (int i = 0; i < vertices.length; i += 2)
        {
            if (i == 0)
            {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[vertices.length-2], vertices[vertices.length-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                {
                    return true;
                }
            }
            else
            {
                if (Intersector.intersectSegmentCircle(new Vector2(vertices[i-2], vertices[i-1]), new Vector2(vertices[i], vertices[i+1]), center, squareRadius))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean CollideBetweenCIRCLEandCIRCLE(Entity firstCircle, Entity secondCircle) {

        Circle circleOne = firstCircle.getCircleHitbox();
        Circle circleTwo = secondCircle.getCircleHitbox();

        if(circleOne.overlaps(circleTwo))
        {
            return true;
        }

        return false;
    }

    public Entity CollideWithNameTagAndEntity(String nameTag, Entity m_circle)
    {
        for(Entity entity : world.getEntitiesByTag(nameTag))
        {
            if(CollideBetweenPOLYGONandCIRCLE(entity, m_circle))
            {
                return entity;
            }
        }

        return null;
    }

    public Entity CollideWithDemolitionBallTagAndPlayerCircle(String nameTag, Player m_circle)
    {
        for(Entity entity : world.getEntitiesByTag(nameTag))
        {
            if(CollideBetweenCIRCLEandCIRCLE(entity, m_circle))
            {
                return entity;
            }
        }

        return null;
    }

    public void UpdatePlayerInputs(int W, int A, int S, int D)
    {
        playerKeys[0] = W;
        playerKeys[1] = A;
        playerKeys[2] = S;
        playerKeys[3] = D;
    }

    public void         Draw(Batch batch){}

    public void         KeyUp(int keycode){}
    public void         KeyDown(int keycode){}

    public String       getNameTag(){return m_nameTag;}

    public Vector2      getPosition(){return position;}
    public void         setPosition(Vector2 position){this.position = position;}

    public float		getPositionX(){return position.x;}
    public void         setVelocityX(float velocityX){this.velocityX = velocityX;}

    public float		getPositionY(){return position.y;}
    public void         setVelocityY(float velocityY){this.velocityY = velocityY;}

    public Circle       getCircleHitbox(){return circleHitbox;};

    public Polygon      getPolygonHitbox() {return new Polygon(polygonHitbox.getTransformedVertices());}

    public float        getVelocityX(){return velocityX;}
    public float        getVelocityY(){return velocityY;}

    public void			setWorld(World world){ this.world = world; }

    public void			setPositionX(float x){ position.x = x;}
    public void			setPositionY(float y){ position.y = y;}
}
