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

enum class RenderMode {
    INLINE,
    FILE
}

/**
 * Abstract SVG named element with attributes and child elements.
 * @param name The svg tag of the element.
 */
@SvgTagMarker
abstract class Element(private val name: String) {
    /**
     * A Map of attributes associated with the element.
     */
    val attributes = hashMapOf<String, Any?>()

    /**
     * Child Element contained in this Element.
     */
    val children = arrayListOf<Element>()

    /**
     * Raw text body of the Element.
     */
    var body: String = ""

    /**
     * Render the Element as SVG.
     * @param writer A Writer to append the SVG to.
     */
    open fun render(writer: Writer, renderMode: RenderMode = RenderMode.INLINE) {
        writer.append("<$name")
        attributes.entries.forEach {
            writer.append(' ')
            writer.append(it.key)
            writer.append("=\"")
            writer.append(it.value.toString())
            writer.append('"')
        }
        if (!hasContent()) {
            writer.append("/>\n")
        } else {
            writer.append('>')
            if (hasBody()) {
                writer.append(body)
            } else {
                writer.append('\n')
            }
            children.forEach {
                it.render(writer)
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
     * Returns the rendered SVG as a String.
     */
    override fun toString(): String {
        try {
            return StringWriter().use {
                render(it)
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
abstract class REGION(name: String) : Element(name), HasStroke, HasFill {
    override var stroke: String by attributes
    override var strokeWidth: Int by RenamedAttribute<Int>(attributes, "stroke-width")
    override var fill: String by attributes
}

/**
 * The SVG element itself.
 */
class SVG : Element("svg"), HasDimensions {
    override var height: Int by attributes
    override var width: Int by attributes

    /**
     * The viewBox attribute.
     */
    var viewBox: String by attributes

    /**
     * Create a rect element in this svg.
     */
    fun rect(init: RECT.() -> Unit): RECT {
        val rect = RECT()
        rect.init()
        children.add(rect)
        return rect
    }

    /**
     * Create a text element in this svg.
     */
    fun text(init: TEXT.() -> Unit): TEXT {
        val text = TEXT()
        text.init()
        children.add(text)
        return text
    }

    /**
     * Create a circle element in this svg.
     */
    fun circle(init: CIRCLE.() -> Unit): CIRCLE {
        val circle = CIRCLE()
        circle.init()
        children.add(circle)
        return circle
    }

    /**
     * Create a polygon element in this svg.
     */
    fun polygon(init: POLYGON.() -> Unit): POLYGON {
        val polygon = POLYGON()
        polygon.init()
        children.add(polygon)
        return polygon
    }

    /**
     * Create a line element in this svg.
     */
    fun line(init: LINE.() -> Unit): LINE {
        val line = LINE()
        line.init()
        children.add(line)
        return line
    }

    /**
     * Create an a refereence element in this svg.
     */
    fun a(block: A.() -> Unit): A {
        val a = A()
        a.block()
        children.add(a)
        return a
    }

    override fun render(writer: Writer, renderMode: RenderMode) {
        if (renderMode == RenderMode.FILE) {
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
        }
        super.render(writer, renderMode)
    }
}

/**
 * The SVG circle element.
 */
class CIRCLE : REGION("circle") {
    /**
     * The x coordinate of the circle's center.
     */
    var cx: Int by attributes

    /**
     * The r coordinate of the circle's center.
     */
    var cy: Int by attributes

    /**
     * The radius or the circle.
     */
    var r: Int by attributes
}

/**
 * The SVG polygon element.
 */
class POLYGON : REGION("polygon") {
    /**
     * The points defining the x and y coordinates for each corner of the polygon.
     */
    var points: String by attributes
}

/**
 * An SVG title element.
 */
class TITLE : Element("title")

/**
 * An SVG rect element.
 */
class RECT : REGION("rect"), HasOrigin, HasDimensions {
    override var x: Int by attributes
    override var y: Int by attributes
    override var height: Int by attributes
    override var width: Int by attributes

    /**
     * Add a title to the rect.
     */
    fun title(block: TITLE.() -> Unit): TITLE {
        val title = TITLE()
        title.block()
        children.add(title)
        return title
    }
}

/**
 * An SVG line element.
 */
class LINE : Element("line"), HasStroke {
    override var stroke: String by attributes
    override var strokeWidth: Int by RenamedAttribute<Int>(attributes, "stroke-width")
    /**
     * The X1 coordinate of the line.
     */
    var x1: Int by attributes

    /**
     * The Y1 coordinate of the line.
     */
    var y1: Int by attributes

    /**
     * The X2 coordinate of the line.
     */
    var x2: Int by attributes

    /**
     * The Y2 coordinate of the line.
     */
    var y2: Int by attributes
}

/**
 * An SVG text element.
 */
class TEXT : Element("text"), HasOrigin, HasFill {
    override var x: Int by attributes
    override var y: Int by attributes
    override var fill: String by attributes
}

/**
 * An SVG A reference element.
 */
class A : Element("a") {
    /**
     * The reference URL.
     */
    var href: String by RenamedAttribute<String>(attributes, "xlink:href")

    /**
     * Create a rect element inside the reference.
     */
    fun rect(block: RECT.() -> Unit): RECT {
        val rect = RECT()
        rect.block()
        children.add(rect)
        return rect
    }

    /**
     * Create a text element inside the reference.
     */
    fun text(block: TEXT.() -> Unit): TEXT {
        val text = TEXT()
        text.block()
        children.add(text)
        return text
    }
}

/**
 * Create an SVG element.
 */
fun svg(init: SVG.() -> Unit): SVG {
    val svg = SVG()
    svg.init()
    return svg
}

/**
 * A property delegate to allow attributes to be stored with a key different from their name.
 */
private class RenamedAttribute<T>(private val attributes: MutableMap<String, Any?>, private val key: String) :
        ReadWriteProperty<Any?, T> {
    @Suppress("UNCHECKED_CAST")
    public override operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return attributes[key] as T
    }

    public override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        attributes[key] = value
    }
}
