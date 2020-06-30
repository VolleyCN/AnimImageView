# AnimImageView
# FlowLayout

**流式布局，适合用于标签，分类筛选等。适配器方式调用，支持单选，多选，自用显示，单行显示（类似listview），指定显示条数（类似gridview）**

![demo展示](https://github.com/VolleyCN/AnimationImageView/blob/master/img/demo.gif "demo展示")

------------

### 怎么样使用？

### 1.在项目根build.gradle中
    allprojects {
        repositories {
            jcenter()
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }
### 2.在项目build.gradle中
### java 版本
    dependencies {
    	 implementation 'com.github.VolleyCN:AnimImageView:Tag'
    }
### 布局文件
```XML
 <com.volleycn.animimageview.AnimationImageView
        android:id="@+id/AnimationImageView"
        android:layout_width="60dp"
        android:layout_height="60dp"
        android:layout_gravity="bottom|right"
        android:layout_margin="20dp"
        app:aiv_anim_circle_src="@drawable/shape_bg_ded9fb_round"
        app:aiv_circle_src="@drawable/shape_bg_bdb3f8_round"
        app:aiv_image_src="@mipmap/icon10" />
```
### 代码使用
```java
 AnimationImageView imageView = findViewById(R.id.AnimationImageView);
 Glide.with(imageView.getContext())
         .load(R.mipmap.icon10)
         .optionalTransform(new RoundedCorners(SizeUtils.dp2px(4)))
         .placeholder(R.drawable.avatar_default)
         .into(imageView.getImageView());
```
