package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.{DirEntry, Directory}

class Mkdir(name:String) extends CreateEntry(name ) {
  override def createSpecificEntry(state: State): DirEntry =
    Directory.empty(state.wd.path,name)

}
