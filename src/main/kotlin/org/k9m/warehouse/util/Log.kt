package org.k9m.warehouse.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

val loggers = ConcurrentHashMap<String, Logger>()

@Suppress("unused")
inline val <reified T> T.log get() = loggers.computeIfAbsent(T::class.java.name) { LoggerFactory.getLogger(T::class.java.name) }
