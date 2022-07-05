package com.guagua.paboo.data.model

enum class Status {
    SUCCESS, ERROR;

    companion object {
        fun from(status: String?): Status = if (status == "ok") SUCCESS else ERROR
    }
}