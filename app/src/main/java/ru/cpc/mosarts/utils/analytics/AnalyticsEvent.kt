package ru.cpc.mosarts.utils.analytics

interface AnalyticsEvent {
    /**
     * Имя события
     * */
    val name: String

    /**
     * Список параметров события
     * */
    val arguments: Map<String, Any?>
}
