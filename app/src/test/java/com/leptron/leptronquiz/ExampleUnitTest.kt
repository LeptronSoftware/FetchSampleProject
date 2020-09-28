package com.leptron.leptronquiz

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(com.leptron.leptronquiz.R.drawable.quiz_button_false_selector, com.leptron.leptronquiz.R.drawable.quiz_button_false_selector)
    }
    @Test
    fun practice1()
    {

    }
    fun suggestedProducts(products: Array<String>, searchWord: String): List<List<String>> {
        var sortedProducts = products.sortedArray()
        var listResult = MutableList<List<String>>(0){List<String>(0){""} }
        var currentString = ""
        searchWord.forEach {
            currentString = currentString + it + ""
            var list= MutableList<String>(0){""}
            sortedProducts.forEach {
                if(it.startsWith(currentString))
                {
                    if(list.size < 3)
                        list.add(it)
                }
            }
            listResult.add(list)
        }
        return listResult
    }
}