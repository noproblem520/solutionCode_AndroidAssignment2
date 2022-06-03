package com.example.hw2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.hw2.databinding.FragmentItemListBinding
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw2.data.Item
import android.R.drawable.btn_star_big_on
import android.R.drawable.btn_star_big_off


class ItemListFragment : Fragment() {


    private val viewModel: VocabularyViewModel by activityViewModels {
        VocabularyViewModelFactory(
            (activity?.application as VocabularyApplication).database.itemDao()
        )
    }

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:ItemListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemListAdapter({
            val action = ItemListFragmentDirections.actionItemListFragmentToItemDetailFragment(it.id)
            this.findNavController().navigate(action)
        },::updateItem)


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        initData()


        binding.floatingActionButton.setOnClickListener {
            val action = ItemListFragmentDirections.actionItemListFragmentToAddItemFragment(
                -1
            )
            this.findNavController().navigate(action)
        }
    }

    // choose the variable that we want to observe
    private fun initData(){

        if(viewModel.isFavourite){
            // you need to remove previous observer to prevent a logic error
            // if you don't do this, it will simultaneously observe two variable in this condition
            viewModel.allItems.removeObservers(this.viewLifecycleOwner)
            viewModel.favouriteItems.observe(this.viewLifecycleOwner) { items ->
                items.let {
                    adapter.submitList(it)
                }
            }
        }else{
            viewModel.favouriteItems.removeObservers(this.viewLifecycleOwner)
            viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
                items.let {
                    adapter.submitList(it)
                }
            }
        }
    }

//    update 'isFavourite' field in the database
    private fun updateItem(item:Item) {
        viewModel.updateItem(
            item.id,
            item.english,
            item.chinese,
            !item.isFavourite
        )
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu,menu)
        /*optional
         If you rotate your phone, then onCreateOptionsMenu is called again.
         Make sure that the item's icon is correct after rotating your phone.
         */
        initMenuItemIcon(menu.getItem(0))
    }

    private fun initMenuItemIcon(item: MenuItem){
        if(viewModel.isFavourite){
            item.setIcon(btn_star_big_on)
        }else{
            item.setIcon(btn_star_big_off)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // Handle item selection
        return when (item.itemId) {
            R.id.Favourite -> {
//              change the variable that controls what information we want to show
                viewModel.isFavourite = !viewModel.isFavourite
//              change MenuItem's icon
                initMenuItemIcon(item)

                initData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}