/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER
 * IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg.elements

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.io.StringWriter

internal class SVGTest : HasSvg() {

    @Test
    internal fun testSvg() {
        assertThat(svg.toString()).isEqualTo("<svg/>\n")
    }

    @Test
    internal fun testSvgWithAttr() {
        svg.viewBox = "0 0 10 10"
        assertThat(svg.toString()).isEqualTo("<svg viewBox=\"0 0 10 10\"/>\n")
    }

    @Test
    internal fun testDimensions() {
        svg.rect {
            width = "20"
            height = "10"
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<rect width=\"20\" height=\"10\"/>\n</svg>\n")
    }

    @Test
    internal fun testNoBody() {

        svg.text {
        }

        assertThat((svg.children[0] as TEXT).body).isEmpty()
    }

    @Test
    internal fun testBodyGetSet() {
        val msg = "Hello World"

        svg.text {
            body = msg
        }

        assertThat((svg.children[0] as TEXT).body).isEqualTo(msg)
    }

    @Test
    internal fun testSvgWithTags() {
        svg.rect {
            x = "1"
            y = "2"
        }
        svg.text {
            body = "Hello World"
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<rect x=\"1\" y=\"2\"/>\n<text>Hello World</text>\n</svg>\n")
    }

    @Test
    internal fun testAdd() {
        val msg = "Hello World"

        svg.rect {}

        svg.text {
            body = msg
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<rect/>\n<text>$msg</text>\n</svg>\n")
    }

    @Test
    internal fun testRawAttributes() {
        svg.attributes["foo"] = "bar"

        assertThat(svg.toString()).isEqualTo("<svg foo=\"bar\"/>\n")
    }

    @Test
    internal fun testFill() {
        svg.text {
            fill = "black"
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<text fill=\"black\"/>\n</svg>\n")
    }

    @Test
    internal fun testSVGFileMode() {
        StringWriter().use {
            svg.render(it, SVG.RenderMode.INLINE)
            assertThat(it.toString()).isEqualTo("<svg/>\n")
        }

        StringWriter().use {
            svg.render(it, SVG.RenderMode.FILE)
            assertThat(it.toString()).isEqualTo("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<svg xmlns=\"http://www.w3.org/2000/svg\"/>\n")
        }
    }

    @Test
    internal fun testPersistedExample() {
        val svg = SVG.svg {
            width = "200"
            height = "200"
            rect {
                title {
                    body = "A Blue Rectangle"
                }
                x = "50"
                y = "50"
                width = "20"
                height = "10"
                fill = "blue"
            }
            text {
                body = "label"
                x = "100"
                y = "100"
            }
            circle {
                cx = "100"
                cy = "150"
                r = "20"
                fill = "red"
                stroke = "blue"
                strokeWidth = "2"
            }
            line {
                x1 = "0"
                y1 = "0"
                x2 = "40"
                y2 = "40"
                strokeWidth = "3"
                stroke = "black"
            }
        }

        javaClass.getResourceAsStream("/example1.svg").use { resource ->
            InputStreamReader(resource).use { input ->
                val text = input.readText()
                StringWriter().use { writer ->
                    svg.render(writer, SVG.RenderMode.FILE)
                    assertThat(writer.toString()).isEqualTo(text)
                }
            }
        }
    }
}
