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

package com.github.nwillc.ksvg.elements

import com.github.javafaker.Faker
import java.io.StringWriter
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("A tag")
class ATest : HasSvg() {
    private val faker = Faker()

    @Test
    fun `format href properly inline`() {
        val url = faker.internet().url()
        val bodyValue = faker.lorem().fixedString(20)

        svg.a {
            href = url
            text {
                body = bodyValue
            }
            rect {
            }
        }
        assertThat((svg.children[0] as A).href).isEqualTo(url)
        assertThat(svg.toString()).isEqualTo("<svg>\n<a xlink:href=\"$url\">\n<text>$bodyValue</text>\n<rect/>\n</a>\n</svg>\n")
    }

    @Test
    fun `format href properly in file mode`() {
        val url = faker.internet().url()
        svg.a {
            href = url
        }

        StringWriter().use { writer ->
            svg.render(writer, SVG.RenderMode.INLINE)
            assertThat(writer.toString()).isEqualTo("<svg>\n<a xlink:href=\"$url\"/>\n</svg>\n")
        }

        StringWriter().use { writer ->
            svg.render(writer, SVG.RenderMode.FILE)
            assertThat(writer.toString()).isEqualTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<svg xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<a href=\"$url\"/>\n" +
                    "</svg>\n"
            )
        }
    }
}
