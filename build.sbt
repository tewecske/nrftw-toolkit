import org.scalajs.linker.interface.ModuleSplitStyle
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

lazy val isDevMode = settingKey[Boolean](
  "True if fastLinkJS is used, false if fullLinkJS is used."
)

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "nrftw-toolkit",
    scalaVersion := "3.5.2",
    scalacOptions ++= Seq("-encoding", "utf-8", "-deprecation", "-feature"),
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
    },
    libraryDependencies ++=
      Seq(
        "org.scala-js" %%% "scalajs-dom" % "2.8.0",
        "com.raquo" %%% "laminar" % "17.2.0",
      ),
    fastJS := {
      isDevMode := true
      Compile / sourceGenerators +=
        Def
          .task {
            val file = (Compile / sourceManaged).value / "com" / "tewe" /
              "nrftw" / "BuildConfig.scala"
            IO.write(
              file,
              s"""package com.tewe.nrftw
object BuildConfig {
  val isDev: Boolean = ${isDevMode.value}
}
""",
            )
            Seq(file)
          }
          .taskValue
      (Compile / fastLinkJS).value
    },
    fullJS := {
      isDevMode := false
      Compile / sourceGenerators +=
        Def
          .task {
            val file = (Compile / sourceManaged).value / "com" / "tewe" /
              "nrftw" / "BuildConfig.scala"
            IO.write(
              file,
              s"""package com.tewe.nrftw
object BuildConfig {
  val isDev: Boolean = ${isDevMode.value}
}
""",
            )
            Seq(file)
          }
          .taskValue
      (Compile / fullLinkJS).value
    },
  )
lazy val fastJS = taskKey[Unit]("Runs fastLinkJS and devMode true")
lazy val fullJS = taskKey[Unit]("Runs fullLinkJS and devMode false")
