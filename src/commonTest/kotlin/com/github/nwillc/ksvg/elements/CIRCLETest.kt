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
import kotlin.js.JsName
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.test.Test
import kotlin.test.assertEquals

class CIRCLETest : HasSvg() {

    @Test
    @JsName("format_a_circle")
    fun `format a circle`() {
        svg.circle {
            cx = cxValue
            cy = cyValue
            r = rValue
            fill = COLOR
        }

        assertEquals(
            svg.toString(),
            "<svg>\n<circle r=\"$rValue\" cx=\"$cxValue\" cy=\"$cyValue\" fill=\"$COLOR\"/>\n</svg>\n"
        )
    }

    @Test
    @JsName("format_a_circle_with_a_width")
    fun `format a circle with a width`() {
        svg.circle {
            strokeWidth = WIDTH
        }

        assertEquals((svg.children[0] as CIRCLE).strokeWidth, WIDTH)
    }

    companion object Fixtures {
        val WIDTH = Random.nextInt(1..100).toString()
        val cxValue = Random.nextInt(0, 1000).toString()
        val cyValue = Random.nextInt(0, 1000).toString()
        val rValue = Random.nextInt(1, 1000).toString()
        val COLOR = listOf("red", "blue", "green").let { it[Random.nextInt(it.indices)]}
    }
}
