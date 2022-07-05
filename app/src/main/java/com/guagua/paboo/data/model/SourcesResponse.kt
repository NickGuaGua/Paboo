package com.guagua.paboo.data.model

import com.guagua.data.bean.SourcesResponseBean

data class SourcesResponse(
    val sources: List<SourceDetail>,
    val totalResults: Int
) {
    companion object {
        internal fun create(bean: SourcesResponseBean) = with(bean) {
            SourcesResponse(
                sources?.map { SourceDetail.create(it) } ?: emptyList(),
                totalResults ?: 0
            )
        }
    }
}