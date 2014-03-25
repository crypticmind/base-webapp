resolvers += "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/"

resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.5.1")

addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "0.7.1")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.2.1")
