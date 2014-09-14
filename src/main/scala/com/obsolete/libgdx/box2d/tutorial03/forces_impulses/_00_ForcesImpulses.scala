package com.obsolete.libgdx.box2d.tutorial03.forces_impulses

import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType.{StaticBody,DynamicBody}
import com.obsolete.libgdx.box2d.Tutorial
import com.badlogic.gdx.Input.Keys

/**
 * Based on http://www.iforce2d.net/b2dtut/forces
 */
//TODO need  a positional shift after changes
class _00_ForcesImpulses extends Tutorial{

  var bodies = new Array[Body](3)

  override val name: String = "Box2d - Forces & Impulses 00"

  override def createWorld: World = {
    val world = new World(new Vector2(0, -10f), true)

    //body definition
    val myBodyDef = new BodyDef()
    myBodyDef.`type` = DynamicBody

    //shape definition
    val polygonShape = new PolygonShape()
    polygonShape.setAsBox(1, 1); //a 2x2 rectangle

    //fixture definition
    val myFixtureDef = new FixtureDef()
    myFixtureDef.shape = polygonShape
    myFixtureDef.density = 1

    //create identical bodies in different positions
    for (i <- 0 until 3) {
      myBodyDef.position.set(0+i*10, 10)
      val body = world.createBody(myBodyDef)
      body.createFixture(myFixtureDef)
      bodies(i) = body
    }

    //a static floor to drop things on
    myBodyDef.`type` = StaticBody
    myBodyDef.position.set(-10, 0)
    val edgeShape = new EdgeShape()
    edgeShape.set( new Vector2(0,0), new Vector2(40,0))
    myFixtureDef.shape = edgeShape
    world.createBody(myBodyDef).createFixture(myFixtureDef)

    world
  }

  override def keyDown(key:Int) = {
    key match {
        case Keys.Q => bodies(0).applyForce(new Vector2(0,50), bodies(0).getWorldCenter,true);true
        case Keys.W => bodies(1).applyLinearImpulse(new Vector2(0,50), bodies(1).getWorldPoint(new Vector2(1,1)),true);true
        case Keys.E => bodies(2).setTransform(new Vector2(30,10), 0);true
        case Keys.S => bodies(1).applyAngularImpulse( 20f,true );true
        case _ => super.keyDown(key)
    }
  }
}
object _00_ForcesImpulses {
  def main(args: Array[String]) {
    Tutorial.demo(new _00_ForcesImpulses)
  }
}
