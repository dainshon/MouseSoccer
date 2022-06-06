package com.example.mousesoccer

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyDBHelper(val context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object{   //외부에서 입력안받고 여기서 사용하면됨
        val DB_NAME = "myteam.db"
        val DB_VERSION = 1
        val TABLE_NAME = "myteam"

        val LEAGUE = "league"
        val TEAMNAME = "teamname"
        val ID = "id"

//        val PID = "pid"
//        val PNAME = "pname"
//        val PQUANTITY = "pquantity"
    }

    fun insertData(myteam: MyTeamData):Boolean{
        val values = ContentValues()
        values.put(LEAGUE, myteam.League)
        values.put(TEAMNAME, myteam.teamname)
        val db = writableDatabase
        Log.d("dbhelper", myteam.League+" "+ myteam.teamname)
        //db삽입 작업
        val flag = db.insert(TABLE_NAME, null, values)>0
        db.close()
        return flag

    }



//    fun deleteData(myteam: MyTeamData):Boolean{
//        val db = writableDatabase
//        var flag = db.delete(TABLE_NAME, LEAGUE+"="+myteam.teamname,null)>0
//
//
//    }

    override fun onCreate(db: SQLiteDatabase?) {    //db처음 만들어질때만 호출
        val create_table = "create table if not exists $TABLE_NAME("+
                "$ID integer primary key autoincrement, "+
                "$LEAGUE text, "+
                "$TEAMNAME text);"  //SQL 구문

        db!!.execSQL(create_table)   //db가 null아니면 create_table실행
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //db바뀐경우
//        val drop_table= "drop table if exists $TABLE_NAME;"
//        db!!.execSQL(drop_table)
//        onCreate(db)
    }

}