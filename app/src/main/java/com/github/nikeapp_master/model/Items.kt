package com.github.nikeapp_master.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "items")
data class Items(
    @ColumnInfo(name = "id")
    val list: List<Info>
) {
    data class Info(
        @ColumnInfo(name = "author")
        val author: String,
        val current_vote: String,
        @ColumnInfo(name = "id")
        val defid: Int,
        val definition: String,
        val example: String,
        val permalink: String,
        val sound_urls: List<Any>,
        val thumbs_down: Int,
        val thumbs_up: Int,
        val word: String,
        val written_on: String
    )
}

