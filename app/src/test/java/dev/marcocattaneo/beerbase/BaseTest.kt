package dev.marcocattaneo.beerbase

import org.junit.Assert.*

abstract class BaseTest {

    protected infix fun Any?.isEqualTo(value: Any?) = assertEquals(value, this)

    protected fun Any?.isNotNull(value: Any?) = assertNotNull(value)

}