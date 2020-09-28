package com.leptron.leptronquiz.data

enum class DataError {
    GENERIC,
    DISK,
    DISK_NOT_UPDATED,
    HOST_NOT_FOUND,
    DATA_NOT_FOUND,
    NETWORK_ERROR_FROM_DISK,
    SHOULD_NEVER_BE_CALLED,
    FAILED_TO_DISCOVER_SERVICES
}