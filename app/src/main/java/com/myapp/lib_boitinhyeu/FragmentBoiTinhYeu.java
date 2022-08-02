package com.myapp.lib_boitinhyeu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;


import com.myapp.lib_boitinhyeu.databinding.FragmentBoiTinhYeuBinding;
import com.myapp.mylibrary.boitinhyeu.BoitinhyeuObject;
import com.myapp.mylibrary.boitinhyeu.FunctionCommon;
import com.myapp.mylibrary.boitinhyeu.ModelDanhNgon;
import com.myapp.mylibrary.boitinhyeu.ModelDataBoiTinhYeu;
import com.myapp.mylibrary.boitinhyeu.ProcessDataBoiTinhYeu;
import com.myapp.mylibrary.boitinhyeu.UserApp;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentBoiTinhYeu extends Fragment {


    FragmentBoiTinhYeuBinding binding;
    ViewModelShareInforBoiTinhYeu model;
    //FirebaseUtiti firebaseUtiti;
    AppCompatActivity activity;
    UserApp userApp;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (BTYMainActivity)getActivity();
        //firebaseUtiti = ((SubApp)activity.getApplication()).getDatabaseFirebase();
        userApp = (UserApp) requireActivity().getIntent().getSerializableExtra("KEY_NAME");
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                //startActivity(new Intent(requireActivity(), MainActivity.class));
                Intent intent;
                if(userApp == null){
                     //intent = new Intent(requireActivity(), ActivityHomeNoMemberScrolling.class);
                    //intent.putExtra("KEY_NAME", user);
                }else{
                     //intent = new Intent(requireActivity(), ActivityHomeMemberScrolling.class);
                    //intent.putExtra("KEY_NAME", user);
                }
                //startActivity(intent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    private void settingUI(View view) {

        binding.button.setOnClickListener(v -> {

            String rs = binding.nameNam.getText().toString()+"-"
                    +binding.birthdayNamNam.getText().toString()+"-"
                    +binding.birthdayNamThang.getText().toString()+"-"
                    +binding.birthdayNamNgay.getText().toString()+"--------"

                    +binding.nameNu.getText().toString()+"-"
                    +binding.birthdayNuNam.getText().toString()+"-"
                    +binding.birthdayNuThang.getText().toString()+"-"
                    +binding.birthdayNuNgay.getText().toString();

            //firebaseUtiti.updateDB2("boitinhyeu", Constant.APPNAME,rs);
            //firebaseUtiti.updateDB("boitinhyeuclick",Constant.APPNAME);

            ProcessDataBoiTinhYeu processDataBoiTinhYeu = new ProcessDataBoiTinhYeu(
                    binding.nameNam,
                    binding.nameNu,
                    binding.birthdayNamNam,
                    binding.birthdayNuNam,
                    binding.birthdayNamNgay,
                    binding.birthdayNuNgay,
                    binding.birthdayNamThang,
                    binding.birthdayNuThang
            );

            if(processDataBoiTinhYeu.checkNullParameter()){
                FunctionCommon.showDialogInform(getResources().getString(R.string.boitinhyeu_thongtin),
                        getResources().getString(R.string.boitinhyeu_khongdctrong),activity);
            }else if(!processDataBoiTinhYeu.checkNumber()){
                FunctionCommon.showDialogInform(getResources().getString(R.string.boitinhyeu_thongtin),
                        getResources().getString(R.string.boitinhyeu_khongdcdeso),activity);
            }else{
                if(processDataBoiTinhYeu.checkOverParameter()){
                    FunctionCommon.showDialogInform(getResources().getString(R.string.boitinhyeu_thongtin),
                            processDataBoiTinhYeu.getInfor(),activity);
                }else{
                    // navigate to love animation fragment
                    int number = processDataBoiTinhYeu.getHashNumber();
                    String fulldate = processDataBoiTinhYeu.getStringCombination();
                    BoitinhyeuObject boitinhyeuObject = new BoitinhyeuObject();
                    List<ModelDanhNgon> modelDanhNgonList = ((SubApp) activity.getApplication()).getDatabaseNgonTinh().getDanhNgon();
                    boitinhyeuObject.start_app(number,fulldate,modelDanhNgonList);

                    ModelDataBoiTinhYeu dataBoiTinhYeu =
                            new ModelDataBoiTinhYeu(
                                    boitinhyeuObject.numberLove,
                                    boitinhyeuObject.contentLove,
                                    boitinhyeuObject.fulldate,"");

                    NavDirections action =
                            FragmentBoiTinhYeuDirections.actionFragmentBoiTinhYeuToFragmentBoiTinhYeuDes();
                    model.select(dataBoiTinhYeu);
                    Navigation.findNavController(view).navigate(action);

                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //new AdsBanner(activity,activity,R.id.adView);
        model = new ViewModelProvider(requireActivity()).get(ViewModelShareInforBoiTinhYeu.class);
        this.view = view;
        settingUI(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoiTinhYeuBinding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }


}