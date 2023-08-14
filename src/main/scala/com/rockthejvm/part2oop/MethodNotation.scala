package com.rockthejvm.part2oop

import scala.language.postfixOps

object MethodNotation {

  class Person(val name: String, val age: Int, favoriteMovie: String) {
    infix def likes(movie: String): Boolean = {
      movie == favoriteMovie
    }

    infix def +(person: Person): String = {
      s"${this.name} is hanging out with ${person.name}"
    }

    infix def +(nickname: String): Person = {
      new Person(s"$name $nickname", this.age, this.favoriteMovie)
    }

    infix def !!(progLanguage: String): String = {
      s"$name wonders how can $progLanguage be so cool"
    }

    // prefix position
    // unary ops: -, +, ~, !
    // No arguments
    def unary_- : String = {
      s"$name's alter ego"
    }

    def unary_+ : Person = {
      new Person(this.name, this.age + 1, this.favoriteMovie)
    }


    def isAlive: Boolean = {
      true
    }

    def apply(): String = {
      s"Hi, my name is $name and I really enjoy $favoriteMovie"
    }

    def apply(times: Int): String = {
      s"$name watched $favoriteMovie $times times"
    }
  }

  val mary: Person = new Person("Mary", 34, "Inception")
  val john: Person = new Person("John", 36, "Fight Club")

  /**
   * Exercises
   * @param args
   */

  def main(args: Array[String]): Unit = {
    println(mary.likes("Fight Club"))
    // infix notation - for methods with ONE argument
    println(mary likes "Fight Club")

    // "operator"
    println(mary + john)
    println(mary.+(john)) //identical
    println(2 + 3)
    println(2.+(3))
    println(mary !! "Scala")

    // prefix position
    println(-mary)

    // postfix position
    println(mary.isAlive)
    println(mary isAlive) // discouraged

    // apply is special
    println(mary.apply())
    println(mary()) // same

    // Exercises
    println((mary + "the scandalous raven").name)
    println((+mary).age)
    println(mary(2))
  }
}
