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

package com.github.nwillc.ksvg

import com.github.nwillc.ksvg.elements.SVG
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MiscTests {
    private val specialCharacters = "a&<>\"\u00E7"
    private val escapedCharacters = "a&#38;&#60;&#62;&#34;&#231;"

    @Test
    fun `inherit validation true if set`() {
        SVG.svg(true) {
            assertThat(validation).isTrue()
            rect {
                assertThat(validation).isTrue()
            }
        }
    }

    @Test
    fun `default validation is false`() {
        SVG.svg {
            assertThat(validation).isFalse()
            rect {
                assertThat(validation).isFalse()
            }
        }
    }

    @Test
    fun `escape html special characters from an Appendable`() {
        val sb = StringBuilder().apply { escapeHTML(specialCharacters) }
        assertThat(sb.toString()).isEqualTo(escapedCharacters)
    }

    @Test
    fun `escape html special characters from a String`() {
        assertThat(specialCharacters.escapeHTML()).isEqualTo(escapedCharacters)
    }
}
