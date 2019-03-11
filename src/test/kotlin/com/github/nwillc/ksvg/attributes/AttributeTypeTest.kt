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

package com.github.nwillc.ksvg.attributes

import com.github.nwillc.ksvg.elements.HasSvg
import com.github.nwillc.ksvg.elements.SVG
import com.github.nwillc.ksvg.elements.USE
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import uk.org.lidalia.slf4jtest.LoggingEvent.warn
import uk.org.lidalia.slf4jtest.TestLoggerFactory
import java.util.Arrays.asList
import kotlin.reflect.KProperty

@DisplayName("Attribute Validations")
internal class AttributeTypeTest : HasSvg(true) {
    private val kProperty = mockk<KProperty<String>>()
    private var logger = TestLoggerFactory.getTestLogger(USE::javaClass.name)!!

    @BeforeEach
    internal fun streamPrep() {
        TestLoggerFactory.clear()
    }

    @Test
    fun `reject invalid positions or percentages`() {
        assertThatThrownBy { svg.width = "a" }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { svg.width = "10a" }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { svg.width = "10 %" }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { svg.width = "10%%" }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `accept valid positions and percentages`() {
        svg.width = "0"
        svg.width = "10"
        svg.width = "10%"
        svg.width = "9px"
    }

    @Test
    fun `reject invalid lists of numbers`() {
        assertThatThrownBy { svg.viewBox = "a" }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { svg.viewBox = "10a" }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { svg.viewBox = "1,,1" }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `accept valid lists of numbers`() {
        svg.viewBox = "0 0 1 5"
        svg.viewBox = "10  0 3 4"
        svg.viewBox = "10,0, 3,4"
    }

    @Test
    fun `reject invalid lengths`() {
        svg.circle {
            assertThatThrownBy { r = "a" }.isInstanceOf(IllegalArgumentException::class.java)
            assertThatThrownBy { r = "10a" }.isInstanceOf(IllegalArgumentException::class.java)
            assertThatThrownBy { r = "10 %" }.isInstanceOf(IllegalArgumentException::class.java)
        }
    }

    @Test
    fun `accept valid lengths`() {
        svg.circle {
            r = "10"
            r = "10px"
        }
    }

    @Test
    fun `reject invalid paths`() {
        svg.path {
            assertThatThrownBy { d = "p" }.isInstanceOf(IllegalArgumentException::class.java)
            assertThatThrownBy { d = "@#$%^&*(" }.isInstanceOf(IllegalArgumentException::class.java)
        }
    }

    @Test
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
    fun `reject invalid id`() {
        assertThatThrownBy { svg.id = "a bad name" }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `accept valid id`() {
        svg.id = "aGoodName"
    }

    @Test
    fun `reject invalid href`() {
        assertThatThrownBy {
            svg.use {
                href = "#a bad name"
            }
        }.isInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy {
            svg.use {
                href = "name"
            }
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `accept valid href`() {
        svg.use {
            href = "#aGoodName"
        }
    }

    @Test
    fun `generate warning when declaring CSS in inline mode`() {
        svg.rect {
            cssClass = "test"
        }
        assertThat(logger.loggingEvents).containsAll(
            asList(warn("CSS support is incomplete in some browsers, know issues in IE and Firefox."))
        )
    }

    @Test
    fun `not generate a warning declaring CSS in file mode`() {
        SVG(false).rect {
            cssClass = "test"
        }
        assertThat(logger.loggingEvents).isEmpty()
    }

    @Nested
    @DisplayName("Coverage of Generated Code")
    inner class GeneratedCodeTests {
        private val foo = "foo"
        private val bar = "bar"

        // Test the impossible error conditions of the RenameAttribute delegate
        @Test
        fun `return null attribute for non element`() {
            val typeAttr = AttributeProperty(foo)

            assertThat(typeAttr.getValue(this, kProperty)).isNull()
            typeAttr.setValue(svg, kProperty, null)
            typeAttr.setValue(this, kProperty, foo)
            typeAttr.setValue(this, kProperty, null)
        }

        @Test
        fun `return attribute of element`() {
            val typeAttr = AttributeProperty()
            every { kProperty.name } returns bar
            svg.attributes[bar] = bar
            assertThat(typeAttr.getValue(svg, kProperty)).isEqualTo(bar)
        }
    }
}
