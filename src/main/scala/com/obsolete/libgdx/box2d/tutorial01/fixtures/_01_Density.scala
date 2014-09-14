package com.obsolete.libgdx.box2d.tutorial01.fixtures

import com.obsolete.libgdx.box2d.{Tutorial, TutorialBase, TutorialPart}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType
import com.badlogic.gdx.physics.box2d

/**
  * Based on http://www.iforce2d.net/b2dtut/fixtures
  *
  * Same code as before except this time we've added density to our fixture.
  * Compare how this example behaves compared to the previous.
  *
  * N.B. All comments have been removed except for the one highlighting the change
  *
  */
class _01_Density extends Tutorial{

   override val name = "Box2D - Fixtures 01"

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
     myFixtureDef.density = 1 // <- MAGIC!!!
     myFixtureDef.shape = circleShape
     dynamicBody1.createFixture(myFixtureDef)

     val vertices = Array(new Vector2(-1,2),new Vector2(-1,0),
          new Vector2(0,-3),new Vector2(1,0),new Vector2(1,1))
     val polygonShape = new PolygonShape()
     polygonShape.set(vertices)

     myFixtureDef.shape = polygonShape
     myBodyDef.position.set(20, 10)
     val dynamicBody2 = world.createBody(myBodyDef)
     dynamicBody2.createFixture(myFixtureDef)

     polygonShape.setAsBox(2, 1)
     myBodyDef.position.set(30,10)

     val dynamicBody3 = world.createBody(myBodyDef)
     dynamicBody3.createFixture(myFixtureDef)

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

object _01_Density {
  def main(args: Array[String]) {
    Tutorial.demo(new _01_Density)
  }
}
