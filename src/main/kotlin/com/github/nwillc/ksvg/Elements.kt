/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

/**
 * Indicates something is an SVG DSL marker.
 */
@DslMarker
annotation class SvgTagMarker

/**
 * Indicate a class's SVG representation can be rendered.
 */
interface Element {
    /**
     * Render the SVG representation of a class to a StringBuffer.
     */
    fun render(builder: StringBuilder)
}

/**
 * An Element with a basic text body between it's SVG tags.
 */
class TextElement(internal val body: String) : Element {
    override fun render(builder: StringBuilder) {
        builder.append(body)
    }
}

/**
 * Abstract SVG named element with attributes and child elements.
 * @param name The svg tag of the element.
 */
@SvgTagMarker
abstract class Tag(private val name: String) : Element, HasAttributes {
    override val attributes = hashMapOf<String, Any?>()
    /**
     * The children elements contained in this element.
     */
    protected val children = arrayListOf<Element>()

    override fun render(builder: StringBuilder) {
        builder.append("<$name")
        if (attributes.isNotEmpty()) {
            builder.append(attributes.entries.joinToString(prefix = " ", separator = " ") {
                it.key + "=\"" + it.value + '"'
            })
        }
        if (children.isEmpty()) {
            builder.append("/>\n")
        } else {
            builder.append('>')
            children.forEach {
                it.render(builder)
            }
            builder.append("</$name>\n")
        }
    }
}

/**
 * An SVG element that has a text body.
 */
abstract class TagWithText(name: String) : Tag(name) {
    /**
     * The text body string.
     */
    var body: String
        get() = (children[0] as TextElement).body
        set(value) {
            children.add(TextElement(value))
        }
}

/**
 * The SVG element itself.
 */
class SVG : Tag("svg"), HasDimensions {
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
}

/**
 * The SVG circle element.
 */
class CIRCLE : Tag("circle"), HasAttributes, IsShape {
    override var stroke: String by attributes
    override var fill: String by attributes

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
 * An SVG title element.
 */
class TITLE : TagWithText("title")

/**
 * An SVG rect element.
 */
class RECT : Tag("rect"), HasOrigin, HasDimensions, IsShape {
    override var x: Int by attributes
    override var y: Int by attributes
    override var height: Int by attributes
    override var width: Int by attributes
    override var stroke: String by attributes
    override var fill: String by attributes

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
class LINE : Tag("line"), HasStroke {
    override var stroke: String by attributes

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
class TEXT : TagWithText("text"), HasOrigin, HasFill {
    override var x: Int by attributes
    override var y: Int by attributes
    override var fill: String by attributes
}

/**
 * An SVG A reference element.
 */
class A : Tag("a") {
    /**
     * The reference URL.
     */
    var href: String
        get() = attributes["xlink:href"]!! as String
        set(value) {
            attributes["xlink:href"] = value
        }

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