package com.sreenu.scala.opp.files

import com.sreenu.scala.opp.fileSystem.OurFileSystemException

class File(override val parentPath: String,override val name: String,conents:String)
  extends DirEntry(parentPath ,name ) {
  override def asDiectory: Directory = throw new OurFileSystemException("A file can't be converted as a directory")

  override def getType: String = "File"

  override def asFile: File = this
}

object File{
  def empty(parentPath:String,name:String) :File = new File(parentPath,name,"")
}
