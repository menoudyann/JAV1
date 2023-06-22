package ch.cpnv.ymd.bmb.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import ch.cpnv.ymd.bmb.models.Book
import ch.cpnv.ymd.bmb.models.Loan
import java.util.Date

private const val DATABASE_NAME = "library";
private const val DATABASE_VERSION = 1;

// table books
const val TABLE_BOOKS = "books";
const val COLUMN_BOOK_ID = "id";
const val COLUMN_BOOK_TITLE = "title";
const val COLUMN_BOOK_AUTHOR = "author";
const val COLUMN_BOOK_ISBN = "isbn";
const val COLUMN_BOOK_IMAGEURL = "imageUrl";

// table loans
const val TABLE_LOANS = "loans";
const val COLUMN_LOAN_ID = "id";
const val COLUMN_LOAN_BOOKS = "book";
const val COLUMN_LOAN_CONTACTS = "contact";
const val COLUMN_LOAN_CREATED_AT = "created_at";
const val COLUMN_LOAN_RETURN_AT = "return_at";

lateinit var db: SQLiteDatabase;

class DatabaseHandler(var context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // create the books table
        val createTableBookStatement = "CREATE TABLE $TABLE_BOOKS (" +
                "$COLUMN_BOOK_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BOOK_TITLE TEXT, " +
                "$COLUMN_BOOK_AUTHOR TEXT, " +
                "$COLUMN_BOOK_IMAGEURL TEXT, " +
                "$COLUMN_BOOK_ISBN TEXT)";

        // create the loans table
        val createTableLoansStatement = "CREATE TABLE $TABLE_LOANS (" +
                "$COLUMN_LOAN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_LOAN_BOOKS TEXT, " +
                "$COLUMN_LOAN_CONTACTS TEXT, " +
                "$COLUMN_LOAN_CREATED_AT LONG, " +
                "$COLUMN_LOAN_RETURN_AT LONG)";

        db?.execSQL(createTableBookStatement);
        db?.execSQL(createTableLoansStatement);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertBook(book: Book) {
        db = this.writableDatabase
        var cv = ContentValues();
        cv.put(COLUMN_BOOK_TITLE, book.title);
        cv.put(COLUMN_BOOK_AUTHOR, book.author);
        cv.put(COLUMN_BOOK_ISBN, book.isbn);
        cv.put(COLUMN_BOOK_IMAGEURL, book.imageUrl);
        var result = db.insert(TABLE_BOOKS, null, cv);
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    fun createLoan(loan: Loan) {
        db = this.writableDatabase;
        var cv = ContentValues();
        cv.put(COLUMN_LOAN_BOOKS, loan.book);
        cv.put(COLUMN_LOAN_CONTACTS, loan.contact);
        cv.put(COLUMN_LOAN_CREATED_AT, loan.created_at);
        cv.put(COLUMN_LOAN_RETURN_AT, loan.return_at);
        var result = db.insert(TABLE_LOANS, null, cv);
        if (result == (-1).toLong())
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
    }

    fun getAllBooks() : MutableList<Book>{
        var list : MutableList<Book> = ArrayList();
        db = this.readableDatabase;
        val query = "SELECT * FROM $TABLE_BOOKS";
        val result = db.rawQuery(query, null);
        if (result.moveToFirst()) {
            do {
                var book = Book();
                book.id = result.getString(0).toInt();
                book.title = result.getString(1);
                book.author = result.getString(2);
                book.isbn = result.getString(4);
                book.imageUrl = result.getString(3);
                list.add(book);
            } while (result.moveToNext());
        }
        result.close();
        db.close();
        return list;
    }

    fun getAllLoans() : MutableList<Loan>{
        var list : MutableList<Loan> = ArrayList();
        db = this.readableDatabase;
        val query = "SELECT * FROM $TABLE_LOANS WHERE $COLUMN_LOAN_RETURN_AT >= ${Date().time}";
        val result = db.rawQuery(query, null);
        if (result.moveToFirst()) {
            do {
                var loan = Loan();
                loan.id = result.getString(0).toInt();
                loan.book = result.getString(1);
                loan.contact = result.getString(2);
                loan.created_at = result.getLong(3);
                loan.return_at = result.getLong(4);
                list.add(loan);
            } while (result.moveToNext());
        }
        result.close();
        db.close();
        return list;
    }

    var list : MutableList<Book> = ArrayList();

}

