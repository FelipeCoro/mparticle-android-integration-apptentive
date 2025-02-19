package com.mparticle.kits

import com.apptentive.android.sdk.ApptentiveLog
import kotlin.Any
import kotlin.Exception
import kotlin.String

internal object CustomDataParser {
    fun parseCustomData(map: Map<String, String?>): Map<String, Any?> {
        val res = HashMap<String, Any?>()
        for ((key, value) in map) {
            res[key] = parseValue(value)
        }
        return res
    }

    @JvmStatic
    fun parseValue(value: String?): Any? {
        return try {
            if (value != null) parseValueGuarded(value) else null
        } catch (e: Exception) {
            ApptentiveLog.e(e, "Unable to parse value: '%s'", value)
            value
        }
    }

    private fun parseValueGuarded(value: String): Any {
        // check for boolean
        if ("true".equals(value, true) || "false".equals(value,true)) {
            return value.toBoolean()
        }

        // check for number
        val number = StringUtils.tryParseNumber(value)
        return number ?: value

        // default to the original value
    }
}