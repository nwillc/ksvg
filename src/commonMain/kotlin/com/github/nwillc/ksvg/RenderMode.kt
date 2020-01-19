package com.github.nwillc.ksvg

/**
 * The mode to employ when rendering. Some Elements must render differently when used inline in HTML5 or in a
 * standalone SVG file.
 */
enum class RenderMode {
    /**
     * Render as inline SVG designed to appear inline in HTML5.
     */
    INLINE,
    /**
     * Render as an SVG `.svg` file format.
     */
    FILE
}
