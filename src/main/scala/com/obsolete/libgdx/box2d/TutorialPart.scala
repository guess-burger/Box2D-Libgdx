package com.obsolete.libgdx.box2d

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.InputAdapter

trait TutorialPart {

  val name:String = "Box2D"
  def setUp:World = ???
  def getInputListener:Option[InputAdapter] = None
}
