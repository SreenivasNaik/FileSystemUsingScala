package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.{DirEntry, Directory}

import scala.annotation.tailrec

class Cd (dir:String) extends Command {
  override def apply(state: State): State = {
    /*
    *  cd /something/someOther/
    * */
    // Find the root
    val root = state.root
    val wd = state.wd
    // 2 . find the absolute path of the directory I want to cd to
    val absolutePath = {
      if(dir.startsWith(Directory.SEPARATOR)) dir
      else if(wd.isRoot) wd.path+dir
      else wd.path+Directory.SEPARATOR+dir
    }
    //3 . find the directory to cd to , given path

    val distinationDir = doFindEntry(root,absolutePath)
    //4 . change the state given new dir
    if(distinationDir == null || !distinationDir.isDirectory)
      state.setMessage(dir+" : No such Directory")
    else
      State(root,distinationDir.asDiectory)
  }
  def doFindEntry(root: Directory, path: String): DirEntry ={
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]):DirEntry = {
      if(path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if(nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDiectory,path.tail)
      }
    }

    // 1. tokens
    val tokens:List[String] = path.substring(1).split(Directory.SEPARATOR).toList
    // 2 . Navigate
    findEntryHelper(root,tokens)
  }
}
