package com.store.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class RecordHolder<T>(
    var totalRecords: Int,
    var records: T
)