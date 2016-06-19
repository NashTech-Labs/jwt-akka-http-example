# jwt-akka-http-example
Integrate JWT with Akka HTTP to handle Authentication and Authorization service.

-----------------------------------------------------------------------
### Dependency
-----------------------------------------------------------------------
The JSON Web Token (JWT) is distributed using Maven Central so it can be easily added as a library dependency in your Akka HTTP Application's SBT build scripts, as follows:

```
libraryDependencies += "io.igl" %% "jwt" % "1.2.0"
```

-----------------------------------------------------------------------
#### What is JSON Web Token (JWT)?
-----------------------------------------------------------------------
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. 
This information can be verified and trusted because it is digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA.

For more details please check [JWT Example Blog](https://blog.knoldus.com/2016/06/18/integrate-jwt-with-akka-http/)

-----------------------------------------------------------------------
### Now Play
-----------------------------------------------------------------------
* Clone the project into local system : `$ git clone git@github.com:anand-singh/jwt-akka-http-example.git` 
* Akka requires that you have [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) or later installed on your machine.
* Install Activator or SBT if you do not have it already
* Execute `sbt clean compile` to build the product
* Execute `sbt run` to execute the product
* jwt-akka-http-example should now be accessible at localhost:9000

-----------------------------------------------------------------------
### References
-----------------------------------------------------------------------
* [Akka HTTP](http://doc.akka.io/docs/akka/2.4.7/scala/http/index.html)
* [JSON Web Token (JWT)](https://jwt.io/)
* [Scala](http://scala-lang.org/)

