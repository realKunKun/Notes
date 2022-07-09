package com.example.notes.logic.model

import android.net.Uri
import com.example.notes.R
import retrofit2.http.Url

object GlobalValue {
    var cookie:String=""
    var accessState:Boolean=false
    val testList= mutableListOf<NoteModel>()
    val testClassList= mutableListOf(
        TagModel(1,"fruit",
            mutableListOf(
            NoteModel("1","1","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","fruit",1,"xxxx"),
            NoteModel("1","2","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","fruit",1,"xxxx")
            )
        )

        , TagModel(2,"vehicle", mutableListOf(NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx"),
            NoteModel("1","3","2022.2.2",4,1, "http://121.37.86.25:8000/static/16548512751654851275.png","vehicle",2,"xxxx")
                                    )))
    lateinit var uri: Uri
}