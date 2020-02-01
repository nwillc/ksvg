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

import com.github.nwillc.ksvg.elements.ATest.Fixtures.QUOTE
import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.js.JsName
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class TEXTTest : HasSvg() {

    @Test
    @JsName("format_a_text_tag")
    fun `format a text tag`() {

        svg.text {
            x = xValue
            y = yValue
            body = QUOTE
        }
        assertEquals(svg.toString(), "<svg>\n<text x=\"$xValue\" y=\"$yValue\">$QUOTE</text>\n</svg>\n")
    }

    companion object Fixtures {
        val xValue = Random.nextInt(1, 50).toString()
        val yValue = Random.nextInt(1, 50).toString()
    }
}
