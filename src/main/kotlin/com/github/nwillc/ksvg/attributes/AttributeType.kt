/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
 * WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL
 * THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR
 * CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING
 * FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,
 * NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION
 * WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 *
 */

package com.github.nwillc.ksvg.attributes

import com.github.nwillc.slf4jkext.getLogger

/**
 *  An enumeration of attribute types and the how to verify if a value is of this type.
 */
enum class AttributeType {
    /**
     * No validations will be performed on type none.
     */
    None(),
    /**
     * A length value, a number and optional unit.
     */
    Length() {
        override fun verify(value: String) {
            if (!(value matches length))
                throw IllegalArgumentException("Value ($value) is not a valid length or percentage: $length")
        }
    },
    /**
     * A length or percentage, a number and optional unit or percent sign.
     */
    LengthOrPercentage() {
        override fun verify(value: String) {
            if (!(value matches lengthPercent))
                throw IllegalArgumentException(
                    "Value ($value) is not a valid length or percentage: $lengthPercent"
                )
        }
    },
    /**
     * A list of numbers separated by white space or commas.
     */
    NumberList() {
        override fun verify(value: String) {
            if (!(value matches numberList))
                throw IllegalArgumentException("Value ($value) is not a valid number list: $number")
        }
    },
    /**
     * Any non empty character string without whitespace.
     */
    IdName() {
        override fun verify(value: String) {
            if (!(value matches idName))
                throw IllegalArgumentException("Value ($value) is not a valid id: $idName")
        }
    },
    /**
     * A relative URL by id name.
     */
    Relative() {
        override fun verify(value: String) {
            if (!(value matches relative))
                throw IllegalArgumentException("Value ($value) is not a valid id: $relative")
        }
    },
    /**
     * A set of commands and coordinates.
     */
    Path() {
        override fun verify(value: String) {
            if (!(value matches path))
                throw IllegalArgumentException("Value ($value) is not a valid path: $path")
        }
    },

    /**
     * CSS class names work imperfectly in some browsers so warn about them.
     */
    CssClass() {
        override fun verify(value: String) {
            logger.warn("CSS support is incomplete in some browsers, know issues in IE and Firefox.")
        }
    };

    /**
     * Constants.
     */
    private companion object {
        private val logger = getLogger<AttributeType>()
        private val number = Regex("[+-]?[0-9]*.?[0-9]+")
        private val separator = Regex("\\s*,?\\s+")
        private val lengthUnits = "em|ex|px|in|cm|mm|pt|pc"
        private val length = Regex("$number($lengthUnits)?")
        private val lengthPercent = Regex("$number($lengthUnits|%)?")
        private val numberList = Regex("($number($separator)?)+")
        private val idName = Regex("[^\\s]+")
        private val relative = Regex("#$idName")
        private val path = Regex("(($number)|[\\smMlLhHvVcCsSqQtTaAzZ])+")
    }

    /**
     * Verify a value is of the AttributeType.
     */
    open fun verify(value: String) { /* No verifications by default */ }
}
