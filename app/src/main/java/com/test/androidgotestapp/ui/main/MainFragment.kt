package com.test.androidgotestapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.foundation.VerticalScroller
import com.test.androidgotestapp.R
import gotestlib.Photo

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val fragmentView = inflater.inflate(R.layout.main_fragment, container, false) as ViewGroup

        fragmentView.setContent {
            val viewState = +observe(viewModel.photos)

            VerticalScroller {
                viewState?.forEach {
                    Text (it.url)
                }
            }
        }

        return fragmentView
    }

    fun <T> observe(data: LiveData<T>) = effectOf<T?> {
        val result = +state { data.value }
        val observer = +memo {
            Observer<T> {
                result.value = it
            }
        }

        +onCommit(data) {
            data.observeForever(observer)
            onDispose { data.removeObserver(observer) }
        }

        result.value
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.loadPhotos()
    }

}
