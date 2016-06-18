package com.knoldus.api.handler

import akka.http.scaladsl.model.HttpRequest
import io.igl.jwt._
import play.api.libs.json.{JsString, JsValue}

import scala.util.Try


case class Role(value: String) extends ClaimValue {
  override val field: ClaimField = Role
  override val jsValue: JsValue = JsString(value)
}

object Role extends ClaimField {
  override def attemptApply(value: JsValue): Option[ClaimValue] =
    value.asOpt[String].map(apply)

  override val name = "role"
}


trait AuthenticationHandler {


  def isVerifyWithRole(req: HttpRequest, secretKey: String, role: String): Boolean = {
    val result = getAuthToken(req)
    val res: Try[Jwt] = DecodedJwt.validateEncodedJwt(
      result, // An encoded jwt as a string
      secretKey, // The key to validate the signature against
      Algorithm.HS256, // The algorithm we require
      Set(Typ), // The set of headers we require (excluding alg)
      Set(Iss, Aud),
      iss = Some(Iss("narayan")), // The iss claim to require (similar optional arguments exist for all registered claims)
      aud = Some(Aud(role))
    )
    res.isSuccess
  }

  def isVerify(req: HttpRequest, secretKey: String): Boolean = {
    val result = getAuthToken(req)
    val res: Try[Jwt] = DecodedJwt.validateEncodedJwt(
      result, // An encoded jwt as a string
      secretKey, // The key to validate the signature against
      Algorithm.HS256, // The algorithm we require
      Set(Typ), // The set of headers we require (excluding alg)
      Set(Iss),
      iss = Some(Iss("ramesh")) // The iss claim to require (similar optional arguments exist for all registered claims)
    )
    res.isSuccess
  }

  def createTokenWithRole(secretKey: String, userName: String, role: String): String = {
    val jwt = new DecodedJwt(Seq(Alg(Algorithm.HS256), Typ("JWT")), Seq(Iss(userName), Aud(role)))
    jwt.encodedAndSigned(secretKey)
  }

  def createToken(secretKey: String, userName: String): String = {
    val jwt = new DecodedJwt(Seq(Alg(Algorithm.HS256), Typ("JWT")), Seq(Iss(userName)))
    jwt.encodedAndSigned(secretKey)
  }

  def getAuthToken(req: HttpRequest): String = {
    val AUTHORIZATION_KEYS: List[String] = List("Authorization", "HTTP_AUTHORIZATION", "X-HTTP_AUTHORIZATION", "X_HTTP_AUTHORIZATION")
    def authorizationKey: Option[String] = AUTHORIZATION_KEYS.find(req.getHeader(_) != null)
    val result = if (authorizationKey.isDefined && authorizationKey.get == "Authorization") {
      req.getHeader("Authorization").get().value()
    } else {
      "request have not authorize token"
    }
    result
  }

}
