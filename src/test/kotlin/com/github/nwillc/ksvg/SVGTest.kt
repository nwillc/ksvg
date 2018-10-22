/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SVGTest {
    private val svg = svg {}

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
    internal fun testTextOrigin() {
        svg.text {
            x = 1
            y = 2
        }
        assertThat(svg.toString()).isEqualTo("<svg><text x=\"1\" y=\"2\"/>\n</svg>\n")
    }

    @Test
    internal fun testDimensions() {
        svg.rect {
            width = 20
            height = 10
        }

        assertThat(svg.toString()).isEqualTo("<svg><rect width=\"20\" height=\"10\"/>\n</svg>\n")
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
    internal fun testLineStrokeWidthGetSet() {
        val width = 10

        svg.line {
            strokeWidth = width
        }

        assertThat((svg.children[0] as LINE).strokeWidth).isEqualTo(width)
    }

    @Test
    internal fun testRectStrokeWidthGetSet() {
        val width = 10

        svg.rect {
            strokeWidth = width
        }

        assertThat((svg.children[0] as HasStroke).strokeWidth).isEqualTo(width)
    }

    @Test
    internal fun testCircleStrokeWidthGetSet() {
        val width = 10

        svg.circle {
            strokeWidth = width
        }

        assertThat((svg.children[0] as CIRCLE).strokeWidth).isEqualTo(width)
    }

    @Test
    internal fun testSvgWithTags() {
        svg.rect {
            x = 1
            y = 2
        }
        svg.text {
            body = "Hello World"
        }

        assertThat(svg.toString()).isEqualTo("<svg><rect x=\"1\" y=\"2\"/>\n<text>Hello World</text>\n</svg>\n")
    }

    @Test
    internal fun testAdd() {
        val msg = "Hello World"

        svg.rect {}

        svg.text {
            body = msg
        }

        assertThat(svg.toString()).isEqualTo("<svg><rect/>\n<text>$msg</text>\n</svg>\n")
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

        assertThat(svg.toString()).isEqualTo("<svg><text fill=\"black\"/>\n</svg>\n")
    }

    @Test
    internal fun testHref() {
        val url = "http://www.google.com"
        svg.a {
            href = url
            text {
                body = "google.com"
            }
            rect {
            }
        }
        assertThat((svg.children[0] as A).href).isEqualTo(url)
        assertThat(svg.toString()).isEqualTo("<svg><a xlink:href=\"http://www.google.com\"><text>google.com</text>\n<rect/>\n</a>\n</svg>\n")
    }

    @Test
    internal fun testCircle() {
        svg.circle {
            cx = 10
            cy = 10
            r = 5
            fill = "blue"
        }

        assertThat(svg.toString()).isEqualTo("<svg><circle r=\"5\" cx=\"10\" cy=\"10\" fill=\"blue\"/>\n</svg>\n")
    }

    @Test
    internal fun testPolygon() {
        val pts = "200,10 250,190 160,210"
        svg.polygon {
            points = pts
        }

        assertThat(svg.toString()).isEqualTo("<svg><polygon points=\"$pts\"/>\n</svg>\n")
    }

    @Test
    internal fun testLine() {
        svg.line {
            x1 = 1
            y1 = 1
            x2 = 5
            y2 = 5
        }

        assertThat(svg.toString()).isEqualTo("<svg><line y1=\"1\" x1=\"1\" y2=\"5\" x2=\"5\"/>\n</svg>\n")
    }

    @Test
    internal fun testOutput() {
        val svg = svg {
            width = 200
            height = 200
            rect {
                title {
                    body = "A Blue Rectangle"
                }
                x = 50
                y = 50
                width = 20
                height = 10
                fill = "blue"
            }
            text {
                body = "label"
                x = 100
                y = 100
            }
            circle {
                cx = 100
                cy = 150
                r = 20
                fill = "red"
                stroke = "blue"
                strokeWidth = 2
            }
            line {
                x1 = 0
                y1 = 0
                x2 = 40
                y2 = 40
                strokeWidth = 3
                stroke = "black"
            }
        }
        System.out.println("<html><body>$svg</body></html>")
    }
}
