package dev.marcocattaneo.beerbase

import org.junit.Assert.*

abstract class BaseTest {

    protected infix fun Any?.isEqualTo(value: Any?) = assertEquals(value, this)

    protected fun Any?.isNotNull(value: Any?) = assertNotNull(value)

    protected fun Any?.isNull(value: Any?) = assertNull(value)

    protected infix fun Any?.isTrue(value: Boolean) = assert(value)

}