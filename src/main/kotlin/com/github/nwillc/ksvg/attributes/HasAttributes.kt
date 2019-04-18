package com.github.nwillc.ksvg.attributes

import com.github.nwillc.ksvg.elements.SVG

/**
 * This interface describes how SVG handles attributes.
 */
interface HasAttributes {
    /**
     * The attributes are simply labels with associated values.
     */
    val attributes: MutableMap<String, String?>
    /**
     * Should we attempt to validated values correctness when assign them?
     */
    var validation: Boolean
    /**
     * Return the attribute map, with possible processing based on the renderMode.
     * @param renderMode which rendering mode the attributes are being accessed for.
     */
    fun getAttributes(renderMode: SVG.RenderMode): Map<String, String?>
    /**
     * All things that can have attributes can have an id attribute.
     */
    var id: String?
}
