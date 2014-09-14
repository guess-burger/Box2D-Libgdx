package com.obsolete.libgdx.box2d

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.physics.box2d.{World, Box2DDebugRenderer}
import com.badlogic.gdx.graphics.{GL10, OrthographicCamera}
import com.badlogic.gdx.Gdx._
import com.badlogic.gdx.backends.lwjgl.{LwjglApplication, LwjglApplicationConfiguration}

class TutorialBase(tutPart: TutorialPart) extends ApplicationListener{

  var b2dr:Box2DDebugRenderer = _
  var camera: OrthographicCamera = _
  var world: World = _

  def create() = {
    b2dr = new Box2DDebugRenderer()

    camera = new OrthographicCamera()
    camera.viewportHeight = TutorialBase.HEIGHT
    camera.viewportWidth = TutorialBase.WIDTH
    camera.position.set(camera.viewportWidth * .5f, camera.viewportHeight * .5f, 0f)
    camera.update()

    //setUp using tutPart
    world = tutPart.setUp
    for(listener <- tutPart.getInputListener){input.setInputProcessor(listener)}
  }

  def render() = {
    gl.glClearColor(0f,0f,0f,0f)
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT)

    world.step(graphics.getDeltaTime, 6, 2)

    camera.update()
    b2dr.render(world, camera.combined)
  }


  def dispose(){
    b2dr.dispose()
    world.dispose()
  }

  override def pause() = {/*Do nothing*/}
  override def resume() = {/*Do nothing*/}
  override def resize(width: Int, height: Int)={/*Do nothing*/}

}

object TutorialBase{

  val DEG_TO_RAD = 0.0174532925199432957f
  val RAD_TO_DEG = 57.295779513082320876f
  val WIDTH = 40
  val HEIGHT = 20

  def play(tutPart: TutorialPart){
    val cfg: LwjglApplicationConfiguration = new LwjglApplicationConfiguration
    cfg.title = tutPart.name
    cfg.width = 800
    cfg.height = 400
    new LwjglApplication(new TutorialBase(tutPart), cfg)
  }

}
