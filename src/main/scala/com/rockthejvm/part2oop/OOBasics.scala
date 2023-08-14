package com.rockthejvm.part2oop

import sun.security.krb5.internal.AuthorizationData

object OOBasics {

  class Person(val name: String = "Khoi", age: Int = 23) { // constructor signature
    // fields
    val allCaps: String = name.toUpperCase()

    // methods
    def greet(name: String): String = {
      s"${this.name} says: $name"
    }

    // signature differs
    // OVERLOADING
    def greet(): String = {
      s"Hi everyone, my name is ${this.name}"
    }

    // aux contructor
//    def this(name: String) =
//      this(name, 0)
//
//    def this() =
//      this("Jane Doe")

  } //  constructor structure

  val aPerson: Person = new Person("John", 26)
  val john: String = aPerson.name // class parameter != field
  val johnYelling: String = aPerson.allCaps
  val johnSaysHiToKhoi: String = aPerson.greet("Khoi")
  val johnSaysHi: String = aPerson.greet()
  val genericPerson: Person = new Person()

  class Writer(val firstname: String, val surname: String, val yearOfBirth: Int) {
    def fullname(): String = {
      s"${this.firstname} ${this.surname}"
    }

  }

  class Novel(val name: String, val yearOfRelease: Int, val author: Writer) {

    def authorAge(): Int = {
      yearOfRelease - this.author.yearOfBirth
    }

    def isWrittenBy(author: Writer): Boolean = {
      author == this.author
    }

    def copy(newYearOfRelease: Int): Novel = {
      new Novel(this.name, newYearOfRelease, this.author)
    }

  }

  class Counter(val cnt: Int = 0) {
    def increment(): Counter = {
      new Counter(this.cnt + 1)
    }

    def decrement(): Counter = {
      new Counter(this.cnt - 1)
    }

    def increment(n: Int): Counter = {
      if (n <= 0) this
      else new Counter(this.cnt + n)
    }

    def decrement(n: Int): Counter = {
      if (n <= 0) this
      else new Counter(this.cnt - n)
    }

    def print(): Unit = {
      println(s"Current count is ${this.cnt}")
    }
  }

  def main(args: Array[String]): Unit = {

    val khoi: Writer = new Writer("Khoi Quang", "Nguyen", 2000)
    val otherKhoi: Writer = new Writer("Khoi Quang", "Nguyen", 2000)
    val newNovel: Novel = new Novel("Meaning of our lives", 2023, khoi)
    println(s"Full name of the author: ${khoi.fullname()}")
    println(s"Information about the novel: ${newNovel.name}, ${newNovel.yearOfRelease}, ${newNovel.author.firstname}")
    println(s"Author age at the time of novel publicaton: ${newNovel.authorAge()}")
    println(s"Is this novel written by otherKhoi?: ${newNovel.isWrittenBy(otherKhoi)}")
    println(s"second version of the novel is going to be published in ${newNovel.copy(2025).yearOfRelease}")
    

  }
}
