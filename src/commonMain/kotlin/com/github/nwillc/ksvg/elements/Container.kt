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

import com.github.nwillc.ksvg.attributes.HasAttributes
import com.github.nwillc.ksvg.attributes.HasAttributesImpl

/**
 * An abstract container element which provides factories for general sub elements.
 */
abstract class Container(
    name: String,
    validation: Boolean,
    hasAttributes: HasAttributes = HasAttributesImpl(validation)
) : Element(name, validation, hasAttributes) {
    /**
     * Create a rect element in this svg.
     */
    fun rect(block: RECT.() -> Unit): RECT = add(RECT(validation), block)

    /**
     * Create a text element in this svg.
     */
    fun text(block: TEXT.() -> Unit): TEXT = add(TEXT(validation), block)

    /**
     * Create a circle element in this svg.
     */
    fun circle(block: CIRCLE.() -> Unit): CIRCLE = add(CIRCLE(validation), block)

    /**
     * Create a polygon element in this svg.
     */
    fun polygon(block: POLYGON.() -> Unit): POLYGON = add(POLYGON(validation), block)

    /**
     * Create a line element in this svg.
     */
    fun line(block: LINE.() -> Unit): LINE = add(LINE(validation), block)

    /**
     * Create an a reference element in this svg.
     */
    fun a(block: A.() -> Unit): A = add(A(validation), block)

    /**
     * Create an a path element in this svg.
     */
    fun path(block: PATH.() -> Unit): PATH = add(PATH(validation), block)

    /**
     * Create a group element in this svg.
     */
    fun g(block: G.() -> Unit): G = add(G(validation), block)

    /**
     * Create a group element in this svg.
     */
    fun use(block: USE.() -> Unit): USE = add(USE(validation), block)
}
