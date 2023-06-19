package ch.cpnv.ymd.bmb.models

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

class Loan {
    var id: Int = 0;
    lateinit var book: String;
    lateinit var contact: String;
    var created_at: Long = 0;
    var return_at: Long = 0;

    constructor(){
    }

    constructor(book: String, contact : String, return_at : Long ){
        this.book = book;
        this.contact = contact;
        this.created_at = Date().time
        this.return_at = return_at;
    }

    override fun toString(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return "$book loan by $contact, return at ${formatter.format(return_at)}"
    }
}