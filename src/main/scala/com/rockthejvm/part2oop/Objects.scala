package com.rockthejvm.part2oop

object Objects {

  object MySingleton {
    val aField = 45
    def aMethod(x: Int): Int = x + 1
  } // type + the only instance of this type

  val theSingleton: Singleton = MySingleton
  val anotherSingleton: Singleton = MySingleton
  val isSameSingleton: Boolean = theSingleton == anotherSingleton // true

  val theSingletonField = MySingleton.aField
  val theSingletonMethodCall = MySingleton.aMethod(99)

  class Person(name: String) {
    def sayHi(): String = s"Hi, my name is $name"
  }

  // companions = class + object with the same name in the same file
  object Person { // companion object
    // can access each other's private fields and methods
    val N_EYES = 2
    def canFly(): Boolean = false
  }

  // methods and fields in classes are used for instance-dependent functionality
  val mary: Person = new Person("mary")
  val mary_v2: Person = new Person("mary")
  val marysGreeting = mary.sayHi()

  // methods and fields in objects are used for instance-independent functionality - "static"
  val humansCanFly: Boolean = Person.canFly()
  val nEyesHuman: Int = Person.N_EYES

  // equality
  // 1 - equality of reference - usually defined as ==
  val sameMary = mary eq mary_v2 // false, different instances
  val sameSingleton = MySingleton eq MySingleton // true
  // equality of "sameness" - in Java defined as .equals
  val sameMary_v2 = mary equals mary_v2 // false
  val sameMary_v3 = mary == mary_v2 // same as equals
  val sameSingleton_v2 = MySingleton == MySingleton

  // objects can extend classes
  object BigFoot extends Person("Big Foot")

  // Scala application = object + def main(args: Array[String]): Unit

  def main(args: Array[String]): Unit = {
    println(isSameSingleton)

  }
}
