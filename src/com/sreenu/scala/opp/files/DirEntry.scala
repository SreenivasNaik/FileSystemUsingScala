package com.sreenu.scala.opp.files

abstract class DirEntry(val parentPath:String,val name:String) {

  def path:String = {
    val separatorIfneccesory =
      if(Directory.ROOT_PATH.equals(parentPath)) ""
      else Directory.SEPARATOR
    parentPath+separatorIfneccesory+name}
  def asDiectory:Directory
  def getType:String
  def asFile:File

  def isDirectory:Boolean
  def isFile:Boolean
}
