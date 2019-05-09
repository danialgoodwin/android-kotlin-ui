# android-kotlin-ui
The most easy, simple, and fast way to create a layout programmatically, using Kotlin




Before:

    @GenerateFragment
    class MyFragment(private val id: Int, private val name: String) {
        fun createView(): View {
            return CoordinatorLayout(
                AppBar(title = name) { },
                CardView(title = name, style = R.style.hero_card_view) { },
                RecyclerView(
                    items = getItems(),
                    emptyView = getEmptyView(),
                    itemViewAdapter = { item ->
                        return CardView(title = item.name, style = R.style.list_card_view)
                    }
                ),
                Fab(icon = R.drawable.ic_add, action = { doSomething() })
            )
        }
    }


After:

    class MyFragment() : GeneratedFragment {
    
        private val id by lazy { argument.getInt("id") }
        private val name by lazy { argument.getString("name") }
        
        override onCreateView(...): View {
            return inflater.inflate(R.layout.generated_fragment_my, parent, false)
        }
    
        // ... More about RecyclerView and Adapter and ViewHolder
    
        companion object {
            fun create(id: Int, name: String) = MyFragment().apply {
                arguments.putInt("id", id)
                arguments.putString("name", name)
            }
        }
        
    }