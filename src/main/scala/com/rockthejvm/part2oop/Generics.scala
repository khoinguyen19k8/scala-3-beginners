package com.rockthejvm.part2oop

import scala.annotation.tailrec

object Generics {
  // reuse code on different types

  abstract class MyList[A] { // "generic" list - Java: abstract class MyList<A>
    def head: A
    def tail: MyList[A]
  }

  class Empty[A] extends MyList[A] {
    override def head: A = throw new NoSuchElementException
    override def tail: MyList[A] = throw new NoSuchElementException
  }

  class NonEmpty[A](override val head: A, override val tail: MyList[A]) extends MyList[A]

  val listOfIntegers: MyList[Int] = new NonEmpty[Int](1, new NonEmpty[Int](2, new Empty[Int]))
  val listOfStrings = new NonEmpty("Scala", new NonEmpty("Java", new Empty))

  val firstNumber = listOfIntegers.head
  val adding = firstNumber + 3

  // multiple generic types
  trait MyMap[K, V]

  // generic methods
  object MyList {
    def from2Elements[A](elem1: A, elem2: A): MyList[A] =
      new NonEmpty[A](elem1, new NonEmpty[A](elem2, new Empty[A]))
  }

  val first2Numbers = MyList.from2Elements[Int](1, 2)
  val first2Numbers_v2 = MyList.from2Elements(1, 2)
  val first2Numbers_v3 = new NonEmpty(1, new NonEmpty(2, new Empty))

  /**
   * Exercises: generics LList
   * @param args
   */

  abstract class LList[A] {
    def head: A
    def tail: LList[A]
    def isEmpty: Boolean
    def add(element: A): Cons[A] = {
      new Cons(element, this)
    }
  }

  class EmptyLList[A] extends LList[A] {
    override def head: A = throw new NoSuchElementException
    override def tail: LList[A] = throw new NoSuchElementException
    override def isEmpty: Boolean = true
    override def toString: String = "[]"
  }

  class Cons[A](override val head: A, override val tail: LList[A]) extends LList[A] {
    override def isEmpty: Boolean = false
    override def toString: String = {
      @tailrec
      def concatenateElements(remainderList: LList[A], str_accumulator: String): String = {
        if (remainderList.isEmpty) str_accumulator
        else concatenateElements(remainderList.tail, s"$str_accumulator ${remainderList.head}")
      }
      s"[${concatenateElements(this.tail, s"${this.head}")}]"
    }
  }

  def main(args: Array[String]): Unit = {
    val empty = new EmptyLList[Int]
    val emptyString = new EmptyLList[String]
    // println(empty)
    val first3Numbers: Cons[Int] = new Cons(1, new Cons(2, new Cons(3, empty)))
    val first3Numbers_v2: Cons[Int] = empty.add(1).add(2).add(3)
    val first3Strings: Cons[String] = new Cons("Scala", new Cons("Python", new Cons("C", emptyString)))
    val first3Strings_v2: Cons[String] = emptyString.add("Scala").add("Python").add("C")
    println(s"$first3Numbers")
    println(s"$first3Numbers_v2")
    println(s"$first3Strings")
    println(s"$first3Strings_v2")

  }
}
