package com.yo.test.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ActivtiyAdapter1 extends FragmentStateAdapter {

    String Name;

    public ActivtiyAdapter1(@NonNull FragmentActivity fragmentActivity, String name) {
        super(fragmentActivity);
        this.Name = name;
    }

    public ActivtiyAdapter1(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ActivtiyAdapter1(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new OverView(Name);
            case 1:
                return new ActivityImage(Name);
            default:
                return new OverView(Name);

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
