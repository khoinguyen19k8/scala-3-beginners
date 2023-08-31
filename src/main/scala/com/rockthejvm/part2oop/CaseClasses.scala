package com.rockthejvm.part2oop

object CaseClasses {

  // lightweight data structures
  case class Person(name: String, age: Int)

  // 1 - class args are now fields
  val daniel = new Person("Daniel", 99)
  val danielAge = daniel.age

  // 2 - toString, equals, and hashCode
  val danielToString = daniel.toString // Person("Daniel", 99)
  val danielDuped = new Person("Daniel", 99)
  val isSameDaniel = (daniel == danielDuped) // true

  // 3 - utility methods
  val danielYounger = daniel.copy(age = 78) // new Person("Daniel", 78)

  // 4 - CCs have companion objects
  val thePersonSingelton = Person
  val daniel_v2 = Person("Daniel", 99) // "constructor"

  // 5 - CCs are serializable
  // use-case: Akka

  // 6 - CCs have extractor patterns for PATTERN MATCHING

  // case classes must have arg list
  case object UnitedKingdom {
    // fields and methods
     def name: String = "The UK of GB and Ni"
  }

  case class CCWithArgListNoArgs() // legal

  /**
   * Exercises
   * @param args
   */

  def main(args: Array[String]): Unit = {
    println(daniel)
    println(isSameDaniel)

  }
}
