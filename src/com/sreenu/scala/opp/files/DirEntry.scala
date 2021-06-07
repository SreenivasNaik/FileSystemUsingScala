package com.sreenu.scala.opp.files

abstract class DirEntry(val parentPath:String,val name:String) {

  def path:String = parentPath+Directory.SEPARATOR+name
  def asDiectory:Directory
  def getType:String
  def asFile:File
}
