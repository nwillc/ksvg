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

/**
 * An abstract container element which provides factories for general sub elements.
 */
abstract class Container(name: String, validateAttributes: Boolean) : Element(name, validateAttributes) {
    /**
     * Create a rect element in this svg.
     */
    fun rect(init: RECT.() -> Unit): RECT {
        val rect = RECT(validateAttributes)
        rect.init()
        children.add(rect)
        return rect
    }

    /**
     * Create a text element in this svg.
     */
    fun text(init: TEXT.() -> Unit): TEXT {
        val text = TEXT(validateAttributes)
        text.init()
        children.add(text)
        return text
    }

    /**
     * Create a circle element in this svg.
     */
    fun circle(init: CIRCLE.() -> Unit): CIRCLE {
        val circle = CIRCLE(validateAttributes)
        circle.init()
        children.add(circle)
        return circle
    }

    /**
     * Create a polygon element in this svg.
     */
    fun polygon(init: POLYGON.() -> Unit): POLYGON {
        val polygon = POLYGON(validateAttributes)
        polygon.init()
        children.add(polygon)
        return polygon
    }

    /**
     * Create a line element in this svg.
     */
    fun line(init: LINE.() -> Unit): LINE {
        val line = LINE(validateAttributes)
        line.init()
        children.add(line)
        return line
    }

    /**
     * Create an a reference element in this svg.
     */
    fun a(block: A.() -> Unit): A {
        val a = A(validateAttributes)
        a.block()
        children.add(a)
        return a
    }

    /**
     * Create an a path element in this svg.
     */
    fun path(block: PATH.() -> Unit): PATH {
        val path = PATH(validateAttributes)
        path.block()
        children.add(path)
        return path
    }

    /**
     * Create a group element in this svg.
     */
    fun g(init: G.() -> Unit): G {
        val group = G(validateAttributes)
        group.init()
        children.add(group)
        return group
    }

    /**
     * Create a group element in this svg.
     */
    fun use(init: USE.() -> Unit): USE {
        val use = USE(validateAttributes)
        use.init()
        children.add(use)
        return use
    }
}
