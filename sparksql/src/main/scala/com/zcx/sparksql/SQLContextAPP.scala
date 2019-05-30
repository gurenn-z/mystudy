package com.zcx.sparksql

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * SQLContext 的使用(1.6版本)
  * 测试数据在虚拟机服务器上，idea在本地Windows上
  */
object SQLContextAPP {

  def main(args: Array[String]): Unit = {

//    val path = "F:/MyProject/data/sparksql/people.json"
    val path = args(0)

    // 1、创建相应的Context
    val sparkConf = new SparkConf()

    // 在测试和生产环境中，AppName 和 Master 是通过脚本来进行指定的
//    sparkConf.setAppName("SQLContextAPP").setMaster("local[2]")

    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    // 2、相关逻辑处理：处理json数据
    val people = sqlContext.read.format("json").load(path)
    people.printSchema()
    people.show()

    // 3、关闭资源

  }

}
