package com.firozmemon.movievilla.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firozmemon.movievilla.db.FavMovies.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class FavMovies(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = MOVIE_ID) var id: Int = 0,
    @ColumnInfo(name = MOVIE_TITLE) var title: String,
    @ColumnInfo(name = MOVIE_POSTER_IMG) var imgUrl: String,
) {

    companion object {
        const val TABLE_NAME = "favourite_movies"
        const val MOVIE_ID = "mId"
        const val MOVIE_TITLE = "title"
        const val MOVIE_POSTER_IMG = "posterImg"
    }

}