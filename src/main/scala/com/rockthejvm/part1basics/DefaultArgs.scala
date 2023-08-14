package com.rockthejvm.part1basics

import scala.annotation.tailrec

object DefaultArgs {

  @tailrec
  def sumUntilTailrec(x: Int, accumulator: Int = 0): Int =
    if (x <= 0) accumulator
    else sumUntilTailrec(x - 1, accumulator + x) // TAIL recursion = recursive call occurs LAST in its code path

  val sumUntil100 = sumUntilTailrec(100)

  def savePicture(dirPath: String, name: String, format: String = "jpg", width: Int = 1920, height: Int = 1080) = {
    println(s"Saving picture in format $format in path $dirPath")
  }

  def main(args: Array[String]): Unit = {
    savePicture("/users/akooi/photos", "myphoto")
    savePicture("/users/akooi/photos", "myphoto", height = 600, width = 800)

  }
}
