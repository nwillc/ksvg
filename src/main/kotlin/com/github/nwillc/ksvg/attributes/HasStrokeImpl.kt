package com.github.nwillc.ksvg.attributes

/**
 * A basic implementation of HasStroke.
 */
class HasStrokeImpl(hasAttributes: HasAttributes) : HasStroke, HasAttributes by hasAttributes {
    override var stroke: String? by attributes
    override var strokeWidth: String? by AttributeProperty()
}
