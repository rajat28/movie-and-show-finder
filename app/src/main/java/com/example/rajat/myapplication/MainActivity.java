package com.example.rajat.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements OnItemSelectListener {

    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {
                return;
            }

            searchFragment = new SearchFragment();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, searchFragment);
            fragmentTransaction.commit();

        }

        searchFragment.setActivityInstance(this);

    }

    @Override
    public void itemSelected(MovieDetailsFragment movieDetailsFragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, movieDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        getFragmentManager().executePendingTransactions();

    }
}