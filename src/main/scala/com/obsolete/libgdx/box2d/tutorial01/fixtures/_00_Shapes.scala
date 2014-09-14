package com.obsolete.libgdx.box2d.tutorial01.fixtures

import com.obsolete.libgdx.box2d.{Tutorial, TutorialBase, TutorialPart}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d

/**
 * Based on http://www.iforce2d.net/b2dtut/fixtures
 *
 * Fixtures describe the shape, size and material properties objects in the scene
 *
 */
class _00_Shapes extends Tutorial{

  override val name = "Box2D - Fixtures 00"

  override def createWorld = {
    val world = new World(new Vector2(0, -10f), true)

    val myBodyDef = new BodyDef()
    myBodyDef.`type` = BodyType.DynamicBody //this will be a dynamic body
    myBodyDef.position.set(10, 10) //a little to the left

    val dynamicBody1 = world.createBody(myBodyDef)

    //circle
    val circleShape = new CircleShape()
    circleShape.setPosition(new Vector2(0,0)) //position, relative to body position
    circleShape.setRadius(1) //radius

    val myFixtureDef = new FixtureDef()
    myFixtureDef.shape = circleShape //this is a pointer to the shape above
    dynamicBody1.createFixture(myFixtureDef) //add a fixture to the body

    //polygon
    //set each vertex of polygon in an array
    val vertices = Array(new Vector2(-1,2),new Vector2(-1,0),new Vector2(0,-3),
        new Vector2(1,0),new Vector2(1,1))
    val polygonShape = new PolygonShape()
    polygonShape.set(vertices) //pass array to the shape

    myFixtureDef.shape = polygonShape //change the shape of the fixture
    myBodyDef.position.set(20, 10) //in the middle
    val dynamicBody2 = world.createBody(myBodyDef)
    dynamicBody2.createFixture(myFixtureDef) //add a fixture to the body
    /*
     * N.B. The max number of vertices for a polygon is 8. This is a hard limit in libGDX.
     * Also, vertices need to be specified in an anticlockwise order. Oh, and you can't
     * make the shape concave.
     *
     * To get around concave or >8 vertices you'd need to combine multiple fixtures!
     */

    //box
    polygonShape.setAsBox(2, 1) //a 4x2 rectangle
    myBodyDef.position.set(30,10) //a bit to the right

    val dynamicBody3 = world.createBody(myBodyDef)
    dynamicBody3.createFixture(myFixtureDef) //add a fixture to the body

    //ground
    myBodyDef.`type` = BodyType.StaticBody //change body type
    myBodyDef.position.set(0,0) //middle, bottom

    val edgeShape = new box2d.EdgeShape()
    edgeShape.set( new Vector2(0,0), new Vector2(40,0)) //ends of the line
    myFixtureDef.shape = edgeShape
    val staticBody = world.createBody(myBodyDef)
    staticBody.createFixture(myFixtureDef) //add a fixture to the body

    world
  }

}

object _00_Shapes {
  def main(args: Array[String]) {
    Tutorial.demo(new _00_Shapes)
  }
}
