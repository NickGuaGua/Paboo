package com.guagua.paboo.presentation.navigation

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

sealed class Screen(val path: String, val isTabScreen: Boolean = false) {
    object Main : Screen("main")
    object Home : Screen("home", true)
    class NewsDetail(articleUrl: String): Screen(path) {
        // Avoid a issue: https://stackoverflow.com/questions/68950770/passing-url-as-a-parameter-to-jetpack-compose-navigation
        val articleUrl: String = URLEncoder.encode(articleUrl, StandardCharsets.UTF_8.toString())

        override fun hashCode(): Int = super.hashCode()

        override fun equals(other: Any?): Boolean {
            return (other as? NewsDetail)?.articleUrl == articleUrl
        }

        override fun toString(): String {
            return "${this::class.java.simpleName}(articleUrl = $articleUrl)"
        }

        companion object {
            const val KEY_ARTICLE_URL = "articleUrl"
            const val path = "newsDetail/{articleUrl}"
        }
    }
    object Category : Screen("search", true)
}