package com.rockthejvm.practice

import scala.annotation.tailrec

// singly linked list
// [1,2,3] = [1] -> [2] -> [3]
abstract class LList[A] {
  def head: A
  def tail: LList[A]
  def isEmpty: Boolean
  def add(element: A): Cons[A] = {
    Cons(element, this)
  }

  // concatenation
  infix def ++(anotherList: LList[A]): LList[A]
  def map[B](transformer: Transformer[A, B]): LList[B]
  def filter(predicate: Predicate[A]): LList[A]
  def flatMap[B](transformer: Transformer[A, LList[B]]): LList[B]
}

trait Predicate[T] {
  def test(element: T): Boolean
}

class EvenPredicate extends Predicate[Int] {
  override def test(element: Int): Boolean =
    (element % 2) == 0
}

trait Transformer[A, B] {
  def transform(value: A): B
}

class Doubler extends Transformer[Int, Int] {
  override def transform(value: Int): Int =
    value * 2
}

class DoublerList extends Transformer[Int, LList[Int]] {
  override def transform(value: Int): LList[Int] = Cons(value, Cons(value + 1, EmptyLList[Int]()))
}
case class EmptyLList[A]() extends LList[A] {
  override def head: A = throw new NoSuchElementException
  override def tail: LList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  // override def toString: String = "[]"

  override infix def ++(anotherList: LList[A]): LList[A] = anotherList

  override def map[B](transformer: Transformer[A, B]): LList[B] = EmptyLList()
  override def filter(predicate: Predicate[A]): LList[A] = this
  override def flatMap[B](transformer: Transformer[A, LList[B]]): LList[B] = EmptyLList()
  }


case class Cons[A](override val head: A, override val tail: LList[A]) extends LList[A] {
  override def isEmpty: Boolean = false
  override def toString: String = {
    @tailrec
    def concatenateElements(remainderList: LList[A], str_accumulator: String): String = {
      if (remainderList.isEmpty) str_accumulator
      else concatenateElements(remainderList.tail, s"$str_accumulator ${remainderList.head}")
    }
    s"[${concatenateElements(this.tail, s"${this.head}")}]"
  }

  override infix def ++(anotherList: LList[A]): LList[A] = {
    Cons[A](head, tail ++ anotherList)
  }

  override def map[B](transformer: Transformer[A, B]): LList[B] = {
    Cons[B](transformer.transform(head), tail.map(transformer))
  }

  override def filter(predicate: Predicate[A]): LList[A] = {
    if predicate.test(head) then Cons[A](head, tail.filter(predicate))
    else tail.filter(predicate)
  }

  override def flatMap[B](transformer: Transformer[A, LList[B]]): LList[B] = {
    transformer.transform(head) ++ tail.flatMap(transformer)
  }
}


object LListTest {
  def main(args: Array[String]): Unit = {

    val empty = EmptyLList[Int]()
    val emptyString = EmptyLList[String]()
    // println(empty)
    val first3Numbers: Cons[Int] = Cons(1, Cons(2, Cons(3, empty)))
    val first3Numbers_v2: Cons[Int] = empty.add(1).add(2).add(3)
    val first3Strings: Cons[String] = Cons("Scala", Cons("Python", Cons("C", emptyString)))
    val first3Strings_v2: Cons[String] = emptyString.add("Scala").add("Python").add("C")
    println(s"$first3Numbers")
    println(s"$first3Numbers_v2")
    println(s"$first3Strings")
    println(s"$first3Strings_v2")

    val doubler: Transformer[Int, Int] = new Doubler
    val numbersDoubled = first3Numbers.map(doubler)
    val numbersNested = first3Numbers.map(new DoublerList)
    val numbersOnlyEven = first3Numbers.filter(new EvenPredicate)
    val numbersFlatted = first3Numbers.flatMap(new DoublerList)
    println(numbersDoubled)
    println(numbersNested)
    println(numbersOnlyEven)
    println(numbersFlatted)

  }
}


