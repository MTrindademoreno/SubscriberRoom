package com.marciotrindade.mysubscribers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.marciotrindade.mysubscribers.data.db.AppDatabase
import com.marciotrindade.mysubscribers.data.db.dao.SubscriberDao
import com.marciotrindade.mysubscribers.extension.hideKeyboard
import com.marciotrindade.mysubscribers.repository.DatabaseDataSource
import com.marciotrindade.mysubscribers.repository.SubscribeRepository
import com.marciotrindade.mysubscribes.R
import com.marciotrindade.mysubscribes.databinding.SubscriberFragmentBinding

class SubscriberFragment : Fragment() {

private lateinit var binding: SubscriberFragmentBinding
    private val  viewModel: SubscriberViewModel by viewModels {
        object  : ViewModelProvider.Factory{
            //injeção de dependência
            override fun <T :ViewModel?>create(modelClass: Class<T>): T {
                val subscriberDao:SubscriberDao =
                    AppDatabase.getInstance(requireContext()).subscriberDao
                val repository:SubscribeRepository = DatabaseDataSource(subscriberDao)
                return SubscriberViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SubscriberFragmentBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        observeEvents()
        setListeners()
    }

    private fun observeEvents() {
        viewModel.subscriberStateEventData.observe(viewLifecycleOwner){subscriberState ->
            when(subscriberState){
                is SubscriberViewModel.SubscriberState.Inserted->{
                    clearFields()
                    hideKeyboard()

                }
            }


        }

        viewModel.messageEventData.observe(viewLifecycleOwner){stringResId ->
            Snackbar.make(requireView(),stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun hideKeyboard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity){
            parentActivity.hideKeyboard()
        }
    }

    private fun clearFields(){
    activity?.let {
        it.findViewById<TextInputEditText>(R.id.input_name).text?.clear()
        it.findViewById<TextInputEditText>(R.id.input_email).text?.clear()
    }
}

    private fun setListeners() {
        activity?.let {
            binding.apply {
               btnSubscriber.setOnClickListener {
                    val name =this.inputName.text.toString()
                    val email =this.inputEmail.text.toString()

                    viewModel.addSubscriber(name, email)
            }

            }
        }


    }

}