package com.example.tailor0;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import me.relex.circleindicator.CircleIndicator;

public class Photo_fullSreen extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private MyPager myPager;
    File files;
    File[] fls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_sreen);
        Intent intent = getIntent();

        String dir = intent.getStringExtra("dir");
        files = new File(dir);
        fls = files.listFiles();

        myPager = new MyPager(this);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(myPager);
        circleIndicator = findViewById(R.id.circle);
        circleIndicator.setViewPager(viewPager);
    }

    public class MyPager extends PagerAdapter {
        private Context context;
        public MyPager(Context context) {
            this.context = context;
        }
        /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.photo_pager_item, null);
            ImageView imageView = view.findViewById(R.id.image);
            imageView.setImageBitmap(getImageAt(position));
            container.addView(view);
            return view;
        }
        /*
        This callback is responsible for destroying a page. Since we are using view only as the
        object key we just directly remove the view from parent container
        */
        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }
        /*
        Returns the count of the total pages
        */
        @Override
        public int getCount() {
            return fls.length;
        }
        /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }

        private Bitmap getImageAt(int position) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(fls[position].getPath(), bmOptions);
        }
    }
}
