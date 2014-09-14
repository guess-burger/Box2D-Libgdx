package com.obsolete.libgdx.box2d.tutorial05.constant_speed

import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.{StaticBody,DynamicBody}
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.Input.Keys
import com.obsolete.libgdx.box2d.Tutorial

/**
  * Based on http://www.iforce2d.net/b2dtut/constant-speed
  */
class _05_ImpulseGradual extends Tutorial{

   override val name: String = "Box2d - Constant Speed 04 (Instant Impulse)"

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
     val desiredVel = moveState match {
       case MoveState.Left => Math.max(vel.x - 0.1f, -5f)
       case MoveState.Stop if Math.abs(vel.x) < 0.1 => 0
       case MoveState.Stop => vel.x * 0.98f
       case MoveState.Right => Math.min(vel.x + 0.1f,5)
     }
     val velChange = desiredVel - vel.x
     val impulse = body.getMass * velChange //disregard time factor
     body.applyLinearImpulse( new Vector2(impulse,0), body.getWorldCenter,true)

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

object _05_ImpulseGradual {
   def main(args: Array[String]) {
     Tutorial.demo(new _05_ImpulseGradual)
   }
 }
