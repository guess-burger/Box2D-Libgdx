package com.obsolete.libgdx.box2d.tutorial05.constant_speed

import com.obsolete.libgdx.box2d.Tutorial
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.{StaticBody,DynamicBody}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Input.Keys

/**
 * Based on http://www.iforce2d.net/b2dtut/constant-speed
 */
class _00_VelocityDirectlyInstant extends Tutorial {

  override val name: String = "Box2d - Constant Speed 00 (Instant Velocity)"

  //class member variables
  var body: Body = _
  var moveState = MoveState.Stop

  override def createWorld = {
    val world = new World(new Vector2(0, -10f), true)

    //body definition
    val myBodyDef = new BodyDef()
    myBodyDef.`type` = DynamicBody

    //shape definition
    val polygonShape = new PolygonShape()
    polygonShape.setAsBox(1, 1) //a 2x2 rectangle

    //fixture definition
    val myFixtureDef = new FixtureDef()
    myFixtureDef.shape = polygonShape
    myFixtureDef.density = 1

    //create dynamic body
    myBodyDef.position.set(20, 10)
    body = world.createBody(myBodyDef)
    body.createFixture(myFixtureDef)

    //a static body
    myBodyDef.`type` = StaticBody
    myBodyDef.position.set(0, 0)
    val staticBody = world.createBody(myBodyDef)

    //add four walls to the static body
    polygonShape.setAsBox( 20, 1, new Vector2(20, 0), 0)//ground
    staticBody.createFixture(myFixtureDef)
    polygonShape.setAsBox( 20, 1, new Vector2(20, 20), 0)//ceiling
    staticBody.createFixture(myFixtureDef)
    polygonShape.setAsBox( 1, 20, new Vector2(0, 10), 0)//left wall
    staticBody.createFixture(myFixtureDef)
    polygonShape.setAsBox( 1, 20, new Vector2(40, 10), 0)//right wall
    staticBody.createFixture(myFixtureDef)

    moveState = MoveState.Stop

    world
  }

  override def step() {
    val vel = body.getLinearVelocity
    val xVel = moveState match{
      case MoveState.Left => -5f
      case MoveState.Stop => 0f
      case MoveState.Right => 5f
    }
    body.setLinearVelocity(xVel,vel.y)

    super.step()
  }

  override def keyDown(key:Int) = {
    key match {
      case Keys.Q => moveState = MoveState.Left; true
      case Keys.W => moveState = MoveState.Stop; true
      case Keys.E => moveState = MoveState.Right; true
      case _ => super.keyDown(key)
    }
  }

}

object _00_VelocityDirectlyInstant {
  def main(args: Array[String]) {
    Tutorial.demo(new _00_VelocityDirectlyInstant)
  }
}
