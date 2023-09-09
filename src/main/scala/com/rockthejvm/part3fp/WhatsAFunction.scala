package com.rockthejvm.part3fp

object WhatsAFunction {

  // FP: functions are "first-class" citizens
  // JVM

  trait MyFunction[A, B] {
    def apply(arg: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningOfLife = 42
  val meaningDoubled = doubler(meaningOfLife)

  // function types
  val doublerStandard = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }
  val meaningDoubled_v2 = doublerStandard(meaningOfLife)

  // (Int, String, Double, Boolean) => Int
  val afourArgsFunction = new Function4[Int, String, Double, Boolean, Int] {
    override def apply(v1: Int, v2: String, v3: Double, v4: Boolean): Int = ???
  }

  /**
   * Exercises
   * @param args
   */

  val concatString = new ((String, String) => String) {
    override def apply(v1: String, v2: String): String = v1 + v2
  }

  val superAdder: Int => (Int => Int) = { (x: Int) =>
    (y: Int) => x + y
  }

  val adder2 = superAdder(2)
  val anAddition_v2 = adder2(67) // 69
  // currying
  val anAddition_v3 = superAdder(2)(67)

  // function values != methods

  // all functions are instances of FunctionX with apply methods
  def main(args: Array[String]): Unit = {
    println(concatString("Scala ", "Python"))
    println(anAddition_v3)

  }
}
