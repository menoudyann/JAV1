package ch.cpnv.ymd.bookmybook

var bookList = mutableListOf<Book>()

class Book(
    var cover: Int,
    var title: String,
    var author: String,
    var ISBN: String,
    val id: Int? = bookList.size
)