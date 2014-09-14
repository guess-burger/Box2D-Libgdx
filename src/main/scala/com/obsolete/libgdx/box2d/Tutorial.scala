package com.obsolete.libgdx.box2d

import com.badlogic.gdx.{Gdx, InputProcessor, ApplicationListener}
import com.badlogic.gdx.physics.box2d.{World, Box2DDebugRenderer}
import com.badlogic.gdx.graphics.{GL10, OrthographicCamera}
import com.badlogic.gdx.Gdx._
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.graphics.g2d.SpriteBatch

trait Tutorial extends ApplicationListener with InputProcessor{

  var b2dr: Box2DDebugRenderer = _
  var camera: OrthographicCamera = _
  var world: World = _
  var debugMatrix: Matrix4 = _

  override def create() = {
    camera = new OrthographicCamera()
    b2dr = new Box2DDebugRenderer()

    camera.viewportHeight = Gdx.graphics.getHeight
    camera.viewportWidth = Gdx.graphics.getWidth
    camera.position.set(Gdx.graphics.getWidth * .5f, Gdx.graphics.getHeight * .5f, 0f)
    camera.update()

    debugMatrix = new Matrix4(camera.combined)
    debugMatrix.scale(20f,20f,1f)
    //debugMatrix.translate(20f,0f,0f)


    world = createWorld
    input.setInputProcessor(this)
  }

  override def render() {
    step()

    gl.glClearColor(0f, 0f, 0f, 0f)
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

    b2dr.render(world, debugMatrix)
  }

  override def dispose() {
    b2dr.dispose()
    world.dispose()
  }

  override def pause() = ()//Do nothing

  override def resume() = ()//Do nothing

  override def resize(width: Int, height: Int) = ()//Do nothing

  def keyDown(key: Int) = {
    if(key == Keys.R){
      world.dispose()
      world = createWorld
      true
    } else false
  }

  def keyUp(key: Int) = false
  def keyTyped(key: Char) = false
  def touchDown(i1: Int, i2: Int, i3: Int, i4: Int) = false
  def touchUp( i1: Int, i2: Int, i3: Int, i4: Int) = false
  def touchDragged(i1: Int, i2: Int, i3: Int) = false
  def mouseMoved(i1: Int, i2: Int) = false
  def scrolled(i1: Int) = false

  def step() = world.step(graphics.getDeltaTime, 6, 2)
  val name:String
  def createWorld:World

}

object Tutorial{

  val DEG_TO_RAD = 0.0174532925199432957f
  val RAD_TO_DEG = 57.295779513082320876f
  val WIDTH = 40
  val HEIGHT = 20

  def demo(tutorial: Tutorial){
    val cfg: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    cfg.title = tutorial.name
    cfg.width = 800
    cfg.height = 400
    new LwjglApplication(tutorial, cfg)
  }

}
