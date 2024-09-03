package com.example.coffeproject2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.coffeproject2.R
import com.example.coffeproject2.databinding.FragmentDetailBinding
import com.example.coffeproject2.view.viewmodel.CoffeeViewModel
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var navController: NavController

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel : CoffeeViewModel
    private var storage: FirebaseStorage = FirebaseStorage.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater,container,false)
        navController = findNavController()

        viewModel = ViewModelProvider(requireActivity()).get(CoffeeViewModel::class.java)
        viewModel.selectedCoffee.let { coffee ->
            binding.CoffeDetailsNameText.text = coffee.coffeeName
            binding.CoffeeDataText.text = coffee.coffeeDetail
            val imageRef = storage.reference.child("images/${coffee.coffeeImageUrl}.jpg")
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                Picasso.get()
                    .load(uri)
                    .placeholder(R.drawable.ref) // Yüklenirken gösterilecek resim
                    .into(binding.CoffeDetailImage)
            }.addOnFailureListener {
                // Hata durumunda varsayılan bir resim göster
                binding.CoffeDetailImage.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
        return binding.root
    }
}