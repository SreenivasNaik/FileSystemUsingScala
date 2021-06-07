package com.sreenu.scala.opp.files

import com.sreenu.scala.opp.fileSystem.OurFileSystemException

class File(override val parentPath: String,override val name: String,val conents:String)
  extends DirEntry(parentPath ,name ) {
  def setConents(newContent: String): File = new File(parentPath,name,newContent)

  def appendConents(newContent: String): File = setConents(conents+"\n"+newContent)

  override def asDiectory: Directory = throw new OurFileSystemException("A file can't be converted as a directory")

  override def getType: String = "File"

  override def asFile: File = this

  override def isDirectory: Boolean = false

  override def isFile: Boolean = true
}

object File{
  def empty(parentPath:String,name:String) :File = new File(parentPath,name,"")
}
