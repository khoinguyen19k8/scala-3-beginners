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

object LList {
  @tailrec
  def find[A](list: LList[A], predicate: Predicate[A]): A = {
    if (predicate.test(list.head)) list.head
    else if (list.isEmpty) throw new NoSuchElementException("No such element satisfied the predicate found")
    else find(list.tail, predicate)
  }
}


object LListTest {
  def main(args: Array[String]): Unit = {

    // LList testing
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

    // map testing
    val doubler: Transformer[Int, Int] = new Doubler
    val numbersDoubled = first3Numbers.map(doubler)
    val numbersNested = first3Numbers.map(new DoublerList)
    println(numbersDoubled)
    println(numbersNested)

    // filter testing
    val numbersOnlyEven = first3Numbers.filter(new EvenPredicate)
    println(numbersOnlyEven)

    // flatMap testing
    val numbersFlatted = first3Numbers.flatMap(new DoublerList)
    println(numbersFlatted)

    // find testing
    val oddPredicate = new Predicate[Int] {
      override def test(element: Int): Boolean = {
        element % 2 != 0
      }
    }
    val over100Predicate = new Predicate[Int] {
      override def test(element: Int): Boolean = {
        element > 100
      }
    }
    val numbersSeq: LList[Int] = Cons(14, Cons(17, Cons(2, EmptyLList[Int]())))
    println(LList.find(numbersSeq, oddPredicate))
    println(LList.find(numbersSeq, over100Predicate))

  }
}


