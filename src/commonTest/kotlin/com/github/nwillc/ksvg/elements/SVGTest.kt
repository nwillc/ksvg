/*
 * Copyright (c) 2020, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg.elements

import com.github.nwillc.ksvg.RenderMode
import com.github.nwillc.ksvg.elements.ATest.Fixtures.QUOTE
import com.github.nwillc.ksvg.elements.CIRCLETest.Fixtures.COLOR
import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.test.Test
import kotlin.test.assertEquals
import com.github.nwillc.ksvg.escapeHTML
import kotlin.random.Random

class SVGTest : HasSvg() {

    @Test
    fun svgTag() {
        assertEquals(svg.toString(), "<svg/>\n")
    }

    @Test
    fun svgTagWithAttr() {
        val box = "${Random.nextInt(0, 10)} ${Random.nextInt(0, 10)} " +
            "${Random.nextInt(10, 100)} ${Random.nextInt(10, 100)}"
        svg.viewBox = box
        assertEquals(svg.toString(), "<svg viewBox=\"$box\"/>\n")
    }

    @Test
    fun rectWithDimensions() {
        val widthValue = Random.nextInt(1, 200).toString()
        val heightValue = Random.nextInt(1, 200).toString()

        svg.rect {
            width = widthValue
            height = heightValue
        }

        assertEquals(svg.toString(), "<svg>\n<rect height=\"$heightValue\" width=\"$widthValue\"/>\n</svg>\n")
    }

    @Test
    fun textWithoutBody() {
        svg.text {
        }
        assertEquals((svg.children[0] as TEXT).body, "")
    }

    @Test
    fun textWithBody() {
        val msg = QUOTE

        svg.text {
            body = msg
        }

        assertEquals((svg.children[0] as TEXT).body, msg.escapeHTML())
    }

    @Test
    fun svgMultipleTags() {
        val xValue = Random.nextInt(1, 100).toString()
        val yValue = Random.nextInt(1, 100).toString()
        val bodyValue = QUOTE

        svg.rect {
            x = xValue
            y = yValue
        }
        svg.text {
            body = bodyValue
        }

        assertEquals(
            svg.toString(),
            "<svg>\n<rect x=\"$xValue\" y=\"$yValue\"/>\n<text>${bodyValue.escapeHTML()}</text>\n</svg>\n"
        )
    }

    @Test
    fun multipleTags() {
        val msg = QUOTE

        svg.rect {}

        svg.text {
            body = msg
        }

        assertEquals(svg.toString(), "<svg>\n<rect/>\n<text>${msg.escapeHTML()}</text>\n</svg>\n")
    }

    @Test
    fun rawAttributes() {
        val noun = "foo"
        val value = "bar"

        svg.attributes[noun] = value

        assertEquals(svg.toString(), "<svg $noun=\"$value\"/>\n")
    }

    @Test
    fun fillAttribute() {
        val color = COLOR

        svg.text {
            fill = color
        }

        assertEquals(svg.toString(), "<svg>\n<text fill=\"$color\"/>\n</svg>\n")
    }

    @Test
    fun svgFileMode() {
        StringBuilder().apply {
            svg.render(this, RenderMode.INLINE)
            assertEquals(this.toString(), "<svg/>\n")
        }

        StringBuilder().apply {
            svg.render(this, RenderMode.FILE)
            assertEquals(
                this.toString(),
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<svg xmlns=\"http://www.w3.org/2000/svg\"/>\n"
            )
        }
    }

//    @Test
//    fun `format rich svg vs persisted file`() {
//        val svg = SVG.svg {
//            width = "200"
//            height = "200"
//            rect {
//                title {
//                    body = "A Blue Rectangle"
//                }
//                x = "50"
//                y = "50"
//                width = "20"
//                height = "10"
//                fill = "blue"
//            }
//            text {
//                body = "label"
//                x = "100"
//                y = "100"
//            }
//            circle {
//                cx = "100"
//                cy = "150"
//                r = "20"
//                fill = "red"
//                stroke = "blue"
//                strokeWidth = "2"
//            }
//            line {
//                x1 = "0"
//                y1 = "0"
//                x2 = "40"
//                y2 = "40"
//                strokeWidth = "3"
//                stroke = "black"
//            }
//        }
//
//        javaClass.getResourceAsStream("/example1.svg").use { resource ->
//            InputStreamReader(resource).use { input ->
//                val text = input.readText()
//                StringWriter().use { writer ->
//                    svg.render(writer, SVG.RenderMode.FILE)
//                    assertEquals(writer.toString(), text)
//                }
//            }
//        }
//    }
}
