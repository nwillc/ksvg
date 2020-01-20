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
import com.github.nwillc.ksvg.RenderMode
import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.test.Test
import kotlin.test.assertEquals

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
        assertEquals((svg.children[0] as A).href, url)
        assertEquals(svg.toString(), "<svg>\n<a xlink:href=\"$url\">\n<text>$bodyValue</text>\n<rect/>\n</a>\n</svg>\n")
    }

    @Test
    fun `format href properly in file mode`() {
        val url = faker.internet().url()
        svg.a {
            href = url
        }

        StringBuilder().apply {
            svg.render(this, RenderMode.INLINE)
            assertEquals(this.toString(), "<svg>\n<a xlink:href=\"$url\"/>\n</svg>\n")
        }

        StringBuilder().apply {
            svg.render(this, RenderMode.FILE)
            assertEquals(this.toString(),
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<svg xmlns=\"http://www.w3.org/2000/svg\">\n" +
                    "<a href=\"$url\"/>\n" +
                    "</svg>\n"
            )
        }
    }
}
