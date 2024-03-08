package com.example.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class secActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sec)
        // 將變數與XML 元件綁定
        val ed_name = findViewById<EditText>(R.id.ed_name)
        val tv_text = findViewById<TextView>(R.id.tv_text)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val btn_scissor = findViewById<RadioButton>(R.id.btn_scissor)
        val btn_stone = findViewById<RadioButton>(R.id.btn_stone)
        val btn_paper = findViewById<RadioButton>(R.id.btn_paper)
        val btn_onemore = findViewById<Button>(R.id.btn_onemore)
        val tv_name = findViewById<TextView>(R.id.tv_player)
        val tv_winner = findViewById<TextView>(R.id.tv_winner)
        val tv_playerchoise = findViewById<TextView>(R.id.tv_playerchoice)
        val tv_pcchoise = findViewById<TextView>(R.id.tv_pcchoice)

        // 當猜拳被點擊
        btn_onemore.setOnClickListener {
            // 判斷EditText 輸入字數 不能小於1
            if (ed_name.length() < 1) {
                tv_text.text = "請輸入玩家姓名"
                return@setOnClickListener
            }
            // 取得玩家姓名
            val playerName = ed_name.text

            // 亂數  0~2 Math.random 為0 ~ 1 之間的小數亂數
            val pcRandom = ((Math.random() * 10) / 3).toInt()

            // 玩家出拳結果-> val
            val playerText = when {
                btn_scissor.isChecked -> "剪刀"
                btn_stone.isChecked -> "石頭"
                else -> "布"
            }
            // computer 出拳
            val pcText = when (pcRandom) {
                0 -> "剪刀"
                1 -> "石頭"
                else -> "布"
            }
            // 顯示玩家+PC
            tv_name.text = "名字 \n $playerName"
            tv_playerchoise.text = "玩家出拳 \n $playerText"
            tv_pcchoise.text = "電腦出拳 \n $pcText"
            // 用三個判斷是決定勝負
            when {
                btn_scissor.isChecked && pcRandom == 2 ||
                        btn_stone.isChecked && pcRandom == 0 ||
                        btn_paper.isChecked && pcRandom == 1 -> {
                    tv_winner.text = "勝利者 \n $playerName"
                    tv_text.text = "恭喜玩家獲勝!!不用出門了"
                }
                btn_scissor.isChecked && pcRandom == 1 ||
                        btn_stone.isChecked && pcRandom == 2 ||
                        btn_paper.isChecked && pcRandom == 0 -> {
                    tv_winner.text = "勝利者\n 電腦"
                    tv_text.text = "去幫忙辦事吧~"
                }
                else -> {
                    tv_winner.text = "勝利者 \n 平手"
                    tv_text.text = "平局，再來一次"
                }

            }
        }
    }
}