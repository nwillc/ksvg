/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

/**
 * An SVG rect element.
 */
@SvgTagMarker
class RECT(validateAttributes: Boolean = false) : REGION("rect", validateAttributes), HasOrigin, HasDimensions {
    override var x: String? by attributes
    override var y: String? by attributes
    override var height: String? by TypedAttribute(AttributeType.LengthOrPercentage)
    override var width: String? by TypedAttribute(AttributeType.LengthOrPercentage)

    /**
     * Add a title to the rect.
     */
    fun title(block: TITLE.() -> Unit): TITLE {
        val title = TITLE(validateAttributes)
        title.block()
        children.add(title)
        return title
    }
}
