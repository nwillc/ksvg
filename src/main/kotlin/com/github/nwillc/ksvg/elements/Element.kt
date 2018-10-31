/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby
 * granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER
 * IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg.elements

import com.github.nwillc.ksvg.attributes.TypedAttribute
import com.github.nwillc.ksvg.attributes.AttributeType
import com.github.nwillc.ksvg.attributes.RenamedAttribute
import com.github.nwillc.ksvg.escapeHTML
import java.io.StringWriter
import java.io.Writer

/**
 * Abstract SVG named element with attributes and child elements.
 * @param name The svg tag of the element.
 * @param validateAttributes A boolean indicating if attribute values should be validated based on AttributeType when
 * assigned.
 */
abstract class Element(private val name: String, var validateAttributes: Boolean) {
    /**
     * A Map of attributes associated with the element.
     */
    val attributes = hashMapOf<String, String?>()

    /**
     * Child Element contained in this Element.
     */
    val children = arrayListOf<Element>()

    /**
     * The id attribute of the Element.
     */
    var id: String? by TypedAttribute(AttributeType.IdName)

    /**
     * The CSS class.
     */
    var cssClass: String? by RenamedAttribute("class")

    /**
     * Raw text body of the Element.
     */
    var body: String = ""

    /**
     * Get the attributes specific to a render mode. Allows tags to modify their attributes during rendering
     * based on the rendering mode. Defaults to the basic Element attributes but can be overridden by Elements to return
     * differing attribute based on mode.
     * @param renderMode which mode we are rendering in
     */
    open fun getAttributes(renderMode: SVG.RenderMode): Map<String, String?> {
        return attributes
    }

    /**
     * Render the Element as SVG.
     * @param writer A Writer to append the SVG to.
     * @param renderMode Should the Elements render for inline SVG or file SVG.
     */
    open fun render(writer: Writer, renderMode: SVG.RenderMode) {
        writer.append("<$name")
        getAttributes(renderMode).entries.forEach {
            writer.append(' ')
            writer.append(it.key)
            writer.append("=\"")
            writer.append(it.value)
            writer.append('"')
        }
        if (!hasContent()) {
            writer.append("/>\n")
        } else {
            writer.append('>')
            if (hasBody()) {
                writer.append(body.escapeHTML())
            } else {
                writer.append('\n')
            }
            children.forEach {
                it.render(writer, renderMode)
            }
            writer.append("</$name>\n")
        }
    }

    /**
     * Has raw text body.
     */
    fun hasBody(): Boolean = body.isNotEmpty()

    /**
     * Has Children.
     */
    fun hasChildren(): Boolean = children.isNotEmpty()

    /**
     * Has any content, i.e. a body and/or children.
     */
    fun hasContent(): Boolean = hasBody() || hasChildren()

    /**
     * Returns the rendered inline SVG as a String.
     */
    override fun toString(): String {
        try {
            return StringWriter().use {
                render(it, SVG.RenderMode.INLINE)
                it.toString()
            }
        } catch (e: Throwable) {
            throw RuntimeException("Unable to generate SVG", e)
        }
    }
}
