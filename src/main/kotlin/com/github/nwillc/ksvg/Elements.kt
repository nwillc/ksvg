/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee
 * is hereby granted, provided that the above copyright notice and this permission notice appear in
 * all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS
 * SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
 * AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE
 * OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import java.io.StringWriter
import java.io.Writer
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Indicates something is an SVG DSL marker.
 */
@DslMarker
annotation class SvgTagMarker

/**
 * The mode to employ when rendering. Some Elements must render differently when used inline in HTML5 or in a
 * standalone SVG file.
 */
enum class RenderMode {
    /**
     * Render as inline SVG designed to appear inline in HTML5.
     */
    INLINE,
    /**
     * Render as an SVG `.svg` file format.
     */
    FILE
}

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
     * Raw text body of the Element.
     */
    var body: String = ""

    /**
     * Get the attributes specific to a render mode. Allows tags to modify their attributes during rendering
     * based on the rendering mode. Defaults to the basic Element attributes but can be overridden by Elements to return
     * differing attribute based on mode.
     * @param renderMode which mode we are rendering in
     */
    open fun getAttributes(renderMode: RenderMode): Map<String, String?> {
        return attributes
    }

    /**
     * Render the Element as SVG.
     * @param writer A Writer to append the SVG to.
     * @param renderMode Should the Elements render for inline SVG or file SVG.
     */
    open fun render(writer: Writer, renderMode: RenderMode) {
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
                writer.append(escapeHTML(body))
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
                render(it, RenderMode.INLINE)
                it.toString()
            }
        } catch (e: Throwable) {
            throw RuntimeException("Unable to generate SVG", e)
        }
    }
}

/**
 * Element is a region and therefore has stroke and fill.
 */
abstract class REGION(name: String, validateAttributes: Boolean) : Element(name, validateAttributes), HasStroke,
    HasFill {
    override var stroke: String? by attributes
    override var strokeWidth: String? by RenamedAttribute("stroke-width")
    override var fill: String? by attributes
}

/**
 * Create an SVG element.
 */
fun svg(validateAttributes: Boolean = false, init: SVG.() -> Unit): SVG {
    val svg = SVG(validateAttributes)
    svg.init()
    return svg
}

/**
 * A property delegate to allow attributes to be rendered with a different name.
 */
internal class RenamedAttribute(private val renamed: String) : ReadWriteProperty<Any?, String?> {
    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        if (thisRef is Element) {
            return thisRef.attributes[renamed]
        }
        return null
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if (value != null && thisRef is Element) {
            thisRef.attributes[renamed] = value
        }
    }
}

/**
 * A property delegate that checks if the value matches a specified AttributeType's criteria.
 */
internal class TypedAttribute(private val type: AttributeType) : ReadWriteProperty<Any?, String?> {
    @Suppress("UNCHECKED_CAST")
    override operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        if (thisRef is Element) {
            return thisRef.attributes[property.name]
        }
        return null
    }

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if (value != null && thisRef is Element) {
            if (thisRef.validateAttributes)
                type.verify(value)

            thisRef.attributes[property.name] = value
        }
    }
}

/**
 * A quick and dirty HTML special character escaping for body elements.
 */
fun escapeHTML(s: String): String {
    val out = StringBuilder(Math.max(16, s.length))
    for (i in 0 until s.length) {
        val c = s[i]
        if (c.toInt() > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
            out.append("&#")
            out.append(c.toInt())
            out.append(';')
        } else {
            out.append(c)
        }
    }
    return out.toString()
}
