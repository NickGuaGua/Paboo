package com.guagua.paboo.data.model

enum class Status {
    OK, ERROR;

    companion object {
        private val map = values().associateBy { it.name.lowercase() }
        fun getStatus(status: String?): Status? = status?.let { map[it] }
    }
}