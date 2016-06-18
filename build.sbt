name := "jwt-akka-http-example"

version := "1.0"

scalaVersion := "2.11.8"
    
libraryDependencies ++= Seq(
"com.typesafe.akka" %% "akka-stream" % "2.4.7",
"com.typesafe.akka" %% "akka-http-core" % "2.4.7",
"com.typesafe.akka" %% "akka-http-experimental" % "2.4.7",
"io.igl" %% "jwt" % "1.2.0",
"mysql" % "mysql-connector-java" % "5.1.34",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.3",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

