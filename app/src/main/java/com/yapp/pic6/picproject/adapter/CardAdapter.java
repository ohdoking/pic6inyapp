package com.yapp.pic6.picproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yapp.pic6.picproject.R;
import com.yapp.pic6.picproject.dao.Gallery;
import com.yapp.pic6.picproject.service.GalleryHelper;

import java.io.File;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
	ArrayList<Gallery> gallerys;
	GalleryHelper gh;

	Context context;

	Integer currentPosition;

	//원래 기본
	ArrayList<String> originPath;
	//이동 되는 번호
	ArrayList<Integer> tempPath;
	//이동된 경로
	ArrayList<String> movePath;

	ArrayList<Integer> arraySelect;

	TextView imageText;


	public CardAdapter(Context context, ArrayList<Gallery> galleries) {
		super();
		this.context = context;
		originPath = new ArrayList<String>();
		tempPath = new ArrayList<Integer>();
		movePath = new ArrayList<String>();
		arraySelect = new ArrayList<Integer>();
		gh = new GalleryHelper(context);
		this.gallerys = galleries;

	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View v = LayoutInflater.from(viewGroup.getContext())
		.inflate(R.layout.cardview_layout, viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(v);
		return viewHolder;
	}

	public void setArray(ArrayList<String> list){
		originPath = list;
		movePath.addAll(list);
	}
	/*
		temp image path list
			set
			get
			remove
			clear
	 */
	public void setAllTempImagePath(ArrayList<Integer> str){
		tempPath.addAll(str);
	}
	public void setTempImagePath(Integer str){
		tempPath.add(str);
	}
	public ArrayList<Integer> getTempImagePathList(){
		return tempPath;
	}
	public void removeTempImagePath(Integer str){
		tempPath.remove(str);
	}
	public void cleanTempImagePath(){
		tempPath.clear();
	}

	/*
		temp image path list
			set
			setList
			remove
			get
			clear
	 */
	public void setRealImagePath(String str){
		movePath.add(str);
	}
	public void setRealImagePathList(ArrayList<String> str){
		movePath.addAll(str);
	}
	public ArrayList<String> getRealImagePathList(){
		return movePath;
	}
	public void removeRealImagePath(String str){
		movePath.remove(str);
	}
	public void cleanRealImagePath(){
		movePath.clear();
	}


	public ArrayList<String> getOriginImagePathList(){
		return originPath;
	}

	public ArrayList<Integer> getSelectList(){
		return arraySelect;
	}


	public String getPath(ViewHolder viewHolder,int i){
		return viewHolder.currentItem.getImagePath();
	}





	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int i) {
		Gallery gallery = gallerys.get(i);
		currentPosition = i;
		viewHolder.tvMovie.setText(gallery.getName());
		viewHolder.currentItem = gallery;
//		Bitmap resized = null;
//
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inSampleSize = 4;
////		options.inJustDecodeBounds = true;
//		Bitmap bitmapImage = BitmapFactory.decodeFile(gallery.getImagePath(),options);
//		if(bitmapImage != null){
//			resized = Bitmap.createScaledBitmap(bitmapImage, 100, 100, true);
//		}




//		viewHolder.imgThumbnail.setImageBitmap(bitmapImage);
//		viewHolder.imgThumbnail.setImageBitmap(resized);
	}

	@Override
	public int getItemCount() {
		return gallerys.size();
	}

	class ViewHolder extends RecyclerView.ViewHolder{

		public ImageView imgThumbnail;
		public TextView tvMovie;

		public View view;
		public Gallery currentItem;

		public ArrayList<String> imgPaths;



		public ViewHolder(final View itemView) {
			super(itemView);
			imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
			tvMovie = (TextView)itemView.findViewById(R.id.tv_movie);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					imgPaths = movePath;

					LinearLayout r = (LinearLayout) ((ViewGroup) itemView.getParent()).getParent();
					HorizontalScrollView h = (HorizontalScrollView)r.getChildAt(1);
					LinearLayout l1 = (LinearLayout)h.getChildAt(0);
					//FrameLayout f1 = (FrameLayout)l1.getChildAt(0);
					Animation anim_bigtosmall = AnimationUtils.loadAnimation(context, R.anim.anim_bigtosmall);
					//비었을때
					if(tempPath.isEmpty()){
//						Toast.makeText(itemView.getContext(),currentItem.getImagePath() +" - NONE",Toast.LENGTH_SHORT).show();
						Toast.makeText(itemView.getContext(),R.string.main_please_select,Toast.LENGTH_SHORT).show();
					}
					else{

						File f = new File(currentItem.getImagePath());
						String fileName = f.getName();

						File f2 = new File(imgPaths.get(0).toString());
						String fileName2 = f2.getName();

						imgThumbnail.startAnimation(anim_bigtosmall); // folder icon animation

						Animation anim_imgdown = AnimationUtils.loadAnimation(context, R.anim.anim_imgdown);

						for(int i = 0; i < tempPath.size(); i++) { // select image animation
							FrameLayout f1 = (FrameLayout) l1.getChildAt(tempPath.get(i));
							f1.startAnimation(anim_imgdown);

							imageText = (TextView) f1.getChildAt(2);
							imageText.setText(fileName);


						}
//						Toast.makeText(itemView.getContext(), "'" + fileName2 + "' 에서 '" + fileName + "'로 이동 되었습니다.", Toast.LENGTH_SHORT).show();
//						Toast.makeText(itemView.getContext(), tempPath.size() + " 장의 사진이 '" + fileName + "'로 이동 되었습니다 ", Toast.LENGTH_SHORT).show();
						Toast.makeText(itemView.getContext(), tempPath.size() + " "+ itemView.getResources().getString(R.string.main_moved,fileName), Toast.LENGTH_SHORT).show();

						for(Integer imagePath :tempPath){
							movePath.set(imagePath, currentItem.getImagePath());
							Log.i("integer", String.valueOf(imagePath));
//


//							Drawable d = context.getResources().getDrawable(R.mipmap.ic_addfolder);
//							img.setBackground(d);
						}

						for(String str : originPath){
							Log.i("origin", str);
						}
						for(String str : movePath){
							Log.i("move",str);
						}
						for(int j = 0 ;j < arraySelect.size();j++){
							arraySelect.set(j,0);
						}
						tempPath.clear();
					}






				}
			});

		}
	}


}