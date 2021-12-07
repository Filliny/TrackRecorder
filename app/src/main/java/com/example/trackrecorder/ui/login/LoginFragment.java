package com.example.trackrecorder.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;

import com.example.trackrecorder.R;
import com.example.trackrecorder.database.models.UserModel;
import com.example.trackrecorder.databinding.FragmentLoginBinding;
import com.example.trackrecorder.ui.MainActivityViewModel;


public class LoginFragment extends Fragment {

    LoginFragmentViewModel viewModel;
    MainActivityViewModel mainViewModel;
    FragmentLoginBinding binding;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentLoginBinding.bind(view);

        viewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        //to re-hide DisplayHomeAsUp when back from register
        //todo find right way for hiding menu button
        if(mainViewModel.getCurrentUser() == null){
            mainViewModel.getLoginObserve().postValue(false);
        }


        binding.setLifecycleOwner(this);
        binding.setModelLogin(viewModel);

        binding.registerLink.setOnClickListener(this::toRegisterFragment);
        viewModel.getUserToWorkWith().observe(getViewLifecycleOwner(), userModel -> mainViewModel.loginUser(userModel));
    }


    public void toRegisterFragment(View view){
      NavController navController =  Navigation.findNavController(getActivity(),R.id.nav_host_fragment_content_main);
      navController.navigate(R.id.action_loginFragment_to_signInFragment);
    }
}