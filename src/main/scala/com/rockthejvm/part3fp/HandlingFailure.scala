package com.rockthejvm.part3fp

import scala.util.{Failure, Random, Success, Try}

object HandlingFailure {
  // Try = a potentially failed computation
  val aTry: Try[Int] = Try(42)
  val aFailedTry: Try[Int] = Try(throw new RuntimeException)

  // alt construction
  val aTry_v2: Try[Int] = Success(42)
  val aFailedTry_v2: Try[Int] = Failure(new RuntimeException)

  // main API
  val checkSuccess = aTry.isSuccess
  val checkFailure = aTry.isFailure
  val aChain = aFailedTry.orElse(aTry)

  // map, flatMap, filter, for comprehensions
  val anIncrementedTry = aTry.map(_ + 1)
  val aFlatmappedTry = aTry.flatMap(mol => Try(s"My meaning of life is $mol"))
  val aFilteredTry = aTry.filter(_ % 2 == 0)

  // WHY: avoid unsafe APIs which can throw exceptions
  def unsafeMethod(): String =
    throw new RuntimeException("no string for you, buster!")

  // defensive: try-catch-finally
  val stringLengthDefensive = try
    val aString = unsafeMethod()
    aString.length
  catch
    case e: RuntimeException => -1

  // purely functional
  val stringLengthPure = Try(unsafeMethod()).map(_.length).getOrElse(-1)

  // DESIGN
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No string for you, buster!"))
  def betterBackupMethod(): Try[String] = Success("Scala")
  val stringLengthPure_v2 = betterUnsafeMethod().map(_.length)
  val aSafeChain = betterUnsafeMethod().orElse(betterBackupMethod()).map(_.length)

  /**
   * Exercises
   * @param args
   */
  val host = "localhost"
  val port = "8081"
  val myDesiredURL = "rockthejvm.com/home"


  class Connection:
    val random = new Random()

    def get(url: String): Try[String] =
      if random.nextBoolean() then Success("<html>Success</html>")
      else Failure(new RuntimeException("Cannot fetch page right now"))

  object HttpService:
    val random = new Random()

    def getConnection(host: String, port: String): Try[Connection] =
      if random.nextBoolean() then Success(new Connection)
      else Failure(new RuntimeException("Cannot access host/port combination"))

  val fetchedWebsite = HttpService.getConnection(host, port).flatMap(_.get(myDesiredURL))
  val finalResult = fetchedWebsite.fold(e => s"<html>${e.getMessage}<>/html>", s => s)

  // for-comprehension
  val fetchedWebsite_v2 = for
    conn <- HttpService.getConnection(host, port)
    html <- conn.get(myDesiredURL)
  yield html


  def main(args: Array[String]): Unit = {
    println(Try(unsafeMethod()).map(_.length))
    println(stringLengthPure)
    println(stringLengthPure_v2)
    println(aSafeChain)
    println(fetchedWebsite)
    println(finalResult)
    println(fetchedWebsite_v2)
  }
}
