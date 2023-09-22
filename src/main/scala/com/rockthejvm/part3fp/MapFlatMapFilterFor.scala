package com.rockthejvm.part3fp

object MapFlatMapFilterFor {

  // standard list
  val aList = List(1,2,3)
  val firstElement = aList.head
  val restOfElements = aList.tail

  // map
  val anIncrementedList = aList.map(_ + 1)

  // filter
  val onlyOddNumbers = aList.filter(_ % 2 != 0)

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  val aFlatMappedList = aList.flatMap(toPair) // [1,2,2,3,3,4]

  //
  val numbers = List(1,2,3,4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white", "red")

  val numPairs = (x: Int) => chars.map((c: Char) => s"$x$c")
  val colorsPairs = (s1: String) => colors.map((s2: String) => s"$s1 - $s2")
  val combinations = numbers.flatMap(numPairs).flatMap(colorsPairs)

  // for-comprehension = IDENTICAL to flatMap + map chains
  val combinationsFor = for {
    number <- numbers if number % 2 == 0// generator
    char <- chars
    color <- colors
  } yield s"$number$char - $color" // an EXPRESSION

  // for-comprehension with Unit
  // if foreach

//  numbers.foreach(println)

  def main(args: Array[String]): Unit = {
    for {
      num <- numbers
    } println(num)

    println(combinations)
    println(combinationsFor)
  }
}
