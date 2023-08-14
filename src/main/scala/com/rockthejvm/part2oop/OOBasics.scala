package com.rockthejvm.part2oop

object OOBasics {

  class Person(val name: String = "Khoi", age: Int = 23) { // constructor signature
    // fields
    val allCaps = name.toUpperCase()

    // methods
    def greet(name: String): String = {
      s"${this.name} says: $name"
    }

    // signature differs
    // OVERLOADING
    def greet(): String = {
      s"Hi everyone, my name is ${this.name}"
    }

    // aux contructor
//    def this(name: String) =
//      this(name, 0)
//
//    def this() =
//      this("Jane Doe")

  } //  constructor structure

  val aPerson: Person = new Person("John", 26)
  val john: String = aPerson.name // class parameter != field
  val johnYelling: String = aPerson.allCaps
  val johnSaysHiToKhoi: String = aPerson.greet("Khoi")
  val johnSaysHi: String = aPerson.greet()
  val genericPerson: Person = new Person()

  def main(args: Array[String]): Unit = {

  }
}
