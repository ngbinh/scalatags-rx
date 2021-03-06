package scalatags.rx

import org.scalajs.dom.Element
import rx._
import utest._
import utest.framework.TestSuite

import scalatags.JsDom.all._
import scalatags.rx.TestUtils._
import scalatags.rx.all._

object RxNodeInstancesSuite extends TestSuite {

  val tests = TestSuite {
    "text node" - {
      val c = Var("a")
      "Var" - {
        val node = span(c).render
        testRx(c, node.textContent, "a", "b")
      }
      "Rx" - {
        val node = span(c.rx).render
        testRx(c, node.textContent, "a", "b")
      }
    }
    "text node join" - {
      val c = Var("")
      val d = Var("")
      val node = span(c, d).render
      assert(node.textContent == "")
      d() = "x"
      c() = "y"
      assert(node.textContent == "yx")
    }
    "child node" - {
      val c = Var[Element](div().render)
      "Var" - {
        val node = div(c).render
        testRx(c, node.innerHTML, "<div/>", span().render -> "<span/>")
      }
      "Rx" - {
        val node = div(c.rx).render
        testRx(c, node.innerHTML, "<div/>", span().render -> "<span/>")
      }
    }
    "child node sequence" - {
      val c = Var[Vector[Element]](Vector(div().render))
      "Var" - {
        val node = div(c).render
        testRx(c, node.innerHTML, "<div/>", Vector(span().render) -> "<span/>")
      }
      "Rx" - {
        val node = div(c.rx).render
        testRx(c, node.innerHTML, "<div/>", Vector(span().render) -> "<span/>")
      }
    }
    "child node sequences join" - {
      val c = Var[Vector[Element]](Vector())
      val d = Var[Vector[Element]](Vector())
      val node = div(c, d).render
      "first to last" - {
        c() = Vector(div().render)
        d() = Vector(span().render)
        assert(node.innerHTML == "<div/><span/>")
      }
      "last to first" - {
        d() = Vector(span().render)
        c() = Vector(div().render)
        assert(node.innerHTML == "<div/><span/>")
      }
    }
  }
}
