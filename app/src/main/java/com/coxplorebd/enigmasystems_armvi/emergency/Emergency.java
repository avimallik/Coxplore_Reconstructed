package com.coxplorebd.enigmasystems_armvi.emergency;

import android.os.Bundle;

import com.coxplorebd.enigmasystems_armvi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.coxplorebd.enigmasystems_armvi.databinding.ActivityEmergencyBinding;

public class Emergency extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        BottomNavigationView bottomNavigationTransportView = (BottomNavigationView)
                findViewById(R.id.navigation_emergency);

        bottomNavigationTransportView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = HospitalFragment.newInstance();
                                break;
                            case R.id.action_item2:
                                selectedFragment = ATMFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = PoliceFragmet.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout_emergency, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_emergency, HospitalFragment.newInstance());
        transaction.commit();

    }
}