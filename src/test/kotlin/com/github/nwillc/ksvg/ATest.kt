/*
 * Copyright 2018 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.ksvg

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.io.StringWriter

internal class ATest : HasSvg() {

    @Test
    internal fun testHref() {
        val url = "http://www.google.com"
        svg.a {
            href = url
            text {
                body = "google.com"
            }
            rect {
            }
        }
        assertThat((svg.children[0] as A).href).isEqualTo(url)
        assertThat(svg.toString()).isEqualTo("<svg>\n<a xlink:href=\"http://www.google.com\">\n<text>google.com</text>\n<rect/>\n</a>\n</svg>\n")
    }

    @Test
    internal fun testAFileMode() {
        svg.a {
            href = "http://www.google.com"
        }

        StringWriter().use {
            svg.render(it, RenderMode.INLINE)
            assertThat(it.toString()).isEqualTo("<svg>\n<a xlink:href=\"http://www.google.com\"/>\n</svg>\n")
        }

        StringWriter().use {
            svg.render(it, RenderMode.FILE)
            assertThat(it.toString()).isEqualTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<svg xmlns=\"http://www.w3.org/2000/svg\">\n" +
                        "<a href=\"http://www.google.com\"/>\n" +
                        "</svg>\n"
            )
        }
    }
}
