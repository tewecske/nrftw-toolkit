package com.tewe.nrftw

import com.raquo.laminar.api.L._
import org.scalajs.dom
import com.tewe.nrftw.RingData
import com.tewe.nrftw.RingBuilder.ringComponentCompact
import com.tewe.nrftw.GemsBuilder.gemComponentCompact
import com.tewe.nrftw.CompactComponent.compactComponent
import com.tewe.nrftw.RunesBuilder.runeComponentCompact

object Modal {
  def gemsModal(
    itemSlot: ItemSlot,
    showModalVar: Var[Boolean],
    items: List[Gem],
    onItemSelected: Gem => Unit,
  ): Element = {
    val isVisibleVar = Var(false)

    showModalVar --> { value =>
      println(s"Modal showSignal triggered: $value")
    }

    div(
      cls("modal-overlay"),
      cls("is-visible") <-- showModalVar.signal,
      onClick.stopPropagation --> { ev =>
        if (ev.target == ev.currentTarget) {
          showModalVar.set(false)
        }
      },
      div(
        cls("modal-content"),
        h3(cls("modal-title"), "Select a Gem"),
        div(
          cls("compact-gems-grid"),
          compactComponent(
            "rune",
            onClick --> { _ =>
              onItemSelected(null)
              showModalVar.set(false)
            },
            "/images/icon-cancel.svg",
            div(p(cls("compact-gem-effect"), "No Gem")),
          ),
          children <--
            Val(items).map(
              _.filter(_.gemEffects.exists(_.itemSlot == itemSlot))
                .map(gem => {
                  gemComponentCompact(
                    itemSlot,
                    gem,
                    selectedGem => {
                      onItemSelected(selectedGem)
                      showModalVar.set(false)
                    },
                  )
                })
            ),
        ),
      ),
    )
  }

  def runesModal(
    weaponTypeIdVar: Var[String],
    showModalVar: Var[Boolean],
    items: List[Rune],
    onItemSelectedVar: Var[Rune => Unit],
    // onItemSelected: Rune => Unit
  ): Element = {
    val isVisibleVar = Var(false)

    div(
      cls("modal-overlay"),
      cls("is-visible") <-- showModalVar.signal,
      onClick.stopPropagation --> { ev =>
        if (ev.target == ev.currentTarget) {
          showModalVar.set(false)
        }
      },
      div(
        cls("modal-content"),
        h3(cls("modal-title"), "Select a Rune"),
        div(
          cls("compact-runes-grid"),
          compactComponent(
            "rune",
            onClick --> { _ =>
              onItemSelectedVar.now()(null)
              showModalVar.set(false)
            },
            "/images/icon-cancel.svg",
            div(p(cls("compact-rune-name"), "No Rune")),
          ),
          children <--
            weaponTypeIdVar
              .signal
              .map { weaponTypeId =>
                items
                  .filter(_.weaponTypes.exists(_.id == weaponTypeId))
                  .map(rune => {
                    runeComponentCompact(
                      rune,
                      selectedRune => {
                        onItemSelectedVar.now()(selectedRune)
                        showModalVar.set(false)
                      },
                    )
                  })
              },
        ),
      ),
    )
  }

  def apply(
    showModalVar: Var[Boolean],
    items: List[RingData],
    onItemSelected: RingData => Unit,
  ): Element = {
    val isVisibleVar = Var(false)

    showModalVar --> { value =>
      println(s"Modal showSignal triggered: $value")
    }

    div(
      cls("modal-overlay"),
      cls("is-visible") <-- showModalVar.signal,
      onClick.stopPropagation --> { ev =>
        if (ev.target == ev.currentTarget) {
          showModalVar.set(false)
        }
      },
      div(
        cls("modal-content"),
        h3(cls("modal-title"), "Select a Ring"),
        div(
          cls("compact-rings-grid"),
          children <--
            Val(items).map(
              _.map(ringData => {
                ringComponentCompact(
                  ringData,
                  selectedRing => {
                    onItemSelected(selectedRing)
                    showModalVar.set(false) // Close modal on item selection
                  },
                )
              })
            ),
        ),
      ),
    )
  }
}
