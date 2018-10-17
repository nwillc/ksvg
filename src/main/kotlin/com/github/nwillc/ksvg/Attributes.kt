/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

/**
 * An Element has attributes.
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
 * An Element has a stroke associated.
 */
interface HasStroke : HasAttributes {
    /**
     * The stroke color.
     */
    var stroke: String
        get() = attributes["stroke"]!!
        set(value) {
            attributes["stroke"] = value
        }
    /**
     * The stroke width.
     */
    var strokeWidth: Int
        get() = attributes["stroke-width"]!!.toInt()
        set(value) {
            attributes["stroke-width"] = value.toString()
        }
}

/**
 * Element is a shape and therefore has stroke and fill.
 */
interface IsShape : HasStroke, HasFill