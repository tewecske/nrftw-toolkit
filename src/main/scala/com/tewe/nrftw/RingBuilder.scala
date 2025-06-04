package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object RingBuilder {

  @JSImport("@find/**/RingBuilder.css", JSImport.Namespace)
  @js.native private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def ringComponentCompact(
      ringData: RingData,
      onSelect: RingData => Unit
  ): Element = {
    div(
      cls("ring-container-compact"),
      onClick --> { _ => onSelect(ringData) },
      div(
        cls("compact-ring-header"),
        img(
          cls("compact-ring-image"),
          src(ringData.imageSrc),
          alt("Ring Compact")
        ),
        div(
          cls("compact-ring-titles"),
          h3(cls(s"compact-ring-name item-type-${ringData.itemRarity}"), ringData.name),
          p(cls("compact-ring-type"), "Ring")
        )
      ),
      EnchantmentsBuilder.enchantmentsCompact(ringData.itemRarity, ringData.enchantments, ringData.enchantDownsides)
    )
  }

  def ringComponentFull(
      ringDataOptionVar: Var[Option[RingData]],
      ringErrorSignal: Signal[Boolean],
      showModalVar: Var[Boolean]
  ): Element = {

    
    div(
      cls("ring-big-container"),
      cls("x-hasError") <-- ringErrorSignal,
      onClick --> { _ =>
        showModalVar.set(true)
      },
      child <-- ringDataOptionVar.signal.map(_.fold {
        div(
          cls("ring-header"),
          div(
            cls("ring-titles"),
            h2(cls(s"ring-name"), "Select a Ring"),
            p(cls("ring-type"), "Ring")
          )
        )
        } { ringData =>
          
          div(
      div(
        cls("ring-header"),
        img(
          cls("ring-image"),
          src(ringData.imageSrc),
          alt("Plagued Ring")
        ),
        div(
          cls("ring-titles"),
          h2(cls(s"ring-name item-type-${ringData.itemRarity}"), ringData.name),
          p(cls("ring-type"), "Ring")
        )
      ),
      // div(
      //   cls("resistances-container"),
      //   div(
      //     cls("resistance"),
      //     div(
      //       cls("resistance-type"),
      //       span("⚡"),
      //       s"Electric Resistance",
      //     ),
      //     div(cls("resistance-value"), s"+10%")
      //   ),
      //   div(
      //     cls("resistance"),
      //     div(
      //       cls("resistance-type"),
      //       span("☣️"),
      //       s"Plague Resistance",
      //     ),
      //     div(cls("resistance-value"), s"+11%")
      //   )
      // ),
     EnchantmentsBuilder.enchantmentsFull(ringData.itemRarity, ringData.enchantments, ringData.enchantDownsides),
      // div(cls("item-quote"), ringData.description),
      // div(
      //   cls := "required-level",
      //   "Required Level: ",
      //   span(cls := "level-value", s"${ringData.requiredLevel}")
      // ),
      // div(cls("bottom-stats"),
      //   div(cls("durability"),
      //     span(cls("durability-icon"), "🗡"),
      //     span(cls("durability-value"),s"${ringData.durability}/${ringData.durability}")
      //   ),
      //   div(cls("weigth"),
      //     span(cls("weight-icon"), "⚖"),
      //     span(cls("weight-value"),s"${ingData.weight}")
      //   )
      // ),
    )
        })
    )

  }


  case class RingState(id: String)

  def createState(initState: Option[String]): Option[RingData] = {
    initState.filter(!_.isBlank).fold {
      None
      } { stringState =>
        println(s"RING: $stringState")
        rings.find(_.id == stringState)
      }
  }
}
