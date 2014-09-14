package com.obsolete.libgdx.box2d.tutorial01.fixtures

import com.obsolete.libgdx.box2d.{Tutorial, TutorialBase, TutorialPart}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d

/**
 * Based on http://www.iforce2d.net/b2dtut/fixtures
 */
class _03_MultipleFixturesB extends Tutorial{

  override val name = "Box2D - Fixtures 03"

  override def createWorld = {
    val world = new World(new Vector2(0, -10f), true)

    val myBodyDef = new BodyDef()
    myBodyDef.`type` = BodyType.DynamicBody
    myBodyDef.position.set(10, 10)

    val dynamicBody1 = world.createBody(myBodyDef)

    val circleShape = new CircleShape()
    circleShape.setPosition(new Vector2(0,0))
    circleShape.setRadius(1)

    val myFixtureDef = new FixtureDef()
    myFixtureDef.density = 1
    myFixtureDef.shape = circleShape
    dynamicBody1.createFixture(myFixtureDef)

    //for the custom polygon, add 10 to each x-coord
    val vertices = Array(new Vector2(-1+10,2),new Vector2(-1+10,0),
      new Vector2(0+10,-3),new Vector2(1+10,0),new Vector2(1+10,1))
    val polygonShape = new PolygonShape()
    polygonShape.set(vertices)

    myFixtureDef.shape = polygonShape

    dynamicBody1.createFixture(myFixtureDef)

    //for the box, use an extended version of the SetAsBox function which allows
    //us to set a location and angle (location is offset from body position)
    polygonShape.setAsBox(2, 1, new Vector2(20,0),0)//moved 20 units right, same angle

    dynamicBody1.createFixture(myFixtureDef)

    myBodyDef.`type` = BodyType.StaticBody
    myBodyDef.position.set(0,0)

    val edgeShape = new box2d.EdgeShape()
    edgeShape.set( new Vector2(0,0), new Vector2(40,0))
    myFixtureDef.shape = edgeShape
    val staticBody = world.createBody(myBodyDef)
    staticBody.createFixture(myFixtureDef)

    world
  }

}

object _03_MultipleFixturesB {
  def main(args: Array[String]) {
    Tutorial.demo(new _03_MultipleFixturesB)
  }
}
