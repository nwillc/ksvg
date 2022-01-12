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

package com.github.nwillc.ksvg

import com.github.nwillc.ksvg.elements.Container
import com.github.nwillc.ksvg.elements.SVG
import java.io.ByteArrayOutputStream
import java.io.OutputStreamWriter
import kotlin.test.Test

class FileTest {
    @Test
    fun `create diagonal circles`() {
        val svg = SVG.svg(true) {
            height = "100"
            width = "100"
            defs {
                g {
                    id = "circle1"
                    circle {
                        r = "20"
                        fill = "blue"
                        strokeWidth = "2"
                        stroke = "red"
                    }
                }
            }
            for (i in 20..80 step 20)
                use {
                    x = i.toString()
                    y = i.toString()
                    href = "#circle1"
                }
        }
        OutputStreamWriter(ByteArrayOutputStream()).use {
            svg.render(it, RenderMode.FILE)
        }
    }

    @Test
    fun `create Code Monkey logo`() {
        val svg = SVG.svg(true) {
            height = "300"
            width = "300"
            style {
                body = """

                    svg .black-stroke { stroke: black; stroke-width: 2; }
                    svg .fur-color { fill: white; }

                """.trimIndent()
            }
            // Label
            text {
                x = "40"
                y = "50"
                body = "#CODE"
                fontFamily = "monospace"
                fontSize = "40px"
            }
            // Ears - use a function because USE tag doesn't work in Safari
            ear(100, 100)
            ear(240, 70)
            // Face
            circle {
                cssClass = "black-stroke"
                id = "face"
                cx = "180"
                cy = "140"
                r = "80"
                fill = "#aa450f"
            }
            // Eyes - use a function because USE tag doesn't work in Safari
            eye(160, 95)
            eye(205, 90)
            // Muzzle
            circle {
                cssClass = "black-stroke fur-color"
                cx = "195"
                cy = "178"
                r = "65"
            }
            // Nostrils - use a function because USE tag doesn't work in Safari
            nostril(178, 138)
            nostril(213, 133)
            // Mouth
            path {
                cssClass = "black-stroke"
                d = "M 150 150 C 100,270 310,255 232,140 C 205,190 165,170 150,150 Z"
                fill = "red"
            }
        }

        OutputStreamWriter(ByteArrayOutputStream()).use {
            svg.render(it, RenderMode.FILE)
        }
    }

    private fun Container.ear(x: Int, y: Int) {
        circle {
            cssClass = "black-stroke fur-color"
            cx = x.toString()
            cy = y.toString()
            r = "40"
        }
        circle {
            cssClass = "black-stroke fur-color"
            cx = x.toString()
            cy = y.toString()
            r = "28"
        }
    }

    private fun Container.eye(x: Int, y: Int) {
        circle {
            cssClass = "black-stroke fur-color"
            cx = x.toString()
            cy = y.toString()
            r = "20"
        }
    }

    private fun Container.nostril(x: Int, y: Int) {
        circle {
            cx = x.toString()
            cy = y.toString()
            r = "4"
            fill = "black"
        }
    }
}
