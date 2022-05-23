package study.android.kotlindbnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.size
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import androidx.lifecycle.Observer as Observer

class MainActivity : AppCompatActivity() {
    val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "results.db"
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        GlobalScope.launch {
            for (company in TestData.russianCompanies2020) {
                db.resultsDao().insert(company)
            }
        }
        companies_list.layoutManager = LinearLayoutManager(this)
        db.resultsDao().getAll("RESULT DESC").observe(this,
            { results -> companies_list.adapter = ResultAdapter(results) })
        statistics.setOnClickListener {
            db.resultsDao().getAll("RESULT DESC").observe(this, { results ->
                val intent  = Intent(this, StatActivity::class.java)
                var max = 0
                var maxLen = 0
                var a = 0
                for (i in results.indices)
                {
                    a+= results[i].result!!
                    if (results[i].result!! > results[max].result!!)
                        max = i
                    if (results[i].name!!.length > results[maxLen].name!!.length)
                        maxLen = i
                }
                var b = 0
                for (i in results)
                    if (i.result!! > a/results.size)
                        b++
                var c = 0
                for (i in results)
                    if (i.name!!.compareTo("А") < 0)
                        c++
                val d :String = results[max].name!!
                val e :String = results[maxLen].name!!
                intent.putExtra("a", a)
                intent.putExtra("b", b)
                intent.putExtra("c", c)
                intent.putExtra("d", d)
                intent.putExtra("e", e)
                startActivity(intent)
            })
        }

        findViewById<Button>(R.id.delete).setOnClickListener {
            val text = findViewById<TextView>(R.id.toDelete).text.toString()
            GlobalScope.launch {
                db.resultsDao().deleteByPattern(text)
                // db.resultsDao().getAll()
            }

        }

    }
}