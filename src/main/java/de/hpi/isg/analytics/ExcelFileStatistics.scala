package de.hpi.isg.analytics

import java.io.{BufferedWriter, File, FileWriter}

import de.hpi.isg.utils.FileUtils
import info.debatty.java.stringsimilarity.NormalizedLevenshtein
import smile.clustering.{DBSCAN, dbscan}

/**
  * This class represents the various statistics of input excel files.
  *
  * @author Lan Jiang
  * @since 2019-08-14
  */
class ExcelFileStatistics(val inputFolderPath: String) {

  def fileNameStatistics(): Unit = {
    val d = new File(inputFolderPath)
    val inputFiles = if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      List[File]()
    }

    /**
      * cross product of two lists
      * @param xs
      * @tparam X
      */
    implicit class Crossable[X](xs: Traversable[X]) {
      def cross[Y](ys: Traversable[Y]): Traversable[(X, Y)] = {
        for {x <- xs; y <- ys} yield (x, y)
      }
    }

    val excelFileNames = inputFiles.filter(file => !FileUtils.shouldNotProcess(file))
            .map(file => {
              val fileName = file.getName
              val splits = fileName.split("@")
              val excelFileName = splits(0)
              val sheetName = splits(1).split(".csv")(0)
              excelFileName
            }).distinct

    val levenshtein = new NormalizedLevenshtein

    val matrix = excelFileNames.map(masterfileName => {
      excelFileNames.map(guestFileName => levenshtein.similarity(masterfileName, guestFileName)).toArray
    }).toArray

    val clusters: DBSCAN[Array[Double]] = dbscan(matrix, 2, 1)
    println(clusters.getNumClusters)

    val fileNameByLabel = excelFileNames.toArray.zip(clusters.getClusterLabel)
            .groupBy(_._2).map(pair => (pair._1, pair._2.map(labeledPair => labeledPair._1)))

    fileNameByLabel.foreach(pair => {
      val groupFileName = ExcelFileStatistics.outputFolderPath + "clusters/" + pair._1 + ".txt"
      val bw = new FileWriter(groupFileName)
      pair._2.foreach(fileName => bw.write(fileName + "\n"))
      bw.close()
    })

//    val result = excelFileNames.cross(excelFileNames)
////            .filter(tuple => tuple._1 != tuple._2)
//            .map(tuple => {
//              (tuple, levenshtein.similarity(tuple._1, tuple._2))
//            }).toArray.sortBy(_._2)(Ordering[Double].reverse)
//
//    val file = new File("file-name-similarity.txt");
//    val bufferedWriter = new BufferedWriter(new FileWriter(file))
//
//    result.filter(tuple => tuple._2 > 0.8).foreach(tuple => {
//      bufferedWriter.write("[" + tuple._1._1 + "\t" + tuple._1._2 + "]" + "\t" + tuple._2)
//      bufferedWriter.newLine()
//    })
//    bufferedWriter.close()
  }
}

object ExcelFileStatistics {

  private val outputFolderPath: String = "/Users/Fuga/Documents/hpi/projects/comment-detection/data/clusteredFileNames/"

  def main(args: Array[String]): Unit = {
    val excelFileStatistics = new ExcelFileStatistics("/Users/Fuga/Documents/hpi/code/data-downloader/data_excel_uk_converted")
    excelFileStatistics.fileNameStatistics()
  }
}
