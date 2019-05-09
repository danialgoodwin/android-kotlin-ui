package dev.goodwin.android.kotlinui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(VerticalLinearLayout(
            R.style.CommonLinearLayout,
            CardView(R.style.CommonCardView, TextView(R.style.CardViewText, text = "Hello, World 1!")),
            HorizontalLinearLayout(
                R.style.CommonLinearLayout,
                TextView(text = "A"),
                TextView(text = "B"),
                TextView(text = "C"),
                TextView(text = "D")
            ),
            RecyclerView(
                R.style.CommonRecyclerView,
                items = getSampleItems(),
                onCreateView = {
                    TextView(text = "Loading")
                },
                onBindView = { item, view ->
                    view.text = item
                },
                onItemClick = { item ->
                    Toast.makeText(this, "Clicked item: $item", Toast.LENGTH_SHORT).show()
                }
            )
        ))
    }

    private fun getSampleItems(): List<String> {
        return mutableListOf<String>().apply {
            for (i in 1..100) {
                add("i=$i")
            }
        }
    }

}
