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
    private val sb = StringBuilder()

    @Test
    internal fun testSvg() {
        assertRenders("<svg/>\n")
    }

    @Test
    internal fun testSvgWithAttr() {
        svg.viewBox = "0 0 10 10"
        assertRenders("<svg viewBox=\"0 0 10 10\"/>\n")
    }

    @Test
    internal fun testTextOrigin() {
        svg.text {
            x = 1
            y = 2
        }

        assertRenders("<svg><text x=\"1\" y=\"2\"/>\n</svg>\n")
    }

    @Test
    internal fun testDimensions() {
        svg.rect {
            width = 20
            height = 10
        }

        assertRenders("<svg><rect width=\"20\" height=\"10\"/>\n</svg>\n")
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

        assertRenders("<svg><rect x=\"1\" y=\"2\"/>\n<text>Hello World</text>\n</svg>\n")
    }

    @Test
    internal fun testAdd() {
        svg.rect {}

        svg.text {
            body = "Hello World"
        }

        assertRenders("<svg><rect/>\n<text>Hello World</text>\n</svg>\n")
    }

    @Test
    internal fun testRawAttributes() {
        svg.attributes["foo"] = "bar"

        assertRenders("<svg foo=\"bar\"/>\n")
    }

    @Test
    internal fun testFill() {
        svg.text {
            fill = "black"
        }

        assertRenders("<svg><text fill=\"black\"/>\n</svg>\n")
    }

    @Test
    internal fun testHref() {
        svg.a {
            href = "http://www.google.com"
            text {
                body = "google.com"
            }
        }
        assertRenders("<svg><a xlink:href=\"http://www.google.com\"><text>google.com</text>\n</a>\n</svg>\n")
    }

    @Test
    internal fun testCircle() {
        svg.circle {
            cx = 10
            cy = 10
            r = 5
            fill = "blue"
        }

        assertRenders("<svg><circle r=\"5\" cx=\"10\" cy=\"10\" fill=\"blue\"/>\n</svg>\n")
    }

    @Test
    internal fun testLine() {
        svg.line {
            x1 = 1
            y1 = 1
            x2 = 5
            y2 = 5
        }

        assertRenders("<svg><line y1=\"1\" x1=\"1\" y2=\"5\" x2=\"5\"/>\n</svg>\n")
    }

    private fun assertRenders(str: String) {
        svg.render(sb)
        assertThat(sb.toString()).isEqualTo(str)
    }
}