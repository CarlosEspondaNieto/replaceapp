package com.datio

import java.io.{BufferedReader, File, FileReader, FileWriter}
import java.nio.file.{Files, Paths, StandardCopyOption}


import scala.io.Source
import java.io.PrintWriter
import scala.collection.mutable.ListBuffer

object App{
  var f2: File = null
  var fileName: String = null
  var listPaths= ListBuffer[String]()
  var finalList:List[String]=null


  def main(args: Array[String]): Unit = {
      val file =  new File ("/home/cesponda/Documentos/QATeam/unified_testing/testAT/src/test/resources/filter")
      recursiveListFiles(file)
      for (fileName <- finalList){
        modifyFile(fileName)
      }
  }

  def modifyFile(fileName: String) ={
    f2 = new File("/tmp/abc.txt") // Temporary File
    val w = new PrintWriter(f2)

    val regex =  "datio-generic-snapshot/qa/[a-z]{2}/[a-z]{4}/conf/kirby"
    val  replace_schema = "\\$\\{SCHEMAS_REPO\\}/qa"
    Source.fromFile(fileName).getLines
      .map { line =>
      line.replaceAll(regex, replace_schema)}
        .foreach(line => w.println(line))
    w.close()
    moveRenameFile(f2.toString,fileName)
  }

  def moveRenameFile(source: String, destination: String): Unit = {
      Files.move(
      Paths.get(source),
      Paths.get(destination),
      StandardCopyOption.REPLACE_EXISTING
    )
  }

  def recursiveListFiles(f: File):List[String]={
    var listBuffer= new ListBuffer[String]()
    for( fichero:File <- f.listFiles()){
      if(fichero.isDirectory()){
        recursiveListFiles(fichero)
      }
      else {
        if(fichero.isFile && fichero.toString.endsWith("paths.properties")){
          listPaths += fichero.getAbsolutePath
          //println(listPaths)
          }
      }
    }
    finalList=listPaths.toList
    finalList
  }

}

