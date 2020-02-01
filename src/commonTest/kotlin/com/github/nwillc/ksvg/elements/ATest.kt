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

package com.github.nwillc.ksvg.elements

import com.github.nwillc.ksvg.RenderMode
import com.github.nwillc.ksvg.testing.HasSvg
import kotlin.test.Test
import kotlin.test.assertEquals

class ATest : HasSvg() {
    @Test
    fun hrefInline() {
        val url = URL
        val bodyValue = QUOTE

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
    fun hrefFileMode() {
        val url = URL
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

    companion object Fixtures {
        const val URL = "http://google.com"
        val QUOTE = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec in lacinia nulla. In congue, nisl at
            porttitor interdum, turpis nulla facilisis nisi, eget congue nulla sapien a augue. Praesent eu nulla
            sed erat commodo maximus. Vestibulum ex tellus, efficitur eu risus eget, faucibus cursus ipsum. Nam efficitur
            sagittis nisl nec porttitor. Cras quis congue augue. Morbi sed dapibus urna, eu pellentesque tortor. Fusce
            nibh elit, posuere at massa eget, venenatis fringilla odio. Aenean tortor nisi, egestas a metus vitae,
            fermentum scelerisque risus. Aliquam ultricies nisl hendrerit lectus tincidunt, id porta ex pretium.
            Pellentesque consequat, dui rutrum elementum varius, odio nisl imperdiet neque, et varius ante est et augue.
            Pellentesque ornare, ante a pellentesque malesuada, ligula mauris luctus diam, sed porttitor metus enim a
            dolor.
        """.trimIndent()
    }
}
