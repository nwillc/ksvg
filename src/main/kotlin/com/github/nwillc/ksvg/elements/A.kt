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

/**
 * An SVG A reference element.
 */
class A(validation: Boolean = false) : Element("a", validation) {
    /**
     * The reference URL.
     */
    var href: String? by attributes

    /**
     * Create a rect element inside the reference.
     */
    fun rect(block: RECT.() -> Unit): RECT = add(RECT(validation), block)

    /**
     * Create a text element inside the reference.
     */
    fun text(block: TEXT.() -> Unit): TEXT = add(TEXT(validation), block)

    override fun getAttributes(renderMode: SVG.RenderMode): Map<String, String?> =
        if (renderMode == SVG.RenderMode.INLINE) HashMap<String, String?>(attributes).also {
            it["xlink:href"] = it.remove("href")
        } else {
            attributes
        }
}
