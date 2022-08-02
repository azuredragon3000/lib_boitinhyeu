package com.myapp.lib_boitinhyeu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myapp.mylibrary.boitinhyeu.ModelDataBoiTinhYeu;


public class ViewModelShareInforBoiTinhYeu extends ViewModel {
    private final MutableLiveData<ModelDataBoiTinhYeu> selected = new MutableLiveData<ModelDataBoiTinhYeu>();
    public void select(ModelDataBoiTinhYeu item) {
        selected.setValue(item);
    }
    public LiveData<ModelDataBoiTinhYeu> getSelected() {
        return selected;
    }
}
