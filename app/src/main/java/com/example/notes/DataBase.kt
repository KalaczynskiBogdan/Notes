package com.example.notes

class DataBase {
    private val listOfNotes: ArrayList<Note> = ArrayList()

    companion object{
        private var instance: DataBase? = null
        fun getInstance():DataBase{
            if (instance == null){
                instance = DataBase()
            }
            return instance as DataBase
        }
    }
    fun makeList(){
        listOfNotes.add(Note(1,"Lesson of Android Development at 7 pm",null))
        listOfNotes.add(Note(2,"Dinner at 8-9 pm on the break",null))
        listOfNotes.add(Note(3,"Go to barber tomorrow at 11 am",null))
    }
    fun getList(): ArrayList<Note>{
        return ArrayList(listOfNotes)
    }
    fun add(note: Note){
        listOfNotes.add(note)
    }
}