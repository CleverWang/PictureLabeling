package com.wangcong.picturelabeling.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangcong.picturelabeling.Adapters.NewImageAdapter;
import com.wangcong.picturelabeling.Beans.OnePic;
import com.wangcong.picturelabeling.R;
import com.wangcong.picturelabeling.Utils.GlobalFlags;
import com.wangcong.picturelabeling.Utils.HttpCallbackListener;
import com.wangcong.picturelabeling.Utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by 13307 on 2017/3/22.
 */

public class AllPictureFragment extends Fragment {
    private ArrayList<OnePic> allPics = new ArrayList<>();//保存所有图片的信息
    private NewImageAdapter adapter;
    private SwipeRefreshLayout swipeRefresh;//下拉刷新
    private EditText searchText;
    private TextView loadInfo;
    private LinearLayout linearLayout_info;
    private ImageView smile, cry;
    private String searchContent = "";//搜索关键字
    private final int LOAD_OK = 1;//加载成功消息
    private final int LOAD_FAILED = 2;//加载失败消息
    private final int LOAD_NO_PICS = 3;//加载图片为空消息
    private final int SEARCH_NO_PICS = 4;//搜索无结果消息
    private boolean isSearched = false;//是否已搜索标识

    //消息处理
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_OK:
                    linearLayout_info.setVisibility(View.GONE);
                    //加载完所有图片路径后，通知适配器进行图片加载
                    adapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                    //for (String item : allPaths)
                    //Log.d("path", "onFinish: " + item);
                    break;
                case LOAD_FAILED:
                    linearLayout_info.setVisibility(View.VISIBLE);
                    cry.setVisibility(View.VISIBLE);
                    smile.setVisibility(View.GONE);
                    loadInfo.setText("图片加载失败，请下拉刷新！");
                    //加载失败后清空缓存数据，提示用户
                    allPics.clear();
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(getActivity(), "图片加载失败！", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                    break;
                case LOAD_NO_PICS:
                    linearLayout_info.setVisibility(View.VISIBLE);
                    smile.setVisibility(View.VISIBLE);
                    cry.setVisibility(View.GONE);
                    loadInfo.setText("所有图片都已打过标签，\n可去历史标签进行修改！");
                    //Toast.makeText(getActivity(), "所有图片都已打过标签，可去历史标签进行修改！", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                    break;
                case SEARCH_NO_PICS:
                    Toast.makeText(getActivity(), "未找到图片！", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    public boolean isSearched() {
        return isSearched;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        getAllPicPaths();

        //隐藏“历史记录”中的未判定，已判定按钮
        LinearLayout radio = (LinearLayout) view.findViewById(R.id.radio_button);
        radio.setVisibility(View.GONE);
        linearLayout_info = (LinearLayout) view.findViewById(R.id.linearlayout_load_info);
        smile = (ImageView) view.findViewById(R.id.image_smile);
        cry = (ImageView) view.findViewById(R.id.image_cry);
        cry.setVisibility(View.GONE);
        loadInfo = (TextView) view.findViewById(R.id.textview_load_information);

        searchText = (EditText) view.findViewById(R.id.edit_search_in_all_pic);
        ImageView searchBtn = (ImageView) view.findViewById(R.id.button_search_in_all_pic);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContent = searchText.getText().toString().trim();
                isSearched = true;//搜索标志位设为true
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);//隐藏软键盘
                searchPics();
            }
        });

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        //swipeRefresh.setVisibility(View.GONE);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新后清除搜索信息以及搜索标志位，并加载所有图片
                searchText.setText("");
                isSearched = false;
                getAllPicPaths();
            }
        });
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewImageAdapter(getActivity(), allPics);
        recyclerView.setAdapter(adapter);
        //Log.d(TAG, "onCreateView");
        return view;
    }

    /**
     * 从server获取所有图片的信息
     */
    public void getAllPicPaths() {
        String address = GlobalFlags.getIpAddress() + "showpersonallpicture";
        String params = "pptelephone=" + GlobalFlags.getUserID();
        //Log.d("changeinfo", "setUserInfo: " + address + " " + params);
        HttpUtil.sendHttpRequest(address, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                //Log.d("all", "message: " + response);
                Message message = new Message();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String paths = jsonObject.getString("showpersonallpicture");
                    //Log.d("paths", "onFinish: " + paths);
                    if (paths != null && paths.length() > 0) {
                        allPics.clear();
                        String t[] = paths.split(";");//以;分割，获取每张图片的路径
                        String temp[];
                        for (int i = 0; i < t.length; i++) {
                            temp = t[i].split(",");//temp[0]为图片id，temp[1]为图片路径，temp[2]为推荐标签
                            int index = temp[1].indexOf('\\');//获取\的位置，将\替换成/
                            allPics.add(new OnePic(temp[0], GlobalFlags.getIpAddress() + temp[1].substring(0, index) + "/" + temp[1].substring(index + 1), temp[2]));
                        }
                        message.what = LOAD_OK;
                    } else
                        message.what = LOAD_NO_PICS;
                    handler.sendMessage(message);//所有图片加载完毕，发送加载完毕message
                } catch (JSONException e) {
                    message.what = LOAD_FAILED;
                    handler.sendMessage(message);
                    //Toast.makeText(context, "错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(final Exception e) {
                //Log.d("Get from server", "error message: " + e.toString());
                //Toast.makeText(context, "错误：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                Message message = new Message();
                message.what = LOAD_FAILED;
                handler.sendMessage(message);
            }
        });
    }

    /**
     * 根据搜索关键字进行图片搜索
     */
    public void searchPics() {
        String address = GlobalFlags.getIpAddress() + "picsearch";
        String params = "search=" + searchContent + "&phone=" + GlobalFlags.getUserID();
        //Log.d("search", "searchPics: "+searchContent);
        HttpUtil.sendHttpRequest(address, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                //Log.d("search", "message: " + response);
                Message message = new Message();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String paths = jsonObject.getString("result");
                    if (paths != null && paths.length() > 0) {
                        allPics.clear();
                        String t[] = paths.split(";");//以;分割，获取每张图片的路径
                        String temp[];
                        for (int i = 0; i < t.length; i++) {
                            temp = t[i].split(",");//temp[0]为图片id，temp[1]为图片路径，temp[2]为推荐标签
                            int index = temp[1].indexOf('\\');//获取\的位置，将\替换成/
                            allPics.add(new OnePic(temp[0], GlobalFlags.getIpAddress() + temp[1].substring(0, index) + "/" + temp[1].substring(index + 1), temp[2]));
                        }
                        message.what = LOAD_OK;
                    } else
                        message.what = SEARCH_NO_PICS;
                    handler.sendMessage(message);//所有图片加载完毕，发送加载完毕message
                } catch (JSONException e) {
                    message.what = LOAD_FAILED;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(final Exception e) {
                Message message = new Message();
                message.what = LOAD_FAILED;
                handler.sendMessage(message);
            }
        });
    }
}
