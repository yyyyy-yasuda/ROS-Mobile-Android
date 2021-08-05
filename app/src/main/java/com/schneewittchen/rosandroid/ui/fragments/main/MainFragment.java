package com.schneewittchen.rosandroid.ui.fragments.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.schneewittchen.rosandroid.R;
import com.schneewittchen.rosandroid.databinding.FragmentIntroBinding;
import com.schneewittchen.rosandroid.databinding.FragmentMainBinding;
import com.schneewittchen.rosandroid.viewmodel.MainViewModel;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class MainFragment extends Fragment implements OnBackPressedListener {

    public static final String TAG = MainFragment.class.getSimpleName();

    TabLayout tabLayout;
    NavController navController;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private FragmentMainBinding mainBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainBinding = FragmentMainBinding.inflate(inflater, container, false);
        View view = mainBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = mainBinding.tabs;
        toolbar = mainBinding.toolbar;
        drawerLayout = mainBinding.drawerLayout;

        navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);

        drawerLayout.setScrimColor(getResources().getColor(R.color.drawerFadeColor));

        // Connect toolbar to application
        if (getActivity() instanceof AppCompatActivity) {

            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.setSupportActionBar(toolbar);

            // Setup home indicator to open drawer layout
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
        }

        // Select Master tab as home
        tabLayout.selectTab(tabLayout.getTabAt(0));
        navController.navigate(R.id.action_to_masterFragment);

        // Setup tabs for navigation
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG, "On Tab selected: " + tab.getText());

                switch (tab.getText().toString()) {
                    case "Master":
                        navController.navigate(R.id.action_to_masterFragment);
                        break;
                    case "Details":
                        navController.navigate(R.id.action_to_detailFragment);
                        break;
                    case "SSH":
                        navController.navigate(R.id.action_to_sshFragment);
                        break;
                    default:
                        navController.navigate(R.id.action_to_vizFragment);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (this.getArguments() != null) {
            mViewModel.createFirstConfig(this.getArguments().getString("configName"));
        }

        mViewModel.getConfigTitle().observe(getViewLifecycleOwner(), this::setTitle);
    }

    private void setTitle(String newTitle) {
        if (newTitle.equals(toolbar.getTitle().toString())) {
            return;
        }

        toolbar.setTitle(newTitle);
    }

    public boolean onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }

        return navController.popBackStack();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainBinding = null;
    }
}
