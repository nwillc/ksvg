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

import com.github.nwillc.ksvg.attributes.HasStroke
import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.js.JsName
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class RECTTest : HasSvg(true) {

    @Test
    @JsName("format_a_rect_with_a_stroke")
    fun `format a rect with a stroke`() {

        svg.rect {
            strokeWidth = WIDTH
        }

        assertEquals((svg.children[0] as HasStroke).strokeWidth, WIDTH)
    }

    @Test
    @JsName("format_a_rect")
    fun `format a rect`() {
        svg.rect {
            x = X_VALUE
            y = "${Y_VALUE}px"
            height = "$HEIGHT%"
            width = "$WIDTH"
        }
        assertEquals(
            svg.toString(),
            "<svg>\n<rect x=\"$X_VALUE\" width=\"$WIDTH\" y=\"${Y_VALUE}px\" height=\"$HEIGHT%\"/>\n</svg>\n"
        )
    }

    companion object Fixtures {
        val WIDTH = Random.nextInt(50, 100).toString()
        val X_VALUE = Random.nextInt(0, 100).toString()
        val Y_VALUE = Random.nextInt(5, 100)
        val HEIGHT = Random.nextInt(10, 100)
    }
}
