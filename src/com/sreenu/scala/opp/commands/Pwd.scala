package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State

class Pwd extends Command {
  override def apply(state: State): State = {
    state.setMessage(state.wd.path)
  }
}
