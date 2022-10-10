package com.yo.test.adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yo.test.Details.upComingItemDetails;
import com.yo.test.fragment.UpComingTabAccomo;
import com.yo.test.fragment.UpComingTabActivities;
import com.yo.test.fragment.UpComingTabHighlights;
import com.yo.test.fragment.UpComingTabImages;

public class UpComingTabAdapter extends FragmentStateAdapter {

    private String P_ID;

    public UpComingTabAdapter(@NonNull FragmentActivity fragmentActivity, String p_id) {
        super(fragmentActivity);
        this.P_ID = p_id;
    }

    public UpComingTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public UpComingTabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UpComingTabHighlights(P_ID);
            case 1:
                return new UpComingTabAccomo(P_ID);
            case 2:
                return new UpComingTabActivities(P_ID);
            case 3:
                return new UpComingTabImages(P_ID);
            default:
                return new UpComingTabHighlights(P_ID);

        }    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
