package com.guagua.paboo.data.model

import com.guagua.data.bean.SourceDetailBean


data class SourceDetail(
    val id: String,
    val name: String,
    val description: String?,
    val url: String,
    val category: Category,
    val language: Language,
    val country: Country,
) {
    companion object {
        internal fun create(bean: SourceDetailBean) = with(bean) {
            SourceDetail(
                id ?: error("No id founded."),
                name ?: error("No name founded."),
                description ?: "",
                url ?: error("No url found."),
                Category.values().firstOrNull { it.name == category } ?: error("Unknown category."),
                Language.values().firstOrNull { it.name == language } ?: error("Unknown language."),
                Country.values().firstOrNull { it.name == category } ?: error("Unknown category.")
            )
        }
    }
}