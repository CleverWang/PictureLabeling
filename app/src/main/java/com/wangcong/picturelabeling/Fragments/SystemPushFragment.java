package com.wangcong.picturelabeling.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * Created by 13307 on 2017/3/26.
 */

public class SystemPushFragment extends Fragment {
    private ArrayList<OnePic> allPics = new ArrayList<>();
    private NewImageAdapter adapter;
    private TextView loadInfo;
    private LinearLayout linearLayout_info;
    private ImageView smile, cry;
    private SwipeRefreshLayout swipeRefresh;
    private final static int LOAD_OK = 1;
    private final static int LOAD_FAILED = 2;
    private final static int LOAD_NO_PICS = 3;

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
                    allPics.clear();
                    adapter.notifyDataSetChanged();
                    //Toast.makeText(getActivity(), "图片加载失败！", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                    break;
                case LOAD_NO_PICS:
                    linearLayout_info.setVisibility(View.VISIBLE);
                    smile.setVisibility(View.VISIBLE);
                    cry.setVisibility(View.GONE);
                    loadInfo.setText("今日没有推送图片！");
                    //Toast.makeText(getActivity(), "今日没有推送图片！", Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        getAllPicPaths();

        //隐藏“所有图片”中的搜索界面和“历史标签”中的未判定已判定按钮
        LinearLayout radio = (LinearLayout) view.findViewById(R.id.radio_button);
        LinearLayout search = (LinearLayout) view.findViewById(R.id.linearlayout_search_in_all_pic);
        search.setVisibility(View.GONE);
        radio.setVisibility(View.GONE);
        loadInfo = (TextView) view.findViewById(R.id.textview_load_information);
        linearLayout_info = (LinearLayout) view.findViewById(R.id.linearlayout_load_info);
        smile = (ImageView) view.findViewById(R.id.image_smile);
        cry = (ImageView) view.findViewById(R.id.image_cry);
        cry.setVisibility(View.GONE);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPicPaths();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NewImageAdapter(getActivity(), allPics);
        recyclerView.setAdapter(adapter);
        return view;
    }

    /**
     * 从server获取今日推送图片的信息
     */
    public void getAllPicPaths() {
        String address = GlobalFlags.getIpAddress() + "picput.jsp";
        String params = "pptelephone=" + GlobalFlags.getUserID();
        //Log.d("changeinfo", "setUserInfo: " + address + " " + params);
        HttpUtil.sendHttpRequest(address, params, new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                //Log.d("push", "message: " + response);
                Message message = new Message();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String paths = jsonObject.getString("dayput");
                    //Log.d("paths", "onFinish: " + paths);
                    if (paths != null && paths.length() > 0) {
                        allPics.clear();
                        String t[] = paths.split(";");//以;分割，获取每张图片的路径
                        String[] temp;
                        for (int i = 0; i < t.length; i++) {
                            temp = t[i].split(",");
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
}
