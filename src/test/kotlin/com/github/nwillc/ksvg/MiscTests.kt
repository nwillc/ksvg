/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is
 * hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE
 * FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 * OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT
 * OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import com.github.nwillc.ksvg.elements.SVG
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.lang.StringBuilder

internal class MiscTests {

    @Test
    internal fun testValidationAttributesTrue() {
        SVG.svg(true) {
            assertThat(validation).isTrue()
            rect {
                assertThat(validation).isTrue()
            }
        }
    }

    @Test
    internal fun testValidationAttributesFalse() {
        SVG.svg {
            assertThat(validation).isFalse()
            rect {
                assertThat(validation).isFalse()
            }
        }
    }

    @Test
    internal fun testEscapeHTML() {
        val sb = StringBuilder()
        sb.escapeHTML("a&<>\"\u00E7")
        assertThat(sb.toString()).isEqualTo("a&#38;&#60;&#62;&#34;&#231;")
    }
}
