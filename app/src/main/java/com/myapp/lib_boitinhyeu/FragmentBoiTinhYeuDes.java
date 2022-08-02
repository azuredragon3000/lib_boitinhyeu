package com.myapp.lib_boitinhyeu;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.myapp.lib_boitinhyeu.databinding.FragmentBoiTinhYeuDesBinding;
import com.myapp.mylibrary.boitinhyeu.AnimChangeTextPerTime;
import com.myapp.mylibrary.boitinhyeu.AnimScaleObject;
import com.myapp.mylibrary.boitinhyeu.FunctionCommon;
import com.myapp.mylibrary.boitinhyeu.InterfaceAnimation;
import com.myapp.mylibrary.boitinhyeu.ModelDataBoiTinhYeu;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentBoiTinhYeuDes extends Fragment implements Animator.AnimatorListener, InterfaceAnimation {

    AnimChangeTextPerTime animChangeTextPerTime;
    AnimScaleObject animScaleObject;
    ModelDataBoiTinhYeu modelDataBoiTinhYeu;
    FragmentBoiTinhYeuDesBinding binding;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        animChangeTextPerTime = new AnimChangeTextPerTime(this);
        animChangeTextPerTime.startRepeatingTask();

        animScaleObject = new AnimScaleObject(this,binding.image1);
        animScaleObject.startAnim(2000,3);

        modelDataBoiTinhYeu = new ModelDataBoiTinhYeu(0,"","","");
        ViewModelShareInforBoiTinhYeu model = new ViewModelProvider(requireActivity()).get(ViewModelShareInforBoiTinhYeu.class);
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            // Update the UI.
            modelDataBoiTinhYeu.number = item.number;
            modelDataBoiTinhYeu.res = item.res;
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoiTinhYeuDesBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        animChangeTextPerTime.stopRepeatingTask();
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onAnimationEnd(Animator animator) {
        animChangeTextPerTime.stopRepeatingTask();
        binding.tv1.setText(modelDataBoiTinhYeu.number+"%");

        // move to other fragment
        NavDirections action =
                FragmentBoiTinhYeuDesDirections.actionFragmentBoiTinhYeuDesToFragmentBoiTinhYeuDes2();
        Navigation.findNavController(view).navigate(action);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void doWork() {
        binding.tv1.setText(FunctionCommon.getRandom(100,0)+"%");
    }


}