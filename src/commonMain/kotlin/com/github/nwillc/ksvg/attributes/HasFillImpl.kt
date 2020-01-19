package com.github.nwillc.ksvg.attributes

/**
 * A basic implementation of HasFill.
 */
class HasFillImpl(hasAttributes: HasAttributes) : HasFill, HasAttributes by hasAttributes {
    override var fill: String? by attributes
}
