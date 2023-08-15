package com.rockthejvm.part2oop

object AccessModifiers {
  class Person(val name: String) {
    protected def sayHi(): String = s"Hi, my name is $name"
    private def watchNetflix(): String = "I'm binge watching my favorite series..."
  }

  // protected == access to inside the class + children classes
  class Kid(override val name: String, age: Int) extends Person(name) {
    def greetPolitely(): String = // no modifier = "public"
      sayHi() + "I love to play!"
  }

  val aPerson: Person = new Person("Alice")
  val aKid: Kid = new Kid("David", 5)

  // complication
  class KidWithParents(override val name: String, age: Int, momName: String, dadName: String) extends Person(name) {
    val mom: Person = new Person(momName)
    val dad: Person = new Person(dadName)

//    def everyoneSaysHi(): String =
//      this.sayHi() + s"Hi, I'm $name, and here are my parents: " + mom.sayHi() + dad.sayHi() // not legal
  }
  def main(args: Array[String]): Unit = {
    println(aKid.greetPolitely())
  }
}
