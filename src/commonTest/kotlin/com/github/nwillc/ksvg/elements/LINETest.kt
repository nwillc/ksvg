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

import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class LINETest : HasSvg() {

    @Test
    fun aLineStrokeWidth() {

        svg.line {
            strokeWidth = width
        }

        assertEquals((svg.children[0] as LINE).strokeWidth, width)
    }


    @Test
    fun lineTag() {

        val svg = SVG.svg {
            line {
                x1 = x1Value
                y1 = y1Value
                x2 = x2Value
                y2 = y2Value
            }
        }

        assertEquals(
            svg.toString(),
            "<svg>\n<line x1=\"$x1Value\" x2=\"$x2Value\" y1=\"$y1Value\" y2=\"$y2Value\"/>\n</svg>\n"
        )
    }

    companion object Fixtures {
        val width = Random.nextInt(5, 1000).toString()
        val x1Value = Random.nextInt(5, 1000).toString()
        val y1Value = Random.nextInt(5, 1000).toString()
        val x2Value = Random.nextInt(5, 1000).toString()
        val y2Value = Random.nextInt(5, 1000).toString()
    }
}
