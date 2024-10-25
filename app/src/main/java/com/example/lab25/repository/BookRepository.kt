package com.example.lab25.repository

// BookRepository.kt
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.lab25.data.BookDBHelper
import com.example.lab25.model.Book

class BookRepository(context: Context) {
    private val dbHelper = BookDBHelper(context)

    fun addBook(book: Book): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(BookDBHelper.COLUMN_TITLE, book.title)
            put(BookDBHelper.COLUMN_AUTHOR, book.author)
            put(BookDBHelper.COLUMN_STATUS, book.status)
        }
        return db.insert(BookDBHelper.TABLE_BOOKS, null, values)
    }

    fun updateBook(book: Book): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(BookDBHelper.COLUMN_TITLE, book.title)
            put(BookDBHelper.COLUMN_AUTHOR, book.author)
            put(BookDBHelper.COLUMN_STATUS, book.status)
        }
        return db.update(BookDBHelper.TABLE_BOOKS, values, "${BookDBHelper.COLUMN_ID} = ?", arrayOf(book.id.toString()))
    }

    fun deleteBook(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(BookDBHelper.TABLE_BOOKS, "${BookDBHelper.COLUMN_ID} = ?", arrayOf(id.toString()))
    }

    fun getBooks(): List<Book> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            BookDBHelper.TABLE_BOOKS,
            null, null, null, null, null, null
        )
        val books = mutableListOf<Book>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(BookDBHelper.COLUMN_ID))
                val title = getString(getColumnIndexOrThrow(BookDBHelper.COLUMN_TITLE))
                val author = getString(getColumnIndexOrThrow(BookDBHelper.COLUMN_AUTHOR))
                val status = getString(getColumnIndexOrThrow(BookDBHelper.COLUMN_STATUS))
                books.add(Book(id, title, author, status))
            }
        }
        cursor.close()
        return books
    }
}
