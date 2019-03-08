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

import com.github.nwillc.ksvg.SvgTagMarker
import com.github.nwillc.ksvg.attributes.AttributeType
import com.github.nwillc.ksvg.attributes.HasFill
import com.github.nwillc.ksvg.attributes.HasOrigin
import com.github.nwillc.ksvg.attributes.AttributeProperty

/**
 * An SVG text element.
 */
@SvgTagMarker
class TEXT(validation: Boolean = false) : Element("text", validation), HasOrigin, HasFill {
    override var x: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
    override var y: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
    override var fill: String? by attributes

    /**
     * The font size attributes.
     */
    var fontSize: String? by AttributeProperty("font-size", AttributeType.Length)
    /**
     * The font family.
     */
    var fontFamily: String? by AttributeProperty("font-family")
}
