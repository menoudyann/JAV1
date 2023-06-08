package ch.cpnv.ymd.bookmybook

class BookModel(
    private var cover: Int,
    private var title: String,
    private var ISBN: String,
    private var author: String,

) {
    fun getCover(): Int {
        return cover
    }

    fun getTitle(): String {
        return title
    }

    fun getAuthor(): String {
        return author
    }

    fun getISBN(): String {
        return ISBN
    }
}