package com.exactaworks.exactabank.dto

import java.io.Serializable

data class PageResponse<T>(
    var data: T,
    var meta: PageMetaData
) : Serializable {
}

data class PageMetaData(
    var currentPage: Int,
    var hasNext: Boolean,
    var totalRecords: Long?,
    var totalPages: Int?
) {}
