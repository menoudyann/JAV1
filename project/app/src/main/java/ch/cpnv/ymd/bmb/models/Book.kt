package ch.cpnv.ymd.bmb.models

class Book {
    var id: Int = 0;
    var title: String = "";
    var author: String = "";
    var isbn: String = "";
    var imageUrl: String = "";

    constructor(){
    }

    constructor(title: String, author : String, isbn : String, imageUrl : String ){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
    }

    override fun toString(): String {
        return "$title by $author"
    }
}

