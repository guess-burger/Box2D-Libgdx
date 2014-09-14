package com.obsolete.libgdx.box2d.tutorial01.fixtures

import com.obsolete.libgdx.box2d.{Tutorial, TutorialBase, TutorialPart}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.obsolete.libgdx.box2d.TutorialBase.DEG_TO_RAD
import com.badlogic.gdx.physics.box2d

/**
 * Based on http://www.iforce2d.net/b2dtut/fixtures
 */
class _04_Friction extends Tutorial{

  override val name = "Box2d - Fixtures 04"

  override def createWorld = {
    val world = new World(new Vector2(0, -10f), true)

    //set up a dynamic body
    val myBodyDef = new BodyDef()
    myBodyDef.`type` = BodyType.DynamicBody
    myBodyDef.position.set(20, 10) //middle
    val dynamicBody = world.createBody(myBodyDef)

    //prepare a shape definition
    val polygonShape = new PolygonShape()
    val myFixtureDef = new FixtureDef()
    myFixtureDef.shape = polygonShape
    myFixtureDef.density = 1

    myFixtureDef.friction = 0 //new code
    myFixtureDef.restitution = 1 //new code
    //restitution isn't just 0 < x < 1! 2 is a valid value

    //add four square shaped fixtures around the body center
    for ( i <- 0 until 4) {
      val pos = new Vector2( Math.sin(i*90*DEG_TO_RAD).toFloat, Math.cos(i*90*DEG_TO_RAD).toFloat ) //radial placement
      polygonShape.setAsBox(1, 1, pos, 0 ) //a 2x2 rectangle
      dynamicBody.createFixture(myFixtureDef) //add a fixture to the body
    }

    //make a static floor to drop things on
    myBodyDef.`type` = BodyType.StaticBody
    myBodyDef.position.set(0, 0); //middle, bottom
    val staticBody = world.createBody(myBodyDef)
    val edgeShape = new box2d.EdgeShape()
    edgeShape.set( new Vector2(0,0), new Vector2(40,3))//slightly sloped
    myFixtureDef.shape = edgeShape
    staticBody.createFixture(myFixtureDef); //add a fixture to the body

    world
  }

}

object _04_Friction {
  def main(args: Array[String]) {
    Tutorial.demo(new _04_Friction)
  }
}
