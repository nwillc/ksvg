/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg.elements

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.util.logging.Logger
import java.util.logging.SimpleFormatter
import java.util.logging.StreamHandler


internal class USETest : HasSvg(true) {

    @Test
    internal fun testValidationWarning() {
        val logger = Logger.getLogger(USE::javaClass.name)
        val formatter = SimpleFormatter()
        ByteArrayOutputStream().use {
            val handler = StreamHandler(it, formatter)
            logger.addHandler(handler)
            svg.validation = true
            it.reset()
            svg.use {

            }
            handler.flush()
            assertThat(it.toString()).contains("The use tags href has compatibility issues with Safari")
            logger.removeHandler(handler)
        }

    }

    @Test
    internal fun testNoValidationWarning() {
        val logger = Logger.getLogger(USE::javaClass.name)
        val formatter = SimpleFormatter()
        ByteArrayOutputStream().use {
            val handler = StreamHandler(it, formatter)
            logger.addHandler(handler)
            svg.validation = false
            svg.use {

            }
            handler.flush()
            assertThat(it.toString()).isEmpty()
            logger.removeHandler(handler)
        }
    }
}
