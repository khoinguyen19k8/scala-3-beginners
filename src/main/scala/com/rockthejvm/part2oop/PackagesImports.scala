package com.rockthejvm.part2oop

val meaningOfLife = 42
def computeMyLife: String = "Scala"

object PackagesImports { // top-level

  // packages = "folders"

  // fully qualified name
  val aList: com.rockthejvm.practice.LList[Int] = ??? // throws NotImplementedError

  // import
  import com.rockthejvm.practice.LList
  val anotherList: LList[Int] = ???

  // importing under an alias
  import java.util.{List as JList}
  val aJavaListL: JList[Int] = ???

  // import everything
  import com.rockthejvm.practice.*
  // val aPredicate: Predicate[Int] = ???

  // import multiple symbols
  import PhysicsConstants.{SPEED_OF_LIGHT, EARTH_GRAVITY}
  val c = SPEED_OF_LIGHT

  // import everything but something
  object PlayingPhysics {
    import PhysicsConstants.{PLANK as _, *}
  }

  // default imports
  // scala.*, scala.PreDef, java.lang.*

  // exports
  class PhysicCalculator {
    import PhysicsConstants.*
    def computePhotonEnergy(wavelength: Double): Double = {
      PLANK / wavelength
    }
  }

  object ScienceApp {
    val physicsCalculator = new PhysicCalculator

    export physicsCalculator.computePhotonEnergy
    def computeEquivalentMass(wavelength: Double): Double = {
      computePhotonEnergy(wavelength) / (SPEED_OF_LIGHT * SPEED_OF_LIGHT)
    }
  }
  def main(args: Array[String]): Unit = {

  }
}
object PhysicsConstants {
  // constants
  val SPEED_OF_LIGHT = 299792458
  val PLANK = 6.62e-34 // scientific
  val EARTH_GRAVITY = 9.8

}
