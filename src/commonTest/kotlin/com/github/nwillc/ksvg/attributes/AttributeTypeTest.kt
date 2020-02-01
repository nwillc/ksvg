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

import com.github.nwillc.ksvg.elements.SVG
import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class AttributeTypeTest : HasSvg(true) {
    @Test
    fun invalidPositionsPercentages() {
        assertFailsWith<IllegalArgumentException> { svg.width = "a" }
        assertFailsWith<IllegalArgumentException> { svg.width = "10a" }
        assertFailsWith<IllegalArgumentException> { svg.width = "10 %" }
        assertFailsWith<IllegalArgumentException> { svg.width = "10%%" }
    }

    @Test
    fun validPositionsPercentages() {
        SVG.svg {
            width = "0"
            width = "10"
            width = "10%"
            width = "9px"
        }
    }

    @Test
    fun invalidListsNumbers() {
        assertFailsWith<IllegalArgumentException> { svg.viewBox = "a" }
        assertFailsWith<IllegalArgumentException> { svg.viewBox = "10a" }
        assertFailsWith<IllegalArgumentException> { svg.viewBox = "1,,1" }
    }

    @Test
    fun validListsNumbers() {
        SVG.svg {
            viewBox = "0 0 1 5"
            viewBox = "10  0 3 4"
            viewBox = "10,0, 3,4"
        }
    }

    @Test
    fun invalidLengths() {
        svg.circle {
            assertFailsWith<IllegalArgumentException> { r = "a" }
            assertFailsWith<IllegalArgumentException> { r = "10a" }
            assertFailsWith<IllegalArgumentException> { r = "10 %" }
        }
    }

    @Test
    fun validLengths() {
        SVG.svg {
            circle {
                r = "10"
                r = "10px"
            }
        }
    }

    @Test
    fun invalidPaths() {
        svg.path {
            assertFailsWith<IllegalArgumentException> { d = "p" }
            assertFailsWith<IllegalArgumentException> { d = "@#$%^&*(" }
        }
    }

    @Test
    fun validPaths() {
        SVG.svg {
            path {
                d = "M 150,0 L 75,200 L 225,200 Z"
                d = "M150 0 L75 200 L225 200 Z"
                d = "m 150 0" +
                    "l 75 200 " +
                    "l 225 200 " +
                    "z"
            }
        }
    }

    @Test
    fun invalidId() {
        assertFailsWith<IllegalArgumentException> { svg.id = "a bad name" }
    }

    @Test
    fun validId() {
        svg.id = "aGoodName"
    }

    @Test
    fun invalidHref() {
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
    fun validHref() {
        SVG.svg {
            use {
                href = "#aGoodName"
            }
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
