package study.android.kotlindbnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
//      val dbHelper = SimpleDBHelper(context)
        btnInsert.setOnClickListener {
            if (editTextName.text.toString().isNotEmpty() &&
                editTextResult.text.toString().isNotEmpty()
            ) {
                //val result =
                //    Result(editTextName.text.toString(), editTextResult.text.toString().toInt())
                //dbHelper.insert(result)
                val result = ResultEntity(
                    0,
                    editTextName.text.toString(),
                    editTextResult.text.toString().toInt()
                )
                GlobalScope.launch {
                    db.resultsDao().insert(result)
                }
                clearFields()
            } else {
                Toast.makeText(context, "Please Fill All Data's", Toast.LENGTH_SHORT).show()
            }
        }
//        btnRead.setOnClickListener {
//            GlobalScope.launch {
//                val data = db.resultsDao().getAll("RESULT DESC")
//                withContext(Main) {
//                    tvResult.text = ""
//                    for (d in data) {
//                        tvResult.append("${d.name} ${d.result}\n")
//                    }
//                }
//            }
//        }
         result_list.layoutManager = LinearLayoutManager(this)
         db.resultsDao().getAll("RESULT DESC").observe(this,
             { results -> result_list.adapter = ResultAdapter(results)})

    }

    private fun clearFields() {
        editTextName.text.clear()
        editTextResult.text.clear()
    }
}