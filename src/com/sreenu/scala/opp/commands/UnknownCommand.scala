package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State = state.setMessage("Command not found")
}
