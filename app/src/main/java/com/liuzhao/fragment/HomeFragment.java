package com.liuzhao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.Liu.adapter.helper.BaseAdapterHelper;
import com.Liu.adapter.helper.QuickAdapter;
import com.Liu.lwidgetutil.AutoGallery;
import com.Liu.lwidgetutil.FlowIndicator;
import com.Liu.utils.PullToRefreshListView;
import com.liuzhao.Bean.AdHeadBean;
import com.liuzhao.Bean.CategoryBean;
import com.liuzhao.Bean.NewsBean;
import com.liuzhao.R;
import com.liuzhao.activity.ItNewsDetaActivity;
import com.liuzhao.biz.HeadDataManager;
import com.liuzhao.biz.NewsDataManager;
import com.liuzhao.common.DefineView;
import com.liuzhao.fragment.base.BaseFragment;
import com.liuzhao.utils.OkhttpManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2月06日0006.
 */

public class HomeFragment extends BaseFragment implements DefineView {
    private  View mView;

    private CategoryBean categoryBean;

    private static final  String KEY = "EXTRA";
    private  List<NewsBean>  newsBeans;
    private QuickAdapter<NewsBean> quickAdapter;
    private PullToRefreshListView home_listview;
    private FrameLayout home_frameLayout;
    private LinearLayout error,empty,loading;
    private static  String mask;
    private String[] masks;
    private  int[] masks_colors;
    private View headView;
    private LayoutInflater minflater;
    private AutoGallery headline_image_gallery;
    private FlowIndicator headline_circle_indicator;
    private int gallerySelectedPositon = 0;//Gallerys索引

    private int circleSelectedPosition = 0; // 默认指示器的圆圈的位置为第一
    private List<AdHeadBean> adHeadBeens ;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    private  int page = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       if (mView == null){
           mView = inflater.inflate(R.layout.home_fragment_layout,container,false);
           minflater= LayoutInflater.from(getActivity());
           headView = minflater.inflate(R.layout.gallery_indicator_layout,null);
            initView();
           initValidata();
           initListener();
       }

