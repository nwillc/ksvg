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

package com.github.nwillc.ksvg.attributes

import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class AttributeTypeTest : HasSvg(true) {
    @Test
    @JsName("reject_invalid_positions_or_percentages")
    fun `reject invalid positions or percentages`() {
        assertFailsWith<IllegalArgumentException> { svg.width = "a" }
        assertFailsWith<IllegalArgumentException> { svg.width = "10a" }
        assertFailsWith<IllegalArgumentException> { svg.width = "10 %" }
        assertFailsWith<IllegalArgumentException> { svg.width = "10%%" }
    }

    @Test
    @JsName("accept_valid_positions_and_percentages")
    fun `accept valid positions and percentages`() {
        svg.width = "0"
        svg.width = "10"
        svg.width = "10%"
        svg.width = "9px"
    }

    @Test
    @JsName("reject_invalid_lists_of_numbers")
    fun `reject invalid lists of numbers`() {
        assertFailsWith<IllegalArgumentException> { svg.viewBox = "a" }
        assertFailsWith<IllegalArgumentException> { svg.viewBox = "10a" }
        assertFailsWith<IllegalArgumentException> { svg.viewBox = "1,,1" }
    }

    @Test
    @JsName("accept_valid_lists_of_numbers")
    fun `accept valid lists of numbers`() {
        svg.viewBox = "0 0 1 5"
        svg.viewBox = "10  0 3 4"
        svg.viewBox = "10,0, 3,4"
    }

    @Test
    @JsName("reject_invalid_lengths")
    fun `reject invalid lengths`() {
        svg.circle {
            assertFailsWith<IllegalArgumentException> { r = "a" }
            assertFailsWith<IllegalArgumentException> { r = "10a" }
            assertFailsWith<IllegalArgumentException> { r = "10 %" }
        }
    }

    @Test
    @JsName("accept_valid_lengths")
    fun `accept valid lengths`() {
        svg.circle {
            r = "10"
            r = "10px"
        }
    }

    @Test
    @JsName("reject_invalid_paths")
    fun `reject invalid paths`() {
        svg.path {
            assertFailsWith<IllegalArgumentException> { d = "p" }
            assertFailsWith<IllegalArgumentException> { d = "@#$%^&*(" }
        }
    }

    @Test
    @JsName("accept_valid_paths")
    fun `accept valid paths`() {
        svg.path {
            d = "M 150,0 L 75,200 L 225,200 Z"
            d = "M150 0 L75 200 L225 200 Z"
            d = "m 150 0" +
                "l 75 200 " +
                "l 225 200 " +
                "z"
        }
    }

    @Test
    @JsName("reject_invalid_id")
    fun `reject invalid id`() {
        assertFailsWith<IllegalArgumentException> { svg.id = "a bad name" }
    }

    @Test
    @JsName("accept_valid_id")
    fun `accept valid id`() {
        svg.id = "aGoodName"
    }

    @Test
    @JsName("reject_invalid_href")
    fun `reject invalid href`() {
        assertFailsWith<IllegalArgumentException> {
            svg.use {
                href = "#a bad name"
            }
        }
        assertFailsWith<IllegalArgumentException> {
            svg.use {
                href = "name"
            }
        }
    }

    @Test
    @JsName("accept_valid_href")
    fun `accept valid href`() {
        svg.use {
            href = "#aGoodName"
        }
    }

//    @Test
//    fun `generate warning when declaring CSS in inline mode`() {
//        svg.rect {
//            cssClass = "test"
//        }
//        assertThat(logger.loggingEvents).containsAll(
//            asList(warn("CSS support is incomplete in some browsers, know issues in IE and Firefox."))
//        )
//    }

//    @Test
//    fun `not generate a warning declaring CSS in file mode`() {
//        SVG(false).rect {
//            cssClass = "test"
//        }
//        assertThat(logger.loggingEvents).isEmpty()
//    }

    inner class GeneratedCodeTests {
        private val foo = "foo"
        private val bar = "bar"

        // Test the impossible error conditions of the RenameAttribute delegate
//        @Test
//        fun `return null attribute for non element`() {
//            val typeAttr = AttributeProperty(foo)
//
//            assertThat(typeAttr.getValue(this, kProperty)).isNull()
//            typeAttr.setValue(svg, kProperty, null)
//            typeAttr.setValue(this, kProperty, foo)
//            typeAttr.setValue(this, kProperty, null)
//        }

//        @Test
//        fun `return attribute of element`() {
//            val typeAttr = AttributeProperty()
//            every { kProperty.name } returns bar
//            svg.attributes[bar] = bar
//            assertThat(typeAttr.getValue(svg, kProperty)).isEqualTo(bar)
//        }
    }
}
