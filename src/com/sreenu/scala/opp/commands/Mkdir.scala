package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.{DirEntry, Directory}

class Mkdir(name:String) extends Command {

  override def apply(state: State): State = {
    val wd = state.wd
    if(wd.hasEntry(name)){
      state.setMessage("Entry: "+name+" already exist")
    } else if(name.contains(Directory.SEPARATOR)){
      state.setMessage(name+" must not contain separators")
    }else if(checkIlligal(name)){
      state.setMessage(name +" illigate entry name")
    }else{
      doMkdir(state,name)
    }
  }

  def checkIlligal(str: String):Boolean  = {
    name.contains(".")
  }
  def doMkdir(state: State,name: String):State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory ={
      /*
        someDir
        /a
        /b
        new /d
        =>> new someDir
        /a
        /b
        /d

      * */

      if(path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        println("Path:"+path)
        println("PathHead:"+path.head)
        println("PathHeadIsEmpty:"+path.head.isEmpty)
        println("oldRntry::"+currentDirectory.findEntry(path.head))
        val oldEntry = currentDirectory.findEntry(path.head).asDiectory
        currentDirectory.replaceEntry(oldEntry.name,updateStructure(oldEntry,path.tail,newEntry))

      }
    }


    val wd =state.wd

    // 1. All the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath
    // 2. create new directory entry in the wd

    val newDir = Directory.empty(wd.path,name)
    // 3. update the whole directory structure starting from root
      // directory structure is IMMUTABLE

    val newRoot = updateStructure(state.root,allDirsInPath,newDir)

    // 4. find new working direcotry INSTANCE given wd's full path, the new direcoty structure

    val newWd = newRoot.findDecendents(allDirsInPath)

    State(newRoot,newWd)
  }
}
