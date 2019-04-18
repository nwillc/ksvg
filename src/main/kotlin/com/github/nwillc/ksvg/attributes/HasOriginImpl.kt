package com.github.nwillc.ksvg.attributes

/**
 * A Basic implementation of HasOrigin.
 */
class HasOriginImpl(hasAttributes: HasAttributes) : HasOrigin, HasAttributes by hasAttributes {
    override var x: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
    override var y: String? by AttributeProperty(type = AttributeType.LengthOrPercentage)
}
