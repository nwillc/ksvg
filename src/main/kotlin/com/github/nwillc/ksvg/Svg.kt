/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

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
 * An Element has a Map of attributes.
 */
interface HasAttributes {
    /**
     * A Map of attributes.
     */
    val attributes: MutableMap<String, String>
}

/**
 * An Element has x,y origin.
 */
interface HasOrigin : HasAttributes {
    /**
     * Origin's X coordinate.
     */
    var x: Int
        get() = attributes["x"]!!.toInt()
        set(value) {
            attributes["x"] = value.toString()
        }
    /**
     * Origin's Y coordinate.
     */
    var y: Int
        get() = attributes["y"]!!.toInt()
        set(value) {
            attributes["y"] = value.toString()
        }
}

/**
 * An Element has height and width dimensions.
 */
interface HasDimensions : HasAttributes {
    /**
     * The height dimension.
     */
    var height: Int
        get() = attributes["height"]!!.toInt()
        set(value) {
            attributes["height"] = value.toString()
        }
    /**
     * The width dimension.
     */
    var width: Int
        get() = attributes["width"]!!.toInt()
        set(value) {
            attributes["width"] = value.toString()
        }
}

/**
 * An Element can have a fill color.
 */
interface HasFill : HasAttributes {
    /**
     * The fill color.
     */
    var fill: String
        get() = attributes["fill"]!!
        set(value) {
            attributes["fill"] = value
        }
}

/**
 * An Element can have a style attribute.
 */
interface HasStyle : HasAttributes {
    /**
     * The style attribute.
     */
    var style: String
        get() = attributes["style"]!!
        set(value) {
            attributes["style"] = value
        }
}

/**
 * Abstract SVG named tag with attributes and child elements.
 */
@SvgTagMarker
abstract class Tag(private val name: String) : Element, HasAttributes {
    override val attributes = hashMapOf<String, String>()
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

abstract class TagWithText(name: String) : Tag(name) {
    var body: String
        get() = (children[0] as TextElement).body
        set(value) {
            children.add(TextElement(value))
        }
}

class SVG : Tag("svg"), HasDimensions {

    var viewBox: String
        get() = attributes["viewBox"]!!
        set(value) {
            attributes["viewBox"] = value
        }

    fun rect(init: RECT.() -> Unit): RECT {
        val rect = RECT()
        rect.init()
        children.add(rect)
        return rect
    }

    fun text(init: TEXT.() -> Unit): TEXT {
        val text = TEXT()
        text.init()
        children.add(text)
        return text
    }

    fun circle(init: CIRCLE.() -> Unit): CIRCLE {
        val circle = CIRCLE()
        circle.init()
        children.add(circle)
        return circle
    }

    fun line(init: LINE.() -> Unit): LINE {
        val line = LINE()
        line.init()
        children.add(line)
        return line
    }

    fun a(block: A.() -> Unit): A {
        val a = A()
        a.block()
        children.add(a)
        return a
    }
}

class CIRCLE : Tag("circle"), HasAttributes, HasFill {
    var cx: Int
        get() = attributes["cx"]!!.toInt()
        set(value) {
            attributes["cx"] = value.toString()
        }
    var cy: Int
        get() = attributes["cy"]!!.toInt()
        set(value) {
            attributes["cy"] = value.toString()
        }
    var r: Int
        get() = attributes["r"]!!.toInt()
        set(value) {
            attributes["r"] = value.toString()
        }
}

class TITLE : TagWithText("title")

class RECT : Tag("rect"), HasFill, HasOrigin, HasDimensions, HasStyle {
    fun title(block: TITLE.() -> Unit): TITLE {
        val title = TITLE()
        title.block()
        children.add(title)
        return title
    }
}

class LINE : Tag("line"), HasStyle {
    var x1: Int
        get() = attributes["x1"]!!.toInt()
        set(value) {
            attributes["x1"] = value.toString()
        }
    var y1: Int
        get() = attributes["y1"]!!.toInt()
        set(value) {
            attributes["y1"] = value.toString()
        }
    var x2: Int
        get() = attributes["x2"]!!.toInt()
        set(value) {
            attributes["x2"] = value.toString()
        }
    var y2: Int
        get() = attributes["y2"]!!.toInt()
        set(value) {
            attributes["y2"] = value.toString()
        }
}

class TEXT : TagWithText("text"), HasOrigin, HasFill

class A : Tag("a") {
    var href: String
        get() = attributes["xlink:href"]!!
        set(value) {
            attributes["xlink:href"] = value
        }

    fun rect(block: RECT.() -> Unit): RECT {
        val rect = RECT()
        rect.block()
        children.add(rect)
        return rect
    }

    fun text(block: TEXT.() -> Unit): TEXT {
        val text = TEXT()
        text.block()
        children.add(text)
        return text
    }
}

fun svg(init: SVG.() -> Unit): SVG {
    val svg = SVG()
    svg.init()
    return svg
}