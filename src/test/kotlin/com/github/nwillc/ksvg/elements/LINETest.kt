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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LINETest : HasSvg() {
    private val faker = Faker()

    @Test
    fun `format a line with stroke width`() {
        val width = faker.number().numberBetween(5, 1000).toString()

        svg.line {
            strokeWidth = width
        }

        assertThat((svg.children[0] as LINE).strokeWidth).isEqualTo(width)
    }

    @Test
    fun `format line tag`() {
        val x1Value = faker.number().numberBetween(5, 1000).toString()
        val y1Value = faker.number().numberBetween(5, 1000).toString()
        val x2Value = faker.number().numberBetween(5, 1000).toString()
        val y2Value = faker.number().numberBetween(5, 1000).toString()

        svg.line {
            x1 = x1Value
            y1 = y1Value
            x2 = x2Value
            y2 = y2Value
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<line y1=\"$y1Value\" x1=\"$x1Value\" y2=\"$y2Value\" x2=\"$x2Value\"/>\n</svg>\n")
    }
}
