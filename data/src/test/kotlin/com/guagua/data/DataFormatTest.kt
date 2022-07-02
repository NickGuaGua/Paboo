package com.guagua.data

import com.guagua.data.extension.parseUTCDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DataFormatTest {

    @Test
    fun `test parsing UTC date string to time in milliseconds`() {
        val date = "2022-05-29T09:17:15Z"
        assertEquals(1653815835000, date.parseUTCDate())
    }
}