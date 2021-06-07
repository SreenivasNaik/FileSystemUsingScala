package com.sreenu.scala.opp.commands

import com.sreenu.scala.opp.fileSystem.State

trait Command {

  def apply(state:State):State

}
object Command{
  val MKDIR = "mkdir"
  def emptyCommand:Command = new Command {
    override def apply(state: State): State = state
  }

  def inCompleteCommand(name:String) : Command = new Command {
    override def apply(state: State): State = state.setMessage(name+": Incomplete Command")
  }
  def from(input: String):Command = {
    val tokens:Array[String] = input.split(" ")

    if(input.isEmpty || tokens.isEmpty) emptyCommand
    else if(MKDIR.equals(tokens(0))){
      if(tokens.length <2) inCompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    }
    else if("ls".equals(tokens(0))) new Ls
    else new UnknownCommand
  }
}
