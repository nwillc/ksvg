/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is
 * hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE
 * INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE
 * FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS
 * OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING
 * OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg.elements

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.util.logging.Logger
import java.util.logging.SimpleFormatter
import java.util.logging.StreamHandler

internal class USETest : HasSvg(true) {
    private val formatter = SimpleFormatter()
    private val stream = ByteArrayOutputStream()
    private val handler = StreamHandler(stream, formatter)

    companion object {
        val logger = Logger.getLogger(USE::javaClass.name)!!
    }

    @BeforeEach
    fun setup() {
        stream.reset()
        logger.addHandler(handler)
    }

    @AfterEach
    fun tearDown() {
        logger.removeHandler(handler)
    }

    @Test
    internal fun testValidationWarning() {
        svg.validation = true
        svg.use {
        }
        handler.flush()
        assertThat(stream.toString()).contains("The use tags href has compatibility issues with Safari")
    }

    @Test
    internal fun testNoValidationWarning() {
        svg.validation = false
        svg.use {
        }
        handler.flush()
        assertThat(stream.toString()).isEmpty()
    }
}
