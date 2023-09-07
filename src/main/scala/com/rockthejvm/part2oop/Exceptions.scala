package com.rockthejvm.part2oop

import com.rockthejvm.practice.*

object Exceptions {
  val aString: String = null
  // aString.length crashes with a NPE

  // 1 - throw exceptions
  // val aWeirdValue: Int = throw new NullPointerException // returns Nothing

  // type Throwable
  // Error, e.g., SOError, OOMError
  // Exception, e.g., NPException, NSEException, ...

  def getInt(withExceptions: Boolean): Int = {
    if (withExceptions) throw new NullPointerException // RuntimeException("No Int for you!")
    else 42
  }

  val potentialFail = try {
    // code that might fail
    getInt(true) // an Int
  } catch {
    // most specific exceptions first
    case e: RuntimeException => 54 // an Int
    case e: NullPointerException => 35
    // ...
  } finally {
    // executed no matter what
    // closing resources
    // Unit here

  }

  // custom exceptions
  class MyException extends RuntimeException {
    // fields or methods
    override def getMessage: String = "MY EXCEPTION"

  }
  val myException = new MyException

  /**
   * Exercises
   */
  def infiniteAddition(n : Int): Int = {
    n + infiniteAddition(n + 1)
  }

  def allocateString(n: Int, acc: String): String = {
    if (n == 0) acc
    else allocateString(n - 1, acc + acc)
  }

  def allocateList(initVal: Int, numTimes: Int): LList[Int] = {
    if (numTimes == 0) EmptyLList()
    else Cons(initVal, allocateList(initVal, numTimes - 1))
  }

  val willStackOverflow: Any= try {
    infiniteAddition(0)
  } catch {
    case e: StackOverflowError => "Stack Overflow Error"
  }

  val willOOM: Any = try {
    // allocateList(42, 10000000)
    allocateString(10000000, "Scala")
  }
  catch {
    case e: OutOfMemoryError => "OOM error"
  }


  def main(args: Array[String]): Unit = {
    println(potentialFail)
    // val throwingMyException = throw myException

    // Stack overflow error
    println(willStackOverflow)
    println(willOOM)

  }
}
