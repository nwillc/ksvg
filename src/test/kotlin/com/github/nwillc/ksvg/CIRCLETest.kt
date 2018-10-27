/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class CIRCLETest : HasSvg() {
    @Test
    internal fun testCircle() {
        svg.circle {
            cx = "10"
            cy = "10"
            r = "5"
            Assertions.assertThat(r).isEqualTo("5")
            fill = "blue"
        }

        assertThat(svg.toString()).isEqualTo("<svg>\n<circle r=\"5\" cx=\"10\" cy=\"10\" fill=\"blue\"/>\n</svg>\n")
    }

    @Test
    internal fun testCircleStrokeWidthGetSet() {
        val width = "10"

        svg.circle {
            strokeWidth = width
        }

        assertThat((svg.children[0] as CIRCLE).strokeWidth).isEqualTo(width)
    }
}
