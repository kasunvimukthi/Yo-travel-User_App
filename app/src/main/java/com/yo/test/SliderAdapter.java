package com.yo.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    public int[] slide_image = {
            R.drawable.slide1,
            R.drawable.slide2,
            R.drawable.slide3
    };

    public String[] slide_heading = {
            "Find Guider Location",
            "Easy To Book",
            "Beautiful places"
    };

    public String [] slide_des = {
            "You can find your guider current location using this app",
            "You can manage your booking",
            "we're selected more beautiful places in Sri Lanka"
    };
    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject( View view, Object object) {
        return view == (ConstraintLayout) object;
    }


    @Override
    public Object instantiateItem( ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slider_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slider_heading);
        TextView slideDesc = (TextView) view.findViewById(R.id.slider_desc);

        slideImageView.setImageResource(slide_image[position]);
        slideHeading.setText(slide_heading[position]);
        slideDesc.setText(slide_des[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
