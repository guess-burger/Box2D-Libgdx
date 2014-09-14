package com.obsolete.libgdx.box2d.tutorial00.bodies

import com.obsolete.libgdx.box2d.{Tutorial, TutorialBase, TutorialPart}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

/**
 * Based on http://www.iforce2d.net/b2dtut/bodies
 *
 * Bodies are fundamental to creating a physics scene. However, they are not the
 * items you see colliding into each other but instead hold the properties of
 * the item such as position, mass, velocity, angle etc.
 */
class _00_Bodies extends Tutorial {
    import TutorialBase.DEG_TO_RAD

  override val name = "Box2D - Bodies 00"

  override def createWorld = {
    val world = new World(new Vector2(0, -10f), true)

    //Creating a body
    val myBodyDef = new BodyDef()
    myBodyDef.`type` = BodyType.DynamicBody //this will be a dynamic body
    myBodyDef.position.set(0, 0) //set the starting position
    myBodyDef.angle = 0 //set the starting angle

    val dynamicBody = world.createBody(myBodyDef)
    val boxFixtureDef = _00_Bodies.createSimpleFixture
    dynamicBody.createFixture(boxFixtureDef)

    //Setting body properties
    dynamicBody.setTransform(new Vector2(24, 10), 45 * TutorialBase.DEG_TO_RAD)
    dynamicBody.setLinearVelocity( new Vector2( -5, 5 )) //moving up and left 5 units per second
    dynamicBody.setAngularVelocity( -90 * DEG_TO_RAD ) //90 degrees per second clockwise

    //Static bodies
    myBodyDef.`type` = BodyType.StaticBody //this will be a static body
    myBodyDef.position.set(16, 5) //slightly lower position
    val staticBody = world.createBody(myBodyDef) //add body to world
    staticBody.createFixture(boxFixtureDef) //add fixture to body

    //Kinematic bodies
    myBodyDef.`type` = BodyType.KinematicBody //this will be a kinematic body
    myBodyDef.position.set(12, 6) // start from left side, slightly above the static body
    val kinematicBody = world.createBody(myBodyDef) //add body to world
    kinematicBody.createFixture(boxFixtureDef); //add fixture to body

    kinematicBody.setLinearVelocity( new Vector2( 1, 0 ) ) //move right 1 unit per second
    kinematicBody.setAngularVelocity( 360 * TutorialBase.DEG_TO_RAD ) //1 turn per second counter-clockwise

    world
  }
}

object _00_Bodies {

  def main(args: Array[String]) {
    Tutorial.demo(new _00_Bodies)
  }


  /*
   * Adding fixtures to bodies gives them a shape (and also adds mass) allowing us to 'see' them.
   * What we do here isn't important for now as fixtures are covered in the next tutorial.
   * It's worth knowing that the density * area of the shape = the mass that will be added
   * to the body.
   */
  def createSimpleFixture = {
    val boxShape = new PolygonShape()
    boxShape.setAsBox(0.5f, 0.5f)

    val boxFixtureDef = new FixtureDef()
    boxFixtureDef.shape = boxShape
    boxFixtureDef.density = 1
    boxFixtureDef
  }


}
