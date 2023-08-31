package com.rockthejvm.practice

import scala.annotation.tailrec

// singly linked list
// [1,2,3] = [1] -> [2] -> [3]
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

object LListTest {
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


