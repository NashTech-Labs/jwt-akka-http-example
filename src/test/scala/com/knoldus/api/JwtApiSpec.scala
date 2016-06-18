package com.knoldus.api

/**
  * Created by narayan on 10/6/16.
  */

import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.{RawHeader, Authorization}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}

class JwtApiSpec extends FlatSpec with Matchers with ScalatestRouteTest {

  behavior of "Akka REST Api with JWT"

  it should "Able to login as admin role in system  " in {
    val usrName = Multipart.FormData.BodyPart.Strict("username", "narayan")
    val passwd = Multipart.FormData.BodyPart.Strict("password", "kumar")
    val responseOutput = """Data : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJuYXJheWFuIiwiYXVkIjoiYWRtaW4ifQ.uDNl3ekBvwo5Ob630dT5Lv2kyMOZEx2ULz10zRwY_wA successfully saved."""
    val formData = Multipart.FormData(usrName, passwd)
    Post(s"/login", formData) ~> JwtApi.routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe responseOutput
    }
  }

  it should "Able to login in system without role " in {
    val usrName = Multipart.FormData.BodyPart.Strict("username", "ramesh")
    val passwd = Multipart.FormData.BodyPart.Strict("password", "ranjan")
    val responseOutput = """Data : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyYW1lc2gifQ.QUsOimu5p1NuMHfLiufcPeYnodNVqljxfxQ_02bW1RI successfully saved."""
    val formData = Multipart.FormData(usrName, passwd)
    Post(s"/login", formData) ~> JwtApi.routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe responseOutput
    }
  }

  it should "not be Able to login  in system with  invalid credentials " in {
    val usrName = Multipart.FormData.BodyPart.Strict("username", "narayan")
    val passwd = Multipart.FormData.BodyPart.Strict("password", "kumar@123")
    val responseOutput = "login credentials are invalid"
    val formData = Multipart.FormData(usrName, passwd)
    Post(s"/login", formData) ~> JwtApi.routes ~> check {
      status shouldBe StatusCodes.Unauthorized
      responseAs[String] shouldBe responseOutput
    }
  }


  it should "Able to access jwt protected uri " in {
    val responseOutput = """user names are  (ramesh,ranjan,other,1235) """
    Get(s"/getUserDetail/1235").withHeaders(RawHeader("Authorization","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJyYW1lc2gifQ.QUsOimu5p1NuMHfLiufcPeYnodNVqljxfxQ_02bW1RI")) ~> JwtApi.routes ~> check {
      status shouldBe StatusCodes.OK
      responseAs[String] shouldBe responseOutput
    }
  }

}

