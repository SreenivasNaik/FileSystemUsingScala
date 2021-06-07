package com.sreenu.scala.opp.files

import com.sreenu.scala.opp.fileSystem.OurFileSystemException

class Directory(override val parentPath: String, override val name: String,val contents:List[DirEntry])
  extends DirEntry(parentPath,name ) {
  def removeEntry(entryName: String) =
    if(!hasEntry(entryName)) this
    else new Directory(parentPath,name,contents.filter(x=> !x.name.equals(entryName)))

  def isRoot: Boolean = parentPath.isEmpty

  override def isDirectory: Boolean = true

  override def isFile: Boolean = false

  def replaceEntry(entryName: String, newEntry: Directory): Directory =
    new Directory(parentPath,name,contents.filter(e=> !e.name.equals(entryName)):+newEntry)

  def findEntry(entryName: String):DirEntry = {
    def findEntryHelper(name:String,contentList:List[DirEntry]):DirEntry = {
      if (contentList.isEmpty) null
      else if ( contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name,contentList.tail)
    }
    findEntryHelper(entryName,contents)
  }


  def addEntry(newEntry: DirEntry):Directory = new Directory(parentPath,name,contents:+newEntry)


  def getAllFoldersInPath:List[String] = {
    path.substring(1).split(Directory.SEPARATOR).toList.filter(x=> !x.isEmpty)
  }

  override def asDiectory: Directory = this

  override def getType: String = "Directory"

  override def asFile: File = throw new OurFileSystemException("A directory can't be converted to file")

  def hasEntry(name: String): Boolean = findEntry(name)!=null

  def findDecendents(path: List[String]):Directory  =
    if(path.isEmpty) this
    else findEntry(path.head).asDiectory.findDecendents(path.tail)

  def findDecendents(relativePath: String):Directory  =
    if(relativePath.isEmpty) this
    else findDecendents(relativePath.split(Directory.SEPARATOR).toList)


}
object Directory{
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT:Directory = Directory.empty("","")

  def empty (parentPath:String,name:String) =
    new Directory(parentPath,name,List())
}
