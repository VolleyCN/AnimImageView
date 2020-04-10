package com.volleycn.sample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.volleycn.animimageview.AnimationImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int[] icons = {R.mipmap.icon_1, R.mipmap.icon2, R.mipmap.icon3, R.mipmap.icon5, R.mipmap.icon7, R.mipmap.icon8, R.mipmap.icon9,
            R.mipmap.icon10, R.mipmap.icon11, R.mipmap.icon12};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnimationImageView imageView = findViewById(R.id.AnimationImageView);
        Glide.with(imageView.getContext())
                .load(R.mipmap.icon10)
                .optionalTransform(new RoundedCorners(SizeUtils.dp2px(4)))
                .placeholder(R.drawable.avatar_default)
                .into(imageView.getImageView());
        initViewDataINM(imageView);
        initViewData();
    }

    private void initViewDataINM(AnimationImageView imageView) {
        imageView.setEnablePlay(!imageView.isEnablePlay());
        imageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initViewDataINM(imageView);
            }
        }, 3000);

    }


    private void initViewData() {
        List<UserInfoEntity> entities = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            entities.add(new UserInfoEntity(icons[i], "Zeck " + ('Z' - i), "hello world!", i < 5 ? true : false));
        }
        for (int i = 0; i < icons.length; i++) {
            entities.add(new UserInfoEntity(icons[i], "Jeck " + ('J' - i), "hello world!", i < 5 ? true : false));
        }
        for (int i = 0; i < icons.length; i++) {
            entities.add(new UserInfoEntity(icons[i], "Meck " + ('M' - i), "hello world!", i < 5 ? true : false));
        }
        for (int i = 0; i < icons.length; i++) {
            entities.add(new UserInfoEntity(icons[i], "Ceck " + ('C' - i), "hello world!", i < 5 ? true : false));
        }
        for (int i = 0; i < icons.length; i++) {
            entities.add(new UserInfoEntity(icons[i], "Keck " + ('K' - i), "hello world!", i < 5 ? true : false));
        }
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new BaseQuickAdapter<UserInfoEntity, BaseViewHolder>(R.layout.adapter_user_item, entities) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, UserInfoEntity userInfoEntity) {
                baseViewHolder.setText(R.id.user_name, userInfoEntity.getName());
                baseViewHolder.setText(R.id.user_info, userInfoEntity.getInfo());
                AnimationImageView imageView = baseViewHolder.getView(R.id.AnimationImageView);
                Glide.with(imageView.getContext())
                        .load(userInfoEntity.getIconRes())
                        .circleCrop()
                        .placeholder(R.drawable.avatar_default)
                        .into(imageView.getImageView());
                imageView.setEnablePlay(userInfoEntity.isOnline());
            }
        });
    }
}
