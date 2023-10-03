package com.rockthejvm.part4power

import scala.util.Random

object PatternMatching {
  // switch on steroid
  val random = new Random()
  val aValue = random.nextInt(100)

  val description = aValue match
    case 1 => "the first"
    case 2 => "the second"
    case 3 => "the third"
    case _ => s"something else: $aValue"

  // decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match
    case Person(n, a) if a > 18 => s"Hello there, my name is $n and I'm $a years old."
    case Person(n, a) => s"Hello there, my name is $n and I'm not allowed to say my age."
    case _ => "I don't know who I am."

  /**
   * Patterns are matched in order
   * no cases match => MatchError
   * type returned => lowest common ancestor of all types
   */

  // PM on sealed hierarchies
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Cat(meowStyle: String) extends Animal

  val anAnimal: Animal = Dog("Terra Nova")
  val animalPM = anAnimal match
    case Dog(someBreed) => "I've detected a dog"
    case Cat(meow) => "I've detected a cat"

  /**
   * Exercises
   * @param args
   */
  trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String =
    expr match
      case Number(n: Int) => s"$n"
      case Sum(e1: Expr, e2: Expr) => s"${show(e1)} + ${show(e2)}"
      case Prod(e1: Expr, e2: Expr) =>
        def maybeShowParentheses(exp: Expr) =
          exp match
            case Sum(_: Expr, _: Expr) => s"(${show(exp)})"
            case _ => show(exp)
        s"${maybeShowParentheses(e1)} * ${maybeShowParentheses(e2)}"


  def main(args: Array[String]): Unit = {
    println(description)
    println(greeting)
    println(show(Sum(Number(2), Number(3))))
    println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
    println(show(Prod(Sum(Number(2), Number(3)), Number(4))))
    println(show(Sum(Prod(Number(2), Number(3)), Number(4))))
  }
}
