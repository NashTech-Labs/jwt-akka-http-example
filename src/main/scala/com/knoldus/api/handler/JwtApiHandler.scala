package com.knoldus.api.handler

import java.sql.{Connection, DriverManager, Statement}
import scala.collection.mutable.ListBuffer

/**
  * Created by narayan on 8/6/16.
  */
trait JwtApiHandler {
  val jdbcDriverName = "com.mysql.jdbc.Driver"
  val connectionUrl = "jdbc:mysql://localhost/jwt"
  val userName = "root"
  val password = "root"
  Class.forName(jdbcDriverName)
  val con: Connection = DriverManager.getConnection(connectionUrl, userName, password)
  val stmt: Statement = con.createStatement

  def getAllUserName(): ListBuffer[String] = {
    val query = "select * from login ;"
    val rs = stmt.executeQuery(query)
    val result: ListBuffer[String] = new ListBuffer()
    while (rs.next()) {
      result.append(rs.getString(1))
    }
    result
  }

  def getLoginInfo(name: String): (String, String, String, Int) = {
    val query = s"select * from login where name ='${name}';"
    val rs = stmt.executeQuery(query)
    val result = if (rs.next()) {
      (rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4))
    } else {
      ("not found", "not found", "not found", 0)
    }
    result
  }

  def getUserInfoById(userId: Int): (String, String, String, Int) = {
    val query = s"select * from login where id =${userId};"
    val rs = stmt.executeQuery(query)
    val result = if (rs.next()) {
      (rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4))
    } else {
      ("not found", "not found", "not found", 0)
    }
    result
  }

}
