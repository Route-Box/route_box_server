package com.routebox.routebox.exception

import java.io.PrintWriter
import java.io.StringWriter

object ExceptionUtils {
    fun getExceptionStackTrace(ex: Exception): String {
        val stringWriter = StringWriter()
        ex.printStackTrace(PrintWriter(stringWriter))
        return stringWriter.toString()
    }
}
