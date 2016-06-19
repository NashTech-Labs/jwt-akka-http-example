package com.knoldus.api

import akka.actor.ActorSystem
import akka.http.scaladsl.model.Multipart.BodyPart
import akka.http.scaladsl.model.{HttpResponse, Multipart, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RequestContext, Route}
import akka.stream.ActorMaterializer
import com.knoldus.api.handler.{AuthenticationHandler, JwtApiHandler}

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration.DurationInt


trait JwtApi extends JwtApiHandler with AuthenticationHandler {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer

  val secretKey = "secret"

  val routes: Route =
    get {
      path("getAllUsersName" / "admin") { ctx: RequestContext =>
        val r: String = if (isVerifyWithRole(ctx.request, secretKey, "admin")) {
          val allUsersName: ListBuffer[String] = getAllUserName()
          s"user names are  ${allUsersName.toString()} "
        } else {
          "other users......"
        }
        ctx.complete(r)
      } ~
        path("getUserDetail" / IntNumber) { (userId: Int) =>
          ctx: RequestContext =>
            val r: String = if (isVerify(ctx.request, secretKey)) {
              val allUsersName: (String, String, String, Int) = getUserInfoById(userId)
              s"user names are  ${allUsersName.toString()} "
            } else {
              "other users......"
            }
            ctx.complete(r)
        }
    } ~
      post {
        path("login") {
          entity(as[Multipart.FormData]) { data =>
            complete {
              val extractedData: Future[Map[String, Any]] = data.parts.mapAsync[(String, Any)](1) {
                case data: BodyPart => data.toStrict(2.seconds).map(strict => data.name -> strict.entity.data.utf8String)
              }.runFold(Map.empty[String, Any])((map, tuple) => map + tuple)
              extractedData.map { data =>
                val userName = data.get("username").get.toString
                val password = data.get("password").get.toString
                val result = getLoginInfo(userName)
                val res = if (result._1 == userName && result._2 == password) {
                  val encodedToken = if (result._3 == "admin") {
                    createTokenWithRole(secretKey, userName, result._3)
                  } else {
                    createToken(secretKey, userName)
                  }
                  HttpResponse(StatusCodes.OK, entity = s"Data : $encodedToken successfully saved.")
                } else {
                  HttpResponse(StatusCodes.Unauthorized, entity = "login credentials are invalid")
                }
                res
              }
                .recover {
                  case ex: Exception => HttpResponse(StatusCodes.InternalServerError, entity = "Error in processing the multi part data")
                }
            }
          }
        }
      }
}

object JwtApi extends JwtApi {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

}