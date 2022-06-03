package com.example.hw2


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hw2.data.Item
import com.example.hw2.databinding.FragmentItemDetailBinding


class ItemDetailFragment : Fragment() {

    private val viewModel: VocabularyViewModel by activityViewModels {
        VocabularyViewModelFactory(
            (activity?.application as VocabularyApplication).database.itemDao()
        )
    }

    private val navigationArgs:ItemDetailFragmentArgs by navArgs()
    lateinit var item: Item
    private var _binding:FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.itemId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
            item = selectedItem
            bind(item)
        }

    }

    private fun bind(item: Item) {
        binding.apply {
            itemEnglish.text = item.english
            itemChinese.text = item.chinese

            deleteItem.setOnClickListener { deleteItem() }
            editItem.setOnClickListener { editItem() }
        }
    }

    private fun deleteItem() {
        viewModel.deleteItem(item)
        findNavController().navigateUp()
    }

    private fun editItem() {
        val action = ItemDetailFragmentDirections.actionItemDetailFragmentToAddItemFragment(
            item.id
        )
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}