package com.schneewittchen.rosandroid.ui.fragments.joystick;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.schneewittchen.rosandroid.domain.RosDomain;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.message.RosData;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;

import java.util.List;


public class JoystickViewModel extends AndroidViewModel {

    private static final String TAG = JoystickViewModel.class.getSimpleName();

    private final RosDomain rosDomain;


    public JoystickViewModel(@NonNull Application application) {
        super(application);

        rosDomain = RosDomain.getInstance(application);
    }

    public void updateWidget(BaseEntity widget) {
        rosDomain.updateWidget(null, widget);
    }

    public LiveData<List<BaseEntity>> getCurrentWidgets() {
        return rosDomain.getCurrentWidgets();
    }

    public LiveData<RosData> getData() {
        return this.rosDomain.getData();
    }


    public void publishData(BaseData data) {
        rosDomain.publishData(data);
    }
}
