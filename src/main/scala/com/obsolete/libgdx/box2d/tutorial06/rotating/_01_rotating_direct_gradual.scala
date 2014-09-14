package com.obsolete.libgdx.box2d.tutorial06.rotating

import com.obsolete.libgdx.box2d.Tutorial
import com.badlogic.gdx.math.{Vector3, Vector2}
import com.badlogic.gdx.physics.box2d._
import com.badlogic.gdx.{Application, Gdx}
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType._
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType


class _01_rotating_direct_gradual extends Tutorial{
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
    myBodyDef.position.set(20, 10)
    body = world.createBody(myBodyDef)
    body.createFixture(myFixtureDef)

    camera.translate(-200,0)
    camera.update()

    world
  }

  override def step() = {
    super.step()
    val pos = body.getPosition
    val desiredAngle = Math.atan2( -1*clickPoint.x-pos.x, clickPoint.y-pos.y ).toFloat

    val change = 1f * Tutorial.DEG_TO_RAD //allow 1 degree rotation per time step

    val bodyAngle = body.getAngle
    val newAngle: Float = bodyAngle + Math.min( change, Math.max(-change, desiredAngle - bodyAngle))
    Gdx.app.log(this.getClass.getName, s"newAngle=$newAngle")

    body.setTransform(pos, newAngle )
    body.setAngularVelocity(0)
  }

  override def touchDown(x: Int, y: Int, pointer: Int, button: Int) = {
    val touchPos = new Vector3(x.toFloat/20,(400-y.toFloat)/20,0)
    clickPoint = new Vector2(touchPos.x,touchPos.y)
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

object _01_rotating_direct_gradual {
  def main(args: Array[String]) {
    Tutorial.demo(new _01_rotating_direct_gradual)
  }
}