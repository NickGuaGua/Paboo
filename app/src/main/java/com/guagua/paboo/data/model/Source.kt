package com.guagua.paboo.data.model

import androidx.room.ColumnInfo
import com.guagua.data.bean.SourceBean

data class Source(
    @ColumnInfo(name = "source_id") val id: String?,
    @ColumnInfo(name = "source_name") val name: String,
) {
    companion object {
        internal fun create(bean: SourceBean) = with(bean) {
            Source(id, name ?: "")
        }
    }
}