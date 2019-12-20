package com.android.mealeeuxs.newmyreads.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Book book);

    @Update
    void update(Book book);

    @Delete
    void delete(Book book);

    @Query("DELETE FROM book_table")
    void deleteAll();

    // =========
    // ALL BOOKS
    // =========

    @Query("SELECT * FROM book_table ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();

    @Query("SELECT * FROM book_table " +
            "WHERE UPPER(title) LIKE '%' || UPPER(:title) || '%' " +
            "ORDER BY title " +
            "ASC")
    LiveData<List<Book>> getBooksByTitle(String title);

    @Query("SELECT * FROM book_table " +
            "WHERE UPPER(authors) LIKE '%' || UPPER(:author) || '%' " +
            "ORDER BY title " +
            "ASC")
    LiveData<List<Book>> getBooksByAuthor(String author);

    @Query("SELECT * FROM book_table " +
            "WHERE UPPER(title) LIKE '%' || UPPER(:title) || '%' " +
            "AND UPPER(authors) LIKE '%' || UPPER(:author) || '%' " +
            "ORDER BY title " +
            "ASC")
    LiveData<List<Book>> getBooksByTitleAndAuthor(String title, String author);

    // ==============
    // BOOKS BY STATE
    // ==============

    @Query("SELECT * FROM book_table WHERE type = :type ORDER BY title ASC")
    LiveData<List<Book>> getBooksByState(int type);

    @Query("SELECT * FROM book_table " +
            "WHERE UPPER(title) LIKE '%' || UPPER(:title) || '%' " +
            "AND type = :type " +
            "ORDER BY title " +
            "ASC")
    LiveData<List<Book>> getBooksByStateAndTitle(int type, String title);

    @Query("SELECT * FROM book_table " +
            "WHERE UPPER(authors) LIKE '%' || UPPER(:author) || '%' " +
            "AND type = :type " +
            "ORDER BY title " +
            "ASC")
    LiveData<List<Book>> getBooksByStateAndAuthor(int type, String author);

    @Query("SELECT * FROM book_table " +
            "WHERE UPPER(title) LIKE '%' || UPPER(:title) || '%' " +
            "AND UPPER(authors) LIKE '%' || UPPER(:author) || '%' " +
            "AND type = :type " +
            "ORDER BY title " +
            "ASC")
    LiveData<List<Book>> getBooksByStateAndTitleAndAuthor(int type, String title, String author);
}
