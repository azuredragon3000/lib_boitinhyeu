package com.myapp.lib_boitinhyeu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;


import com.myapp.lib_boitinhyeu.databinding.FragmentBoiTinhYeuDes2Binding;
import com.myapp.mylibrary.boitinhyeu.ContentBoiPhuongDong;
import com.myapp.mylibrary.boitinhyeu.ContentBoiPhuongTay;
import com.myapp.mylibrary.boitinhyeu.DatabaseBoiTinhYeuCHD;
import com.myapp.mylibrary.boitinhyeu.DatabaseBoiTinhYeuPD;
import com.myapp.mylibrary.boitinhyeu.InterfaceBackPressed;
import com.myapp.mylibrary.boitinhyeu.ModelCungMang;
import com.myapp.mylibrary.boitinhyeu.ModelDataBoiTinhYeu;
import com.myapp.mylibrary.boitinhyeu.UserPD;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FragmentBoiTinhYeuDes2 extends Fragment implements InterfaceBackPressed {

    ModelDataBoiTinhYeu modelDataBoiTinhYeu;
    //FirebaseUtiti firebaseUtiti;
    AppCompatActivity activity;
    FragmentBoiTinhYeuDes2Binding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (BTYMainActivity)getActivity();
        //firebaseUtiti = ((SubApp)activity.getApplication()).getDatabaseFirebase();

        ContentBoiPhuongDong contentBoiPhuongDong = new ContentBoiPhuongDong();
        DatabaseBoiTinhYeuCHD databaseCungHoangDao = ((SubApp) activity.getApplication()).getDatabaseBoiCHD();
        ContentBoiPhuongTay contentBoiPhuongTay = new ContentBoiPhuongTay();

        ViewModelShareInforBoiTinhYeu model = new ViewModelProvider(requireActivity()).get(ViewModelShareInforBoiTinhYeu.class);
        modelDataBoiTinhYeu = new ModelDataBoiTinhYeu(0,"","","");
        model.getSelected().observe(getViewLifecycleOwner(), item -> {
            // Update the UI.
            modelDataBoiTinhYeu.number = item.number;
            modelDataBoiTinhYeu.res = item.res;
            modelDataBoiTinhYeu.fulldate = item.fulldate;
            binding.resultBty1.setText(modelDataBoiTinhYeu.number+"%");
            binding.resultBty2.setText(modelDataBoiTinhYeu.res+"");
        });

        binding.btPd.setOnClickListener(v -> {
            //firebaseUtiti.updateDB("boiphuongdongclick", Constant.APPNAME);
            DatabaseBoiTinhYeuPD databaseBoiPhuongDong = ((SubApp) activity.getApplication()).getDatabaseBoiPhuongDongBTY();
            UserPD userNam = new UserPD(activity.getResources().getStringArray(R.array.boitinhyeu_cungmang));
            UserPD userNu = new UserPD(activity.getResources().getStringArray(R.array.boitinhyeu_cungmang));
            List<ModelCungMang> cungmang = ((SubApp) activity.getApplication()).getDatabaseCungMang().getCungMang();
            //String result = databaseBoiPhuongDong.getContent((userNu.index) + String.valueOf(userNam.index));
            modelDataBoiTinhYeu.result
                    = contentBoiPhuongDong.getPD(modelDataBoiTinhYeu.fulldate,databaseBoiPhuongDong,cungmang,userNam,userNu);
            model.select(modelDataBoiTinhYeu);
            NavDirections action =
                    FragmentBoiTinhYeuDes2Directions.actionFragmentBoiTinhYeuDes2ToFragmentBoiTinhYeuDes3();
            Navigation.findNavController(view).navigate(action);
        });

        binding.btChd.setOnClickListener(v -> {
            //firebaseUtiti.updateDB("boiphuongtayclick",Constant.APPNAME);
            modelDataBoiTinhYeu.result = contentBoiPhuongTay.getCHD(modelDataBoiTinhYeu.fulldate,databaseCungHoangDao);
            model.select(modelDataBoiTinhYeu);
            NavDirections action =
                    FragmentBoiTinhYeuDes2Directions.actionFragmentBoiTinhYeuDes2ToFragmentBoiTinhYeuDes3();
            Navigation.findNavController(view).navigate(action);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoiTinhYeuDes2Binding.inflate(inflater,container,false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                // handle back button's click listener
                Intent intent = new Intent(requireActivity(), BTYMainActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}