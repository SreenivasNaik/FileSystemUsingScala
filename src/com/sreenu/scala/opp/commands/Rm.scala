package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.Directory

class Rm (name:String) extends Command {
/*
*  /a =>["a"]
*
*
* */
  def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
    if(path.isEmpty) currentDirectory
    else if(path.tail.isEmpty) currentDirectory.removeEntry(path.head)
    else{
      val nextDir = currentDirectory.findEntry(path.head)
      if(!nextDir.isDirectory) currentDirectory
      else {
        val newNextDiectory = rmHelper(nextDir.asDiectory,path.tail)
        if(newNextDiectory == nextDir) currentDirectory
        else currentDirectory.replaceEntry(path.head,newNextDiectory)
      }
    }
  }

  def doRm(state: State, absolutePath: String): State = {
    // 4 .find entry to remove

    // 5. update the structure like we do for mkdir
    val tokens = absolutePath.substring(1).split(Directory.SEPARATOR).toList
    val newRoot:Directory = rmHelper(state.root,tokens)
    if(newRoot == state.root)
      state.setMessage(absolutePath+" : no such file or directory")
    else
      State(newRoot,newRoot.findDecendents(state.wd.path.substring(1)))
  }

  override def apply(state: State): State = {
    // 1 . get working directory
    val wd = state.wd
    // 2. get absolute path
    val absolutePath ={
      if (name.startsWith(Directory.SEPARATOR)) name
      else if( wd.isRoot) wd.path+name
      else wd.path+Directory.SEPARATOR+name
    }
    // 3. do some checks
    if(Directory.ROOT_PATH.equals(absolutePath))
        state.setMessage("Not supported")
    else
      doRm(state,absolutePath)

  }
}
