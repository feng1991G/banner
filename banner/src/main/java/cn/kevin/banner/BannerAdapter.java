package cn.kevin.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2017/7/18.
 *
 * @author yangjinhai
 */

public class BannerAdapter extends PagerAdapter {
    private List<IBannerItem> itemList = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private ImageLoader imageLoader;
    private int indexCount;
    private static final int MIN_ITEM_COUNT = 6;

    public void setData(Context context, List<? extends IBannerItem> items){
        indexCount = items.size();
        itemList.clear();
        while(itemList.size() < MIN_ITEM_COUNT){
            itemList.addAll(items);
        }
        createImageView(context, itemList);
    }

    private void createImageView(Context context, List<? extends IBannerItem> items) {
        imageViews.clear();
        for (int i = 0; i < items.size(); i ++){
            ImageView itemView = new ImageView(context);
            itemView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(itemView);
        }
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    IBannerItem getItem(int position){
        return itemList.get(getRealPosition(position));
    }

    int getItemCount(){
        return itemList.size();
    }

    public int getIndexCount(){
        return indexCount;
    }

    int getIndexPosition(int position){
        return position % getIndexCount();
    }

    int getRealPosition(int position){
        return position % itemList.size();
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPosition(position);
        IBannerItem item = itemList.get(realPosition);

        ImageView v = imageViews.get(realPosition);
        if (null != v.getParent())
            ((ViewGroup)v.getParent()).removeView(v);

        try {
            container.addView(v);
            if(imageLoader != null)
                imageLoader.onDisplayImage(v.getContext(), v, item.ImageUrl());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }
}
