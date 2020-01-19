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

import com.github.nwillc.ksvg.toAttributeName
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A property delegate to allow attributes to be rendered with a different name and to validate content by type.
 * @param renamed The name to use when rendering the property.
 * @param type The attribute type to use for validation.
 */
internal class AttributeProperty(
    private val renamed: String? = null,
    private val type: AttributeType = AttributeType.None
) : ReadWriteProperty<Any?, String?> {
    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): String? = if (thisRef is HasAttributes) {
        thisRef.attributes[renamed ?: property.name.toAttributeName()]
    } else {
        null
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if (value != null && thisRef is HasAttributes) {
            if (thisRef.validation)
                type.verify(value)
            thisRef.attributes[renamed ?: property.name.toAttributeName()] = value
        }
    }
}
