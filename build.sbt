enablePlugins(ScalaJSPlugin)
enablePlugins(ScalaJSBundlerPlugin)
//enablePlugins(WorkbenchPlugin)

name := "edgar_prelim_ui"
version := "0.1"
scalaVersion := "2.12.6"

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.6"
libraryDependencies += "org.querki" %%% "jquery-facade" % "1.2"
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.6.3" % "test"
libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "1.4.2"
libraryDependencies += "com.github.japgolly.scalajs-react" %%% "extra" % "1.4.2"

npmDependencies in Compile ++= Seq(
    "core-js" -> "2.6.9",
    "react" -> "16.8.6",
    "react-dom" -> "16.8.6",
    "jquery" -> "1.9.1",
    "jsdom" -> "11.12.0",
    "bootstrap" -> "4.3.1",
    "react-bootstrap" -> "1.0.0-beta.8",
)

skip in packageJSDependencies := false
testFrameworks += new TestFramework("utest.runner.Framework")

jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()