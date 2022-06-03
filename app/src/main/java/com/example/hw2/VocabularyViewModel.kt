package com.example.hw2

import androidx.lifecycle.*
import com.example.hw2.data.Item
import com.example.hw2.data.ItemDao
import kotlinx.coroutines.launch

class VocabularyViewModel(private val itemDao: ItemDao) : ViewModel()  {
    val allItems: LiveData<List<Item>> = itemDao.getItems().asLiveData()
    val favouriteItems: LiveData<List<Item>> = itemDao.getFavouriteItems().asLiveData()
    var isFavourite:Boolean = false

    private fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }


    private fun getNewItemEntry(
        itemEnglish: String,
        itemChinese: String,
        itemIsFavourite: Boolean
    ): Item {
        return Item(
            english = itemEnglish,
            chinese = itemChinese,
            isFavourite = itemIsFavourite
        )
    }

    fun addNewItem(itemEnglish: String, itemChinese: String, itemIsFavourite: Boolean) {
        val newItem = getNewItemEntry(itemEnglish, itemChinese, itemIsFavourite)
        insertItem(newItem)
    }

    fun isEntryValid(itemEnglish: String): Boolean {
        if (itemEnglish.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    private fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.update(item)
        }
    }


    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemEnglish: String,
        itemChinese: String,
        itemIsFavourite: Boolean
    ): Item {
        return Item(itemId,itemEnglish,itemChinese,itemIsFavourite)
    }

    fun updateItem(
        itemId: Int,
        itemEnglish: String,
        itemChinese: String,
        itemIsFavourite: Boolean
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemEnglish, itemChinese, itemIsFavourite)
        updateItem(updatedItem)
    }
}

class VocabularyViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VocabularyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VocabularyViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}