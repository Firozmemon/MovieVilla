package com.firozmemon.movievilla.db

import androidx.room.*
import com.firozmemon.movievilla.db.FavMovies.Companion.MOVIE_ID
import com.firozmemon.movievilla.db.FavMovies.Companion.TABLE_NAME

@Dao
interface FavMoviesDao {

    @Query("SELECT * FROM $TABLE_NAME ORDER BY $MOVIE_ID DESC")
    fun getAllFavMovies(): List<FavMovies>?

    @Query("SELECT * FROM $TABLE_NAME WHERE $MOVIE_ID = :mId LIMIT 1")
    fun getFavMoviesById(mId: Int): FavMovies?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favMovies: FavMovies): Long

    @Query("DELETE FROM $TABLE_NAME WHERE $MOVIE_ID = :mId")
    fun deleteFavMovies(mId: Int): Int

}