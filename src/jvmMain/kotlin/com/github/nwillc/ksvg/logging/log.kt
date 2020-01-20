package com.github.nwillc.ksvg.logging

import com.github.nwillc.ksvg.elements.SVG
import java.util.logging.Level
import java.util.logging.Logger

private val logger = Logger.getLogger(SVG::class.java.name)

internal actual fun log(logLevel: LogLevel, message: String) =
    logger.log(
        when (logLevel) {
            LogLevel.DEBUG -> Level.FINE
            LogLevel.INFO -> Level.INFO
            LogLevel.WARN -> Level.WARNING
            LogLevel.ERROR -> Level.SEVERE
        },
        message
    )

