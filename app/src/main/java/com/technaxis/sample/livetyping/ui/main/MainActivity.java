package com.technaxis.sample.livetyping.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.technaxis.sample.livetyping.R;
import com.technaxis.sample.livetyping.ui.filter.CategoriesFilterFragment;
import com.technaxis.sample.livetyping.ui.drinks.DrinksFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openFilter() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_right, R.anim.exit_right, R.anim.enter_right, R.anim.exit_right)
                .replace(R.id.fragment_container, new CategoriesFilterFragment())
                .addToBackStack("filter")
                .commit();
    }

    public void applyFilter() {
        getSupportFragmentManager().popBackStack("filter", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        ((DrinksFragment) getSupportFragmentManager().findFragmentById(R.id.drinks_fragment))
                .refresh(false);
    }
}
