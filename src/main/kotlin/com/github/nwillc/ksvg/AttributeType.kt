/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

private val NUMBER_REGEX = Regex("[+-]?[0-9]*.?[0-9]+")
private val SEPARATOR_REGEX = Regex("\\s*,?\\s+")
private val UNITS = "em|ex|px|in|cm|mm|pt|pc"
private val LENGTH_REGEX = Regex("$NUMBER_REGEX($UNITS)?")
private val LENGTH_OR_PERCENTAGE_REGEX = Regex("$NUMBER_REGEX($UNITS|%)?")
private val NUMBER_LIST_REGEX = Regex("($NUMBER_REGEX($SEPARATOR_REGEX)?)+")

/**
 *  An enumeration of attribute types and the how to verify if a value is of this type.
 */
enum class AttributeType {
    Length() {
        override fun verify(value: Any?) {
            if (value is String) {
                if (value matches LENGTH_REGEX) {
                    return
                }
            }
            throw IllegalArgumentException("Value ($value) is not a valid length or percentage")
        }
    },
    LengthOrPercentage() {
        override fun verify(value: Any?) {
            if (value is String) {
                if (value matches LENGTH_OR_PERCENTAGE_REGEX) {
                    return
                }
            }
            throw IllegalArgumentException("Value ($value) is not a valid length or percentage")
        }
    },
    NumberList() {
        override fun verify(value: Any?) {
            if (value is String) {
                if (value matches NUMBER_LIST_REGEX) {
                    return
                }
            }
            throw IllegalArgumentException("Value ($value) is not a valid number list")
        }
    };

    /**
     * Verify a value is of the AttributeType.
     */
    abstract fun verify(value: Any?)
}
