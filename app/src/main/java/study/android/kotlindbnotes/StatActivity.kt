package study.android.kotlindbnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_stat.*

class StatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)
        money.setText(intent.getIntExtra("a", 0).toString())
        good.setText(intent.getIntExtra("b", 0).toString())
        english.setText(intent.getIntExtra("c", 0).toString())
        best.setText(intent.getStringExtra("d"))
        longest.setText(intent.getStringExtra("e"))
    }
}