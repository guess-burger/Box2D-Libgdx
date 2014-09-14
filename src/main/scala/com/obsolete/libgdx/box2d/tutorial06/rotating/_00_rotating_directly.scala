package com.obsolete.libgdx.box2d.tutorial06.rotating

import com.obsolete.libgdx.box2d.{TutorialBase, Tutorial}
import com.obsolete.libgdx.box2d.Tutorial.RAD_TO_DEG
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.math.{Vector3, Vector2}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType._
import com.badlogic.gdx.{Application, Gdx}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType

/**
 * http://www.iforce2d.net/b2dtut/rotate-to-angle
 */
class _00_rotating_directly extends Tutorial{
  var clickPoint = new Vector2(0,0)
  var body:Body = _
  override val name: String = "Rotating Directly"

  override def createWorld: World = {
    Gdx.app.setLogLevel(Application.LOG_DEBUG)
    //zero gravity
    val world = new World(new Vector2(0f, 0f), true)

    //body definition
    val myBodyDef = new BodyDef()
    myBodyDef.`type` = DynamicBody

    //hexagonal shape definition
    val polygonShape = new PolygonShape()
    val vertices = new Array[Vector2](6)
    for(i <- 0 until 6) {
      val angle = -i/6.0 * 360 * Tutorial.DEG_TO_RAD
      vertices(i) = new Vector2(Math.sin(angle).toFloat, Math.cos(angle).toFloat)
    }
    vertices(0).set( 0, 4 ) //change one vertex to be pointy
    polygonShape.set(vertices)

    //fixture definition
    val myFixtureDef = new FixtureDef
    myFixtureDef.shape = polygonShape
    myFixtureDef.density = 1

    //create dynamic body
    myBodyDef.position.set(0, 10)
    body = world.createBody(myBodyDef)
    body.createFixture(myFixtureDef)

    camera.translate(-200,0)
    camera.update()

    world
  }

  override def step() = {
    super.step()
    val pos = body.getPosition
    val desiredAngle = Math.atan2( -1*clickPoint.x-pos.x, clickPoint.y-pos.y )

    body.setTransform(body.getPosition, desiredAngle.toFloat )
    body.setAngularVelocity(0)
  }

  override def touchDown(x: Int, y: Int, pointer: Int, button: Int) = {
    val touchPos = new Vector3(x.toFloat/20,(400-y.toFloat)/20,0)
    clickPoint = new Vector2(touchPos.x-20,touchPos.y)
    addPoint(clickPoint.x,clickPoint.y)
    true
  }

  def addPoint(x:Float,y:Float){
    val myBodyDef = new BodyDef()
    myBodyDef.`type` = BodyType.StaticBody
    myBodyDef.position.set(0, 0)
    myBodyDef.position.set(x, y)
    myBodyDef.angle = 0
    val point = world.createBody(myBodyDef)
    val shape = new CircleShape()
    shape.setRadius(0.3f)
    val fix = new FixtureDef()
    fix.shape = shape
    fix.density = 1

    point.createFixture(fix)

    //Setting body properties
    point.setTransform(new Vector2(x, y), 0)
  }
}

object _00_rotating_directly {
  def main(args: Array[String]) {
    Tutorial.demo(new _00_rotating_directly)
  }
}
