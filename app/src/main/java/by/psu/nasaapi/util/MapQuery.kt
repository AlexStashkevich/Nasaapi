package by.psu.nasaapi.util

object MapQuery {
    fun defaultQuery(): Map<String, String> = mapOf("api_key" to "DEMO_KEY")

    fun byDateQuery(date: String): Map<String, String> = mapOf("api_key" to "DEMO_KEY", "date" to date)
}