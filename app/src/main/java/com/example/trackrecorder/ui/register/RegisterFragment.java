package com.example.trackrecorder.ui.register;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trackrecorder.R;
import com.example.trackrecorder.database.models.UserModel;
import com.example.trackrecorder.databinding.FragmentRegisterBinding;
import com.example.trackrecorder.ui.MainActivityViewModel;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    RegisterFragmentViewModel viewModel;
    MainActivityViewModel mainViewModel;

    public RegisterFragment() {
        super(R.layout.fragment_register);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentRegisterBinding.bind(view);

        mainViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        viewModel = new ViewModelProvider(this).get(RegisterFragmentViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setModelRegister(viewModel);

        mainViewModel.getGlobalAvatar().observe(getViewLifecycleOwner(), bitmap -> viewModel.postAvatar(bitmap));
        viewModel.getUserToWorkWith().observe(getViewLifecycleOwner(), userModel -> mainViewModel.loginUser(userModel));
    }


}