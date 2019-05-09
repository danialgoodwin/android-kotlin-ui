package dev.goodwin.android.kotlinui

import android.app.Activity
import android.content.Context
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun Context.CardView(@StyleRes style: Int? = null, view: View, onClick: (() -> Unit)? = null): CardView {
    val cardView = if (style == null) CardView(this) else CardView(this, null, style)
    return cardView.apply {
        addView(view)
        onClick?.let { setOnClickListener { onClick.invoke() } }
    }
}

// TODO: Automatically wrap in ScrollView
fun Context.HorizontalLinearLayout(@StyleRes style: Int? = null, vararg view: View): LinearLayout {
    return (if (style == null) LinearLayout(this) else LinearLayout(this, null, style)).apply {
        orientation = LinearLayout.HORIZONTAL
        view.forEach { addView(it) }
    }
}

/** Create a new vertical RecyclerView, along with its adapter and view-holder. */
fun <T, V : View> Context.RecyclerView(
    @StyleRes style: Int? = null,
    items: List<T>,
    onCreateView: () -> V,
    onBindView: (T, V) -> Unit,
    onItemClick: ((T) -> Unit)? = null
): RecyclerView {
    return (if (style == null) RecyclerView(this) else RecyclerView(this, null, style)).apply {
        this.layoutManager = LinearLayoutManager(this@RecyclerView)
        this.adapter = CommonRecyclerViewAdapter(items, onCreateView, onBindView, onItemClick)
    }
}

fun Context.TextInputLayout(@StyleRes layoutStyle: Int? = null, @StyleRes editTextStyle: Int? = null, text: String, hint: String, isPassword: Boolean = false): TextInputLayout {
    return (if (layoutStyle == null) TextInputLayout(this) else TextInputLayout(this, null, layoutStyle)).apply {
        if (isPassword) isPasswordVisibilityToggleEnabled = true
        addView((if (editTextStyle == null) TextInputEditText(this@TextInputLayout) else TextInputEditText(this@TextInputLayout, null, editTextStyle)).apply {
            this.setText(text)
            this.hint = hint
            if (isPassword) inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        })
    }
}

fun Context.TextView(@StyleRes style: Int? = null, text: String): TextView {
    return (if (style == null) TextView(this) else TextView(this, null, style)).apply { this.text = text }
}

// TODO: Automatically wrap in ScrollView
fun Context.VerticalLinearLayout(@StyleRes style: Int? = null, vararg view: View): LinearLayout {
    return (if (style == null) LinearLayout(this) else LinearLayout(this, null, style)).apply {
        orientation = LinearLayout.VERTICAL
        view.forEach { addView(it) }
    }
}

fun Fragment.CardView(@StyleRes style: Int? = null, view: View, onClick: (() -> Unit)? = null) = this.requireContext().CardView(style, view, onClick)
fun Fragment.HorizontalLinearLayout(@StyleRes style: Int? = null, vararg view: View) = this.requireContext().HorizontalLinearLayout(style, *view)
fun Fragment.TextInputLayout(@StyleRes layoutStyle: Int? = null, @StyleRes editTextStyle: Int? = null, text: String, hint: String, isPassword: Boolean = false) = this.requireContext().TextInputLayout(layoutStyle, editTextStyle, text, hint, isPassword)
fun Fragment.TextView(@StyleRes style: Int? = null, text: String) = this.requireContext().TextView(style, text)
fun Fragment.VerticalLinearLayout(@StyleRes style: Int? = null, vararg view: View) = this.requireContext().VerticalLinearLayout(style, *view)


class CommonRecyclerViewAdapter<T, V : View>(
    private val items: List<T>,
    private val onCreateView: () -> V,
    private val onBindView: (T, V) -> Unit,
    private val onItemClick: ((T) -> Unit)? = null
) : RecyclerView.Adapter<CommonRecyclerViewAdapter.ViewHolder<V>>() {

    override fun getItemCount() = items.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(onCreateView.invoke())

    override fun onBindViewHolder(holder: ViewHolder<V>, position: Int) = onBindView.invoke(items[position], holder.view).also {
        onItemClick?.let { holder.view.setOnClickListener { onItemClick.invoke(items[position]) } }
    }

    class ViewHolder<V : View>(val view: V) : RecyclerView.ViewHolder(view)

}