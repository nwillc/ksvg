/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
 * WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL
 * THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.ksvg.elements

import com.github.javafaker.Faker
import com.github.nwillc.ksvg.RenderMode
import com.github.nwillc.ksvg.testing.HasSvg
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import com.github.nwillc.ksvg.escapeHTML

class SVGTest : HasSvg() {
    private val faker = Faker()

    @Test
    fun `format of svg tag`() {
        assertThat(svg.toString()).isEqualTo("<svg/>\n")
    }

    @Test
    fun `format of svg tag with attr`() {
        val box = "${faker.number().numberBetween(0, 10)} ${faker.number().numberBetween(0, 10)} " +
            "${faker.number().numberBetween(10, 100)} ${faker.number().numberBetween(10, 100)}"
        svg.viewBox = box
        assertThat(svg.toString()).isEqualTo("<svg viewBox=\"$box\"/>\n")
    }

    @Test
    fun `format rect with dimensions`() {
        val widthValue = faker.number().numberBetween(1, 200).toString()
        val heightValue = faker.number().numberBetween(1, 200).toString()

        svg.rect {
            width = widthValue
            height = heightValue
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<rect width=\"$widthValue\" height=\"$heightValue\"/>\n</svg>\n")
    }

    @Test
    fun `format of text without body`() {
        svg.text {
        }

        assertThat((svg.children[0] as TEXT).body).isEmpty()
    }

    @Test
    fun `format of text with body`() {
        val msg = faker.chuckNorris().fact()

        svg.text {
            body = msg
        }

        assertThat((svg.children[0] as TEXT).body).isEqualTo(msg.escapeHTML())
    }

    @Test
    fun `format of svg with multiple tags`() {
        val xValue = faker.number().numberBetween(1, 100).toString()
        val yValue = faker.number().numberBetween(1, 100).toString()
        val bodyValue = faker.chuckNorris().fact().toString()

        svg.rect {
            x = xValue
            y = yValue
        }
        svg.text {
            body = bodyValue
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<rect x=\"%s\" y=\"%s\"/>\n<text>%s</text>\n</svg>\n", xValue, yValue, bodyValue.escapeHTML())
    }

    @Test
    fun `format multiple tags`() {
        val msg = faker.chuckNorris().fact()

        svg.rect {}

        svg.text {
            body = msg
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<rect/>\n<text>%s</text>\n</svg>\n", msg.escapeHTML())
    }

    @Test
    fun `format raw attributes`() {
        val noun = faker.hacker().noun()
        val value = faker.hacker().verb()

        svg.attributes[noun] = value

        assertThat(svg.toString()).isEqualTo("<svg $noun=\"$value\"/>\n")
    }

    @Test
    fun `format the fill attribute`() {
        val color = faker.color().name()

        svg.text {
            fill = color
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<text fill=\"$color\"/>\n</svg>\n")
    }

    @Test
    fun `format svg in file mode`() {
        StringBuilder().apply {
            svg.render(this, RenderMode.INLINE)
            assertThat(this.toString()).isEqualTo("<svg/>\n")
        }

        StringBuilder().apply {
            svg.render(this, RenderMode.FILE)
            assertThat(this.toString()).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<svg xmlns=\"http://www.w3.org/2000/svg\"/>\n")
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
//                    assertThat(writer.toString()).isEqualTo(text)
//                }
//            }
//        }
//    }
}
