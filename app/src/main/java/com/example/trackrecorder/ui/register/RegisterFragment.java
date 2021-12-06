package com.example.trackrecorder.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    RegisterFragmentViewModel viewModel;

    public RegisterFragment() {
        super(R.layout.fragment_register);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentRegisterBinding.bind(view);

        viewModel = new ViewModelProvider(this).get(RegisterFragmentViewModel.class);

        binding.setModelRegister(viewModel);
    }


}