package com.knoldus.api

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

/**
  * Created by narayan on 10/6/16.
  */
object JwtHttpServer extends App with JwtApi {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  Http().bindAndHandle(routes, "localhost", 9000)
}
