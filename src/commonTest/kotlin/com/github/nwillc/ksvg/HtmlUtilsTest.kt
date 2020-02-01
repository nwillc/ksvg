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

package com.github.nwillc.ksvg

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class HtmlUtilsTest {
    private val specialCharacters = "a&<>\"\u00E7"
    private val escapedCharacters = "a&#38;&#60;&#62;&#34;&#231;"

    @Test
    @JsName("escape_html_special_characters_from_a_String")
    fun `escape html special characters from a String`() {
        assertEquals(specialCharacters.escapeHTML(), escapedCharacters)
    }

    @Test
    @JsName("normalize_attribute_names")
    fun `normalize attribute names`() {
        assertEquals("strokeWidth".toAttributeName(),"stroke-width")
    }
}
