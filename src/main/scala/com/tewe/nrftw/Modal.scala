package com.tewe.nrftw

import com.raquo.laminar.api.L._
import org.scalajs.dom
import com.tewe.nrftw.RingData
import com.tewe.nrftw.RingBuilder.ringComponentCompact

object Modal {
  def apply(
      showModalVar: Var[Boolean],
      items: List[RingData],
      onItemSelected: RingData => Unit
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
          children <-- Val(items).map(
            _.map(ringData =>
              ringComponentCompact(ringData, selectedRing => {
                onItemSelected(selectedRing)
                showModalVar.set(false) // Close modal on item selection
              })
            )
          )
        )
      )
    )
  }
}

