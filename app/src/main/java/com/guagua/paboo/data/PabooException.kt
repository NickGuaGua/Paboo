package com.guagua.paboo.data

data class PabooException(val code: String?, override val message: String?) : Exception() {
    companion object {
        fun from(throwable: Throwable) : PabooException {
            if (throwable is PabooException) return throwable
            return PabooException(null, throwable.message)
        }
    }
}