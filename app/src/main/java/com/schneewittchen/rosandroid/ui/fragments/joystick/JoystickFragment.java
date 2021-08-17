package com.schneewittchen.rosandroid.ui.fragments.joystick;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.databinding.FragmentJoystickBinding;
import com.schneewittchen.rosandroid.model.entities.widgets.BaseEntity;
import com.schneewittchen.rosandroid.model.repositories.rosRepo.node.BaseData;
import com.schneewittchen.rosandroid.ui.fragments.viz.WidgetViewGroup;
import com.schneewittchen.rosandroid.ui.general.DataListener;
import com.schneewittchen.rosandroid.ui.general.WidgetChangeListener;

public class JoystickFragment extends Fragment implements DataListener, WidgetChangeListener {

    public static final String TAG = JoystickFragment.class.getSimpleName();

    private FragmentJoystickBinding binding;

    private JoystickViewModel joyViewModel;
    private WidgetViewGroup widgetViewGroupJoy;

    public static JoystickFragment newInstance() {
        return new JoystickFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJoystickBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        widgetViewGroupJoy = view.findViewById(R.id.widget_groupviewjoy);
        widgetViewGroupJoy.setDataListener((DataListener) this);
        widgetViewGroupJoy.setOnWidgetDetailsChanged((WidgetChangeListener) this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        joyViewModel = new ViewModelProvider(this).get(JoystickViewModel.class);

        joyViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            widgetViewGroupJoy.onNewData(data);
        });

    }

    @Override
    public void onNewWidgetData(BaseData data) {
        joyViewModel.publishData(data);
    }

    @Override
    public void onWidgetDetailsChanged(BaseEntity widgetEntity) {
        joyViewModel.updateWidget(widgetEntity);
    }
}