package com.example.jll.hackernewsofflinefirst.utils

import com.example.jll.hackernewsofflinefirst.BuildConfig
import java.text.SimpleDateFormat
import java.util.*

object Utils {
  fun convertUtcDatetimeToDate(utcDatetime: String): Date {
    val serverSdf = SimpleDateFormat(BuildConfig.HACKER_NEWS_TIMESTAMP_FORMAT)
    val utcZone = TimeZone.getTimeZone("UTC")
    serverSdf.timeZone = utcZone
    return serverSdf.parse(utcDatetime)
  }
}