        return mView;
    }
    public static HomeFragment newInstance(CategoryBean extra){
        Bundle bundle = new Bundle();
        mask = extra.getTitle();
        //序列化的Category
        bundle.putSerializable(KEY,extra);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle!=null){
            categoryBean =(CategoryBean) bundle.getSerializable(KEY);
        }

    }

    @Override
    public void initView() {

        home_listview= (PullToRefreshListView) mView.findViewById(R.id.home_listview);
        home_frameLayout = (FrameLayout) mView.findViewById(R.id.home_framelayout);
        error = (LinearLayout) mView.findViewById(R.id.error);
        loading = (LinearLayout) mView.findViewById(R.id.loading);
        empty = (LinearLayout) mView.findViewById(R.id.empty);
        home_listview.addHeaderView(headView);
        headline_image_gallery = (AutoGallery) headView.findViewById(R.id.headline_image_gallery);
        headline_circle_indicator= (FlowIndicator) headView.findViewById(R.id.headline_circle_indicator);
        //tv_page.setText(categoryBean.getTitle());

    }

    @Override
    public void initValidata() {
        //Universal-Image-Loader 得到ImageLoader 的实例 单例模式
        mImageLoader = ImageLoader.getInstance();
        //Universal-Image-Loader 设置
        options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.defaultbg).cacheInMemory(true)
                .cacheOnDisk(true).build();

        masks = new String[]{"业界","评论","人物","活动互动","软媒动态","囧科技","创业","通信","电商","微软","苹果"};
        masks_colors= new int[]{R.color.mask_tags_1,R.color.mask_tags_2
                ,R.color.mask_tags_3,R.color.mask_tags_4,R.color.mask_tags_5
                ,R.color.mask_tags_6,R.color.mask_tags_7,R.color.mask_tags_8
                ,R.color.mask_tags_9,R.color.mask_tags_10,R.color.mask_tags_11

        };
        home_listview.setVisibility(View.GONE);
        home_frameLayout.setVisibility(View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        empty.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        String asd = "http://baijia.baidu.com/ajax/labellatestarticle?pagesize=100&prevarticalid=771266&flagtogether=1&labelid=3&page="+page;
        OkhttpManager.getAsync(categoryBean.getHref(), new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.d("liuzhao","首页加载失败");
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result);

                adHeadBeens = new HeadDataManager().getHeadBeans(document);

                Log.d("liuzhaohomefrag",""+categoryBean.getTitle());
                if (adHeadBeens !=null && newsBeans !=null){
                    home_listview.setVisibility(View.VISIBLE);
                    home_frameLayout.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    bindData();
                }else {
                    home_listview.setVisibility(View.GONE);
                    home_frameLayout.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                }

            }
        });
        OkhttpManager.getAsync(asd, new OkhttpManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) {
                newsBeans = new NewsDataManager().getNewsBeans(result);

                if (adHeadBeens !=null && newsBeans !=null){
                    home_listview.setVisibility(View.VISIBLE);
                    home_frameLayout.setVisibility(View.GONE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                    bindData();
                }else {
                    home_listview.setVisibility(View.GONE);
                    home_frameLayout.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                    error.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void initListener() {
        home_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mintent = new Intent(getActivity(), ItNewsDetaActivity.class);
                mintent.putExtra("titleUrl",newsBeans.get((int) id).getM_display_url());
                getActivity().startActivity(mintent);
            }
        });
        headline_image_gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mintent = new Intent(getActivity(), ItNewsDetaActivity.class);
                mintent.putExtra("titleUrl",adHeadBeens.get((int)id%adHeadBeens.size()).getHref());
                getActivity().startActivity(mintent);
            }
        });

    }

    @Override
    public void bindData() {
        int topSize = adHeadBeens.size();
        //设置指示器
        headline_circle_indicator.setCount(topSize);
        headline_circle_indicator.setSeletion(circleSelectedPosition);
        //设置 Gallery
        headline_image_gallery.setLength(topSize);
        gallerySelectedPositon = topSize*50+circleSelectedPosition;
        Log.d("liuzhaoGallery",gallerySelectedPositon+"");
        headline_image_gallery.setSelection(gallerySelectedPositon);
        headline_image_gallery.setDelayMillis(4000);
        headline_image_gallery.start();

        headline_image_gallery.setAdapter(new GalleryAdapter());
        headline_image_gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                circleSelectedPosition=position;
                gallerySelectedPositon=circleSelectedPosition%adHeadBeens.size();
                headline_circle_indicator.setSeletion(gallerySelectedPositon);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

            quickAdapter = new QuickAdapter<NewsBean>(getContext(), R.layout.item_home_news_layout, newsBeans) {
                @Override
                protected void convert(BaseAdapterHelper helper, NewsBean item) {
                    Log.d("liuzhao-imgurl", item.getM_display_url());
                    helper.setText(R.id.item_news_tv_ci, item.hashCode()+"）")
                            .setText(R.id.item_news_tv_time, item.getM_create_time())
                            .setText(R.id.item_news_tv_title, item.getM_title())
                            .setText(R.id.item_news_tv_source,item.getM_writer_name())
                            .setImageUrl(R.id.item_news_iv_news, item.getM_image_url());

                    mImageLoader.displayImage(item.getM_image_url(), (ImageView) helper.getView(R.id.item_news_iv_news),options);
                    //根据标签名设置颜色
                    int index = 0;
                    for (int i = 0; i < masks.length; i++) {
                        if (masks[i].equals(mask)) {
                            index++;
                            break;
                        }
                    }
                    helper.getView(R.id.item_news_tv_arrow).setBackgroundColor(getActivity().getResources().getColor(masks_colors[index]));
                }
            };
            home_listview.setAdapter(quickAdapter);

    }
    class GalleryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object getItem(int position) {
            return adHeadBeens.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder holder = null;
            if (convertView == null){
                holder = new Holder();
                convertView = minflater.inflate(R.layout.item_gallery_layout,null);
                holder.item_head_gallery_img = (ImageView) convertView.findViewById(R.id.item_head_gallery_img);
                holder.item_head_gallery_text = (TextView) convertView.findViewById(R.id.item_head_gallery_text);
                convertView.setTag(holder);
            }else {
                holder = (Holder) convertView.getTag();
            }
            //显示数据
          //  Picasso.with(mView.getContext()).load(adHeadBeens.get(position%adHeadBeens.size()).getImgurl()).into(holder.item_head_gallery_img);
            mImageLoader.displayImage(adHeadBeens.get(position%adHeadBeens.size()).getImgurl(), holder.item_head_gallery_img,options);
            holder.item_head_gallery_text.setText(adHeadBeens.get(position%adHeadBeens.size()).getTitle());
            return convertView;
        }
    }
    private static  class Holder{
        ImageView item_head_gallery_img;
        TextView item_head_gallery_text;
    }
}
