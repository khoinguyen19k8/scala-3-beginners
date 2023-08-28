package com.rockthejvm.practice

import scala.annotation.tailrec

// singly linked list
// [1,2,3] = [1] -> [2] -> [3]
abstract class LList {
  def head: Int
  def tail: LList
  def isEmpty: Boolean
  def add(element: Int): Cons = {
    new Cons(element, this)
  }
}

class Empty extends LList {
  override def head: Int = throw new NoSuchElementException
  override def tail: LList = throw new NoSuchElementException
  override def isEmpty: Boolean = true
  override def toString: String = "[]"
}

class Cons(override val head: Int, override val tail: LList) extends LList {
  override def isEmpty: Boolean = false
  override def toString: String = {
    @tailrec
    def concatenateElements(remainderList: LList, str_accumulator: String): String = {
      if (remainderList.isEmpty) str_accumulator
      else concatenateElements(remainderList.tail, s"$str_accumulator ${remainderList.head}")
    }
    s"[${concatenateElements(this.tail, s"${this.head}")}]"
  }
}

object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = new Empty
    // println(empty)
    val first3Numbers: Cons = new Cons(1, new Cons(2, new Cons(3, empty)))
    println(s"$first3Numbers")
    val first3Numbers_v2: Cons = empty.add(1).add(2).add(3)
    println(s"$first3Numbers_v2")

  }
}


