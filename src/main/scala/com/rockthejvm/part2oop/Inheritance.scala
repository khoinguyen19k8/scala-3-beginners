package com.rockthejvm.part2oop

import sun.misc.Cache

import java.util.jar.Attributes.Name

object Inheritance {

  class Animal {
    val creatureType = "Wild"
    def eat(): Unit = println("nomnomnom")
  }

  class Cat extends Animal {
    def crunch() = {
      eat()
      println("crunch, crunch")
    }
  }

  class Person(val name: String, age: Int) {
    def this(name: String) = this(name, 0)
  }

  class Adult(override val name: String, age: Int, idCard: String) extends Person(name, age) // must specify super-constructor

  // overriding

  class Dog extends Animal {
    override val creatureType = "Domestic"
    override def eat(): Unit = println("mmm, I like this bone")

    // popular overridable method
    override def toString: String = "a dog"
  }

  val cat: Cat = new Cat
  val dog: Dog = new Dog

  // subtype polymorphism
  val newDog: Animal = new Dog

  // overloading vs overriding
  class Crocodile extends Animal {
    override val creatureType: String = "Wild"
    override def eat(): Unit = println("I can eat anything, I am a croc")

    // overloading: multiple methods with the same name, different signature
    // different signature = different argument list + different return type (optional)
    def eat(animal: Animal): Unit = println("I'm eating this poor fella")
    def eat(dog: Dog, person: Person): Unit = println("I am eating a dog and a human")
    def eat(person: Person, dog: Dog): Unit = println("I am eating a human and a dog")
  }
  val khoi = new Person("Khoi", 22)
  val croc = new Crocodile
  def main(args: Array[String]): Unit = {
    croc.eat(khoi, dog)
    croc.eat(dog, khoi)
    // croc.eat(person = khoi, dog = dog) // will throw an error
  }
}
