package com.sreenu.scala.opp.commands

import com.sreenu.scala.opp.fileSystem.State

trait Command {

  def apply(state:State):State

}
object Command{

  def from(input: String):Command = new UnknownCommand
}
