/*
 * Copyright (c) 2020, nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

internal const val EIGHT_BIT_START = 127.toChar()

/**
 * Escapes special characters of an Appendable for HTML output. Handles double quotes,
 * greater than, less than, ampersand, and characters greater than eight bit.
 * @param csq A sequence of characters to HTML escape.
 */
fun Appendable.escapeHTML(csq: CharSequence) {
    for (c in csq) {
        when {
            c > EIGHT_BIT_START || c == '"' || c == '<' || c == '>' || c == '&' -> {
                append("&#")
                append(c.code.toString())
                append(';')
            }
            else -> append(c)
        }
    }
}

/**
 * Escapes special characters of a String for HTML output. Handles double quotes,
 * greater than, less than, ampersand, and characters greater than eight bit.
 */
fun String.escapeHTML(): String {
   return usingAppendable(Appendable::escapeHTML)
}

fun Appendable.toAttributeName(csq: CharSequence) {
    csq.forEach {
        if (it in 'A'..'Z') {
            append('-')
            append(it.lowercaseChar())
        } else {
            append(it)
        }
    }
}

fun String.toAttributeName(): String = usingAppendable(Appendable::toAttributeName)

fun String.usingAppendable(function: Appendable.(CharSequence) -> Unit): String =
    StringBuilder().also { it.function(this) }.toString()
