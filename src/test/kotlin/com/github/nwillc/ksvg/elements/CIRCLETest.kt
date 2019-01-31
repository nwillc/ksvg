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

import com.github.javafaker.Faker
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CIRCLETest : HasSvg() {
    private val faker = Faker()

    @Test
    internal fun testCircle() {
        val colorValue = faker.color().name()
        val cxValue = faker.number().numberBetween(0, 1000).toString()
        val cyValue = faker.number().numberBetween(0, 1000).toString()
        val rValue = faker.number().numberBetween(1, 1000).toString()

        svg.circle {
            cx = cxValue
            cy = cyValue
            r = rValue
            fill = colorValue
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<circle r=\"$rValue\" cx=\"$cxValue\" cy=\"$cyValue\" fill=\"$colorValue\"/>\n</svg>\n")
    }

    @Test
    internal fun testCircleStrokeWidthGetSet() {
        val width = faker.number().numberBetween(1, 100).toString()

        svg.circle {
            strokeWidth = width
        }

        assertThat((svg.children[0] as CIRCLE).strokeWidth).isEqualTo(width)
    }
}
