import org.scalajs.linker.interface.ModuleSplitStyle
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

val isDev = settingKey[Boolean]("Development mode flag")

lazy val root = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin, BuildInfoPlugin)
  .settings(
    name := "nrftw-toolkit",
    scalaVersion := "3.5.2",
    scalacOptions ++= Seq("-encoding", "utf-8", "-deprecation", "-feature"),
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
    },
    isDev := false,
    buildInfoKeys := Seq[BuildInfoKey](
      "isDev" -> isDev.value
    ),
    buildInfoPackage := "com.tewe.nrftw",
    libraryDependencies ++=
      Seq(
        "org.scala-js" %%% "scalajs-dom" % "2.8.0",
        "com.raquo" %%% "laminar" % "17.2.0",
      ),
  )
