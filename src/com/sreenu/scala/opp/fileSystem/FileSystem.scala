package com.sreenu.scala.opp.fileSystem

import com.sreenu.scala.opp.commands.Command
import com.sreenu.scala.opp.files.Directory

import java.util.Scanner

object FileSystem extends App {
  val root = Directory.ROOT
  /* [1,2,3,4] => 0 (op) 1=>1
  1 (op) 2 =>3
  3 (op) 3 =>6
  6 (op) 4 => your last value = 10
  list(1,2,3,4).foldleft(0)((x,y)=>x+y))
  * */
  io.Source.stdin.getLines().foldLeft(State(root,root))((currentState,newLine)=>{
   // currentState.show
    val newState = Command.from(newLine).apply(currentState)
    newState.show
    newState
  })

  //var state = State(root,root)


  /*
  val scanner = new Scanner(System.in)
  while (true){

    state.show
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }*/


}
