package com.rockthejvm.practice

import java.util.Currency
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
  infix def ++(another: LList[A]): LList[A]

  // map, filter, flatMap
  def map[B](transformer: A => B): LList[B]
  def filter(predicate: A => Boolean): LList[A]
  def flatMap[B](transformer: A => LList[B]): LList[B]

  // apply, length
  def length(): Int
  def apply(index: Int): A

  // forEach, sort, zip, etc
  def foreach(func: A => Unit): Unit
  def sort(compare: (A, A) => Int): LList[A]
  def zipWith[B, T](another: LList[T], zip: (A, T) => B): LList[B]
  def foldLeft[B](start: B)(operator: (A, B) => B): B
}

case class EmptyLList[A]() extends LList[A] {
  override def head: A = throw new NoSuchElementException
  override def tail: LList[A] = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  // override def toString: String = "[]"

  override infix def ++(another: LList[A]): LList[A] = another

  override def map[B](transformer: A => B): LList[B] = EmptyLList()
  override def filter(predicate: A => Boolean): LList[A] = this
  override def flatMap[B](transformer: A => LList[B]): LList[B] = EmptyLList()

  override def length(): Int = 0
  override def apply(index: Int): A = throw new IndexOutOfBoundsException

  override def foreach(func: A => Unit): Unit = {}
  override def sort(compare: (A, A) => Int): LList[A] = this
  override def zipWith[B, T](another: LList[T], zip: (A, T) => B): LList[B] = EmptyLList[B]()
  override def foldLeft[B](start: B)(operator: (A, B) => B): B = start

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

  override infix def ++(another: LList[A]): LList[A] = {
    Cons[A](head, tail ++ another)
  }

  override def map[B](transformer: A => B): LList[B] = {
    Cons[B](transformer(head), tail.map(transformer))
  }

  override def filter(predicate: A => Boolean): LList[A] = {
    if predicate(head) then Cons[A](head, tail.filter(predicate))
    else tail.filter(predicate)
  }

  override def flatMap[B](transformer: A => LList[B]): LList[B] = {
    transformer(head) ++ tail.flatMap(transformer)
  }

  override def length(): Int =
    @tailrec
    def lengthTail(current: LList[A], n: Int): Int =
      if (current.isEmpty) n
      else lengthTail(current.tail, n + 1)
    lengthTail(this, 0)

  override def apply(index: Int): A =
    @tailrec
    def applyTail(current: LList[A], index: Int): A =
      if ((index == 0) && !current.isEmpty) current.head
      else applyTail(current.tail, index - 1)

    if (index > this.length() - 1) throw new IndexOutOfBoundsException("Index larger than list length")
    else if (index < 0) throw new IndexOutOfBoundsException("Index smaller than 0")
    else applyTail(this, index)

  override def foreach(func: A => Unit): Unit =
    func(head)
    tail.foreach(func)

  override def sort(compare: (A, A) => Int): LList[A] =
    // insertion sort
    // compare evaluates to > 0 if arg1 > arg2, 0 if arg1 == arg2
    def insert(elem: A, sortedList: LList[A]): LList[A] =
      if (sortedList.isEmpty) Cons(elem, EmptyLList())
      else if (compare(elem, sortedList.head) <= 0) Cons(elem, sortedList)
      else Cons(sortedList.head, insert(elem, sortedList.tail))

    val sortedTail = tail.sort(compare)
    insert(head, sortedTail)

  override def zipWith[B, T](another: LList[T], zip: (A, T) => B): LList[B] =
    if (this.length() != another.length()) throw new RuntimeException("Two lists of different length")
    else Cons[B](zip(this.head, another.head), this.tail.zipWith(another.tail, zip))

  override def foldLeft[B](start: B)(operator: (A, B) => B): B =
    this.tail.foldLeft(operator(this.head, start))(operator)
}

object LList {
  @tailrec
  def find[A](list: LList[A], predicate: A => Boolean): A = {
    if (predicate(list.head)) list.head
    else if (list.isEmpty) throw new NoSuchElementException("No such element satisfied the predicate found")
    else find(list.tail, predicate)
  }
}


object LListTest {
  def main(args: Array[String]): Unit = {

    // LList testing
    println("# LList construction testing\n")
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
    println("\n# map, filter, flatMap, find testing\n")
    val doubler: Int => Int = _ * 2
    val DoublerList: Int => LList[Int] = value => Cons(value, Cons(value + 1, EmptyLList[Int]()))
    val numbersDoubled = first3Numbers.map(doubler)
    val numbersNested = first3Numbers.map(DoublerList)
    println(numbersDoubled)
    println(numbersNested)

    // filter testing
    val evenPredicate: Int => Boolean = (_ % 2 == 0)
    val numbersOnlyEven = first3Numbers.filter(evenPredicate)
    println(numbersOnlyEven)

    // flatMap testing
    val numbersFlatted = first3Numbers.flatMap(DoublerList)
    println(numbersFlatted)

    // find testing
    val oddPredicate: Int => Boolean = (_ % 2 != 0)
    val over100Predicate: Int => Boolean = (_ > 100)
    val numbersSeq: LList[Int] = Cons(14, Cons(17, Cons(2, Cons(98, Cons(9, empty)) )))
    println(LList.find(numbersSeq, oddPredicate))

    println("\n# Exception testing\n")

    try {
      println(LList.find(numbersSeq, over100Predicate))
    } catch {
      case e: NoSuchElementException => println("No such element satisfied the filter")
    }

    println("\n# apply, length testing")
    println(s"numbersSeq length: ${numbersSeq.length()}")
    println(s"numbersSeq elements: ${numbersSeq(0)}, ${numbersSeq(3)}, ${numbersSeq(4)}")

    println("\n# foreach testing\n")
    numbersSeq.foreach(x=> println(x))

    println("\n# zipWith testing\n")
    val anotherNumbersSeq: LList[Int] = Cons(6, Cons(3, Cons(18, Cons (2, Cons (11, empty)))))
    println(numbersSeq.zipWith(anotherNumbersSeq, (x, y) => x + y))
    println(first3Numbers.zipWith[String, String](first3Strings, (num, str) => s"$num-$str"))

    println("\n# foldLeft testing\n")
    println(numbersSeq.foldLeft[Int](0)(_ + _))

    println("\n# sort testing\n")
    println(numbersSeq.sort(_ - _))


  }
}


