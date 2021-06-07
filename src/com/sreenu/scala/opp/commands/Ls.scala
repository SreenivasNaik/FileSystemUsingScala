package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.DirEntry

class Ls extends Command {

  def createNiceOut(contents: List[DirEntry]):String = {
    if(contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name+"["+entry.getType+"]\n"+createNiceOut(contents.tail)
    }
  }

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val niceOutput = createNiceOut(contents)
    state.setMessage(niceOutput)
  }
}
