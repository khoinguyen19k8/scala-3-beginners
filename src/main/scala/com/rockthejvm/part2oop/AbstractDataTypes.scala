package com.rockthejvm.part2oop

object AbstractDataTypes {

  abstract class Animal {
    val creatureType: String // abstract
    def eat(): Unit
    // non-abstract fields/methods are allowed
    def preferredMeal: String = "anything" // "accessor methods"
  }

//  val anAnimal: Animal = new Animal // not allowed

  class Dog extends Animal {
    override val creatureType: String = "domestic"
    override def eat(): Unit = println("crunching this bone")
    // overriding is legal for everything
    override val preferredMeal: String = "bones" // overriding accessor method with a field
  }

  val aDog: Animal = new Dog

  // traits
  trait Carnivore { // Scala 3 - traits can have constructor args
    def eat(animal: Animal): Unit
  }

  class TRex extends Carnivore {
    override def eat(animal: Animal): Unit = println("I'm a T-rex, I eat animals")
  }

  // practical differences
  // one class inheritance
  // multiple traits inheritance

  trait ColdBlooded
  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"
    override def eat(): Unit = println("I'm a croc, I just crunch stuff")
    override def eat(animal: Animal): Unit = println("croc eating Animal")
  }

  /**
   * - abstract classes are THINGS
   * - traits are BEHAVIOURS
   */

  /**
   * Any
   *  AnyRef
   *    All classes we write
   *      scala.Null (the null reference)
   *  AnyVal
   *    Int, Boolean, Char, ...
   *
   *
   *      scala.Nothing
  */

  val nonExistentAnimal: Animal = null
  val anInt: Int = throw new NullPointerException

  def main(args: Array[String]): Unit = {

  }
}
