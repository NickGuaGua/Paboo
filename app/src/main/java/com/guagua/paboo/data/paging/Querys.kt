package com.guagua.paboo.data.paging

import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.model.Country

data class SearchQuery(
    var query: String? = null,
    var searchIn: String? = null,
    var source: String? = null,
    var domains: String? = null,
    var excludeDomains: String? = null,
    var from: String? = null,
    var to: String? = null,
    var language: String? = null,
    var sortBy: String? = null
)