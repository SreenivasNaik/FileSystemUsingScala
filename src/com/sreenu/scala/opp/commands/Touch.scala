package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.{DirEntry, File}

class Touch(name:String) extends CreateEntry(name){
  override def createSpecificEntry(state: State): DirEntry =
    File.empty(state.wd.path,name)
}
