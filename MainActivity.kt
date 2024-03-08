package com.example.mainactivity

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.italkutalk.lab15.MyDBHelper

class MainActivity : AppCompatActivity() {
    private var items: ArrayList<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var dbrw: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_nextpage = findViewById<Button>(R.id.btn_next)
        val btn_map = findViewById<Button>(R.id.btn_map)

        //跳轉到猜拳
        btn_nextpage.setOnClickListener {
            val i = Intent(this@MainActivity, secActivity:: class.java)
            startActivity(i)
        }

        //跳轉到map
        btn_map.setOnClickListener {
            val imap = Intent(this@MainActivity, MapsActivity:: class.java)
            startActivity(imap)
        }
        //取得資料庫實體
        dbrw = MyDBHelper(this).writableDatabase
        //宣告 Adapter 並連結 ListView
        adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, items)
        findViewById<ListView>(R.id.listView).adapter = adapter
        //設定監聽器
        setListener()

    }
    override fun onDestroy() {
        dbrw.close() //關閉資料庫
        super.onDestroy()
    }

    //設定監聽器
    private fun setListener() {
        val ed_drink = findViewById<EditText>(R.id.ed_drink)
        val ed_sweetice = findViewById<EditText>(R.id.ed_sweetice)
        // ---------insert------------
        findViewById<Button>(R.id.btn_insert).setOnClickListener {
            //判斷是否有填入
            if (ed_drink.length() < 1 || ed_sweetice.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    //新增一筆紀錄於 myTable 資料表
                    dbrw.execSQL(
                        "INSERT INTO myTable(book, price) VALUES(?,?)",
                        arrayOf(ed_drink.text.toString(),
                            ed_sweetice.text.toString())
                    )
                    showToast("事項: ${ed_drink.text},備註:${ed_sweetice.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("重複囉,新增失敗:$e")
                }
        }
        // -----------------update------------
        findViewById<Button>(R.id.btn_update).setOnClickListener {
            //判斷是否有填入
            if (ed_drink.length() < 1 || ed_sweetice.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    //尋找相同的紀錄並更新欄位的值
                    dbrw.execSQL("UPDATE myTable SET price = ${ed_sweetice.text} WHERE book LIKE '${ed_drink.text}'")
                    showToast("更新: ${ed_drink.text},備註:${ed_sweetice.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("更新失敗:$e")
                }
        }
        //-------------Delete---------------------
        findViewById<Button>(R.id.btn_delete).setOnClickListener {
            //判斷是否有填入
            if (ed_drink.length() < 1)
                showToast("欄位請勿留空")
            else
                try {
                    //從 myTable 資料表刪除相同的紀錄
                    dbrw.execSQL("DELETE FROM myTable WHERE book LIKE '${ed_drink.text}'")
                    showToast("刪除:${ed_drink.text}")
                    cleanEditText()
                } catch (e: Exception) {
                    showToast("刪除失敗:$e")
                }
        }
        // ------------search------------
        findViewById<Button>(R.id.btn_query).setOnClickListener {
            //若無輸入東西則 SQL 語法為查詢全部東西，查詢該資料
            val queryString = if (ed_drink.length() < 1)
                "SELECT * FROM myTable"
            else
                "SELECT * FROM myTable WHERE book LIKE '${ed_drink.text}'"
            val c = dbrw.rawQuery(queryString, null)
            c.moveToFirst() //從第一筆開始輸出
            items.clear() //清空舊資料
            showToast("共有${c.count}筆資料")
            for (i in 0 until c.count) {
                //加入新資料
                items.add("事項: ${c.getString(0)}\t\t\t\t 備註:${c.getString(1)}")
                c.moveToNext() //移動到下一筆
            }
            adapter.notifyDataSetChanged() //更新列表資料
            c.close() //關閉
        }
    }
    //Toast 訊息
    private fun showToast(text: String) =
        Toast.makeText(this,text, Toast.LENGTH_LONG).show()
    //清空輸入的東西
    private fun cleanEditText() {
        findViewById<EditText>(R.id.ed_drink).setText("")
        findViewById<EditText>(R.id.ed_sweetice).setText("")
    }

}
