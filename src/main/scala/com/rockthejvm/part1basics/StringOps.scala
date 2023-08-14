package com.rockthejvm.part1basics

object StringOps {
  val aString: String = "Hello, I am learning Scala"

  // string functions
  val secondChar: Char = aString.charAt(1)
  val firstWord: String = aString.substring(0, 5) // "Hello", 0 inclusive and 5 exclusive
  val words: Array[String] = aString.split(" ") // Array("Hello,", "I", "am", "learning", "Scala")
  val startsWithHello: Boolean = aString.startsWith("Hello") // true
  val allDashes: String = aString.replace(" ", "-")
  val allUppercase: String = aString.toUpperCase() // also toLowerCase()
  val nChars: Int = aString.length

  // other functions
  val reversed: String = aString.reverse
  val aBunchOfChars: String = aString.take(10)

  // parse to numeric
  val numberAsString: String = "2"
  val number: Int = numberAsString.toInt

  // interpolation
  val name: String = "Alice"
  val age: Int = 12
  val greeting: String = s"Hello, I'm $name and I'm $age years old"
  val greeting_v2: String = s"Hello, I'm $name and I will be turning ${age + 1} years old"

  // f-interpolation
  val speed: Float = 1.2f
  val myth: String = f"$name can eat $speed%2.2f burgers per minute"

  // raw interpolation
  val escapes: String = raw"This is a \n newline"


  def main(args: Array[String]): Unit = {
    println(words.mkString(" "))
    println(allDashes)
    println(f"$nChars%d")
    println(myth)
    println(escapes)
  }
}
