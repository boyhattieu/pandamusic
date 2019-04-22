package com.pdc.pandamusic.layout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ItemTab extends LinearLayout {

    private ImageView imgTab;
    private TextView txtTab;
    private int position;
    private IOnClickItem onClickItem;
    private LayoutParams paramsImg;
    private LayoutParams paramsTxt;

    public void setOnClickItem(IOnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public ItemTab(Context context, int res, String text, int width, int position) {
        super(context);
        this.position = position;
        initView(res, text, width);
    }

    public void isSelected(boolean isSelect){
        if (isSelect){
            imgTab.setColorFilter(Color.parseColor("#ffffff"));
            txtTab.setTextColor(Color.parseColor("#ffffff"));
        } else {
            imgTab.setColorFilter(Color.BLACK);
            txtTab.setTextColor(Color.BLACK);
        }
    }

    private void initView(int res, String text, int width) {
        LayoutParams layoutParams = new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        setGravity(Gravity.CENTER);

        imgTab = new ImageView(getContext());
        txtTab = new TextView(getContext());
        setOrientation(VERTICAL);

        paramsImg = new LayoutParams(dpToPx(28), dpToPx(28));
        paramsTxt = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        setContainer(res, text);

        addView(imgTab);
        addView(txtTab);

        imgTab.setColorFilter(Color.BLACK);
        txtTab.setTextColor(Color.BLACK);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItem.onClickInterface(position);
            }
        });
    }

    private void setContainer(int res, String text) {
        Glide.with(getContext()).load(res).into(imgTab);
        txtTab.setText(text);
        imgTab.setLayoutParams(paramsImg);
        txtTab.setLayoutParams(paramsTxt);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public interface IOnClickItem{
        void onClickInterface(int position);
    }
}
