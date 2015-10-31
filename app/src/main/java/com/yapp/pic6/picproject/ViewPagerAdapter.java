package com.yapp.pic6.picproject;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewPagerAdapter extends PagerAdapter {
	// Declare Variables
	Context context;
	String[] tutorialText;
	int[] tutorialImage;
	int[] indicator;
	LayoutInflater inflater;
 
	public ViewPagerAdapter(Context context, String[] tutorialText, int[] indicator,
			 int[] tutorialImage) {
		this.context = context;

		this.tutorialImage = tutorialImage;
		this.tutorialText = tutorialText;
		this.indicator = indicator;
	}
 
	@Override
	public int getCount() {
		return 5;
	}
 
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}
 
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
 
		// Declare Variables
		TextView txtTutorial;
		ImageView imgTutorial;
		ImageView imgIndicator;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.viewpager_item, container,
				false);
 
		// Locate the TextViews in viewpager_item.xml
		txtTutorial = (TextView) itemView.findViewById(R.id.tutorialText);
		imgTutorial = (ImageView) itemView.findViewById(R.id.tutorialImage);
		imgIndicator = (ImageView) itemView.findViewById(R.id.indicator);

		//tutorialText.setTypeface(Typeface.createFromAsset(getAssets(), "NotoSansCJKkr-Thin.otf"));
		// Capture position and set to the TextViews
		txtTutorial.setText(tutorialText[position]);
		imgTutorial.setImageResource(tutorialImage[position]);
		imgIndicator.setImageResource(indicator[position]);
		// Locate the ImageView in viewpager_item.xml

 
		// Add viewpager_item.xml to ViewPager
		((ViewPager) container).addView(itemView);
 
		return itemView;
	}
 
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// Remove viewpager_item.xml from ViewPager
		((ViewPager) container).removeView((RelativeLayout) object);
 
	}
}