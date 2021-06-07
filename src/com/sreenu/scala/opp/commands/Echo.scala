package com.sreenu.scala.opp.commands
import com.sreenu.scala.opp.fileSystem.State
import com.sreenu.scala.opp.files.{Directory, File}

import scala.annotation.tailrec

class Echo(args:Array[String]) extends Command {
  override def apply(state: State): State = {

    /*
    *  if no args , state
    *  else if just one arg, print to console
    *   else if multiple orgs
    *     operator =next to last argument
    *   if >
    *   echo to file - may create file if not present
    *   if >>
    *     append to a file
    *   else just echo everything
    * */

    if(args.isEmpty) state
    else if(args.length == 1) state.setMessage(args(0))
    else{
      val operator = args(args.length -2)
      val filename = args(args.length-1)
      val contents = createContent(args,args.length-2)
      if(">>".equals(operator))
        doEcho(state,contents,filename,append = true)
      else if(">".equals(operator))
        doEcho(state,contents,filename,append = false)
      else
        state.setMessage(createContent(args,args.length))
    }
  }
  def getRootAfterEcho(currentDirectory:Directory,path:List[String],content:String,append:Boolean):Directory ={
    /*
    *  if( path is empty = fail currentDirecoty
    * else if no more things to explore == path.tail.isEmpty
    *   find the file to create/add content to
    *   if file not found create file
    *   else if the entry os actually a directory fail
    *   else
    *     replace or append conent to file
    *     replace the entry with filename with new File name
    *
    * else
    *   find the next directory to navigate
    *   call gRAE recurssivly
    *
    * if recursive call failed , fail
    * else replace entry with new direcoty after the recursive call
    * */

    if(path.isEmpty) currentDirectory
    else if(path.tail.isEmpty){
      val dirEntry = currentDirectory.findEntry(path.head)
      if(dirEntry ==null) currentDirectory.addEntry(new File(currentDirectory.path,path.head,content))
      else if(dirEntry.isDirectory) currentDirectory
      else{
        if( append) currentDirectory.replaceEntry(path.head,dirEntry.asFile.appendConents(content))
        else currentDirectory.replaceEntry(path.head,dirEntry.asFile.setConents(content))
      }
    } else{
        val nextDir = currentDirectory.findEntry(path.head).asDiectory
      val newNextDir = getRootAfterEcho(nextDir,path.tail,content,append)
      if (newNextDir == nextDir)currentDirectory
      else currentDirectory.replaceEntry(path.head,newNextDir)
      }
    }

  def doEcho(state: State, content: String, fileName: String,append:Boolean):State = {
    if(fileName.contains(Directory.SEPARATOR))
      state.setMessage("Echo: filename must not contain separator")
    else {
        val newRoot :Directory = getRootAfterEcho(state.root,state.wd.getAllFoldersInPath:+fileName,content,append)
        if(newRoot == state.root)
            state.setMessage(fileName+": no such file")
        else
          State(newRoot,newRoot.findDecendents(state.wd.getAllFoldersInPath))
    }
  }
  // TOP index is not NON-INCLUSIVE
  def createContent(args: Array[String], topIndex: Int):String = {
    @tailrec
    def createConentHelper(currentIndex:Int,accumalator:String):String ={
      if(currentIndex >= topIndex) accumalator
      else createConentHelper(currentIndex+1,accumalator+" "+args(currentIndex))
    }
    createConentHelper(0,"")
  }
}
