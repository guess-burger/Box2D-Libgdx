package com.obsolete.libgdx.box2d

import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.math.Vector2

class Something extends TutorialPart{
  override def setUp() = new World(new Vector2(0, -10f), true)
}

object Something extends App {
  TutorialBase.play(new Something)
}
