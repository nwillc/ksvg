package com.github.nwillc.ksvg.attributes

/**
 * A basic implementation of HasDimensions.
 */
class HasDimensionsImpl(hasAttributes: HasAttributes) : HasDimensions, HasAttributes by hasAttributes {
    override var height: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
    override var width: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
}
