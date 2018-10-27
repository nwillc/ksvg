/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import java.io.Writer

/**
 * The SVG element itself.
 */
@SvgTagMarker
class SVG : Element("svg"), HasDimensions {
    override var height: String by attributes
    override var width: String by attributes

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

    override fun getAttributes(renderMode: RenderMode): Map<String, Any?> {
        return if (renderMode == RenderMode.FILE) {
            val map = mutableMapOf<String, Any?>("xmlns" to "http://www.w3.org/2000/svg")
            map.putAll(attributes)
            map
        } else {
            super.getAttributes(renderMode)
        }
    }

    override fun render(writer: Writer, renderMode: RenderMode) {
        if (renderMode == RenderMode.FILE) {
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n")
        }
        super.render(writer, renderMode)
    }
}
