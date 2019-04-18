package com.github.nwillc.ksvg.attributes

import com.github.nwillc.ksvg.elements.SVG

/**
 * A basic implementation of HasAttributes.
 */
open class HasAttributesImpl(override var validation: Boolean) : HasAttributes {
    override val attributes: MutableMap<String, String?> = hashMapOf()
    override var id: String? by AttributeProperty(type = AttributeType.IdName)
    override fun getAttributes(renderMode: SVG.RenderMode) = attributes
}
