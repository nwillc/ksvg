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

interface Element {
    fun render(builder: StringBuilder)
}

class TextElement(internal val text: String) : Element {
    override fun render(builder: StringBuilder) {
        builder.append(text)
    }
}

interface HasAttributes {
    val attributes: MutableMap<String, String>
}

interface HasOrigin : HasAttributes {
    var x: Int
        get() = attributes["x"]!!.toInt()
        set(value) {
            attributes["x"] = value.toString()
        }
    var y: Int
        get() = attributes["y"]!!.toInt()
        set(value) {
            attributes["y"] = value.toString()
        }
}

interface HasDimensions : HasAttributes {
    var height: Int
        get() = attributes["height"]!!.toInt()
        set(value) {
            attributes["height"] = value.toString()
        }
    var width: Int
        get() = attributes["width"]!!.toInt()
        set(value) {
            attributes["width"] = value.toString()
        }
}

interface HasFill : HasAttributes {
    var fill: String
        get() = attributes["fill"]!!
        set(value) {
            attributes["fill"] = value
        }
}

interface HasStyle : HasAttributes {
    var style: String
        get() = attributes["style"]!!
        set(value) {
            attributes["style"] = value
        }
}

@SvgTagMarker
abstract class Tag(private val name: String) : Element, HasAttributes {
    override val attributes = hashMapOf<String, String>()
    val children = arrayListOf<Element>()

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
        get() = (children[0] as TextElement).text
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

class RECT : Tag("rect"), HasOrigin, HasDimensions, HasStyle {
    fun title(block: TITLE.() -> Unit): TITLE {
        val title = TITLE()
        title.block()
        children.add(title)
        return title
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