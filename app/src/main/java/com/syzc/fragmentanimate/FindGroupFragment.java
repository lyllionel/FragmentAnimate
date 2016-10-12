package com.syzc.fragmentanimate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/** wzj
 * 查找联盟页面
 */
public class FindGroupFragment extends Fragment {
    private FragmentActivity activity;
    private ArrayList<HashMap<String,Object>> data=new ArrayList<HashMap<String, Object>>();
    private CustomListView GroupList;//自定义ListView
    private static final String TAG = "FindGroupFragment";
    private int mCount = 10;//加载数据条数
    private static final int LOAD_DATA_FINISH = 10;//上拉刷新
    private static final int REFRESH_DATA_FINISH = 11;//下拉刷新

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.find_group_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        GroupList=(CustomListView)this.getView().findViewById(R.id.GroupList);
        for (int i=0;i<20;i++){
            HashMap<String,Object> itemData=new HashMap<String, Object>();
            itemData.put("GroupName","中超联盟");
            itemData.put("GroupNub","恒大国安球迷、恒大国安、西班牙球迷大神");
            data.add(itemData);
        }

        GroupList.setAdapter(new MyAdapter());
        GroupList.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 下拉刷新
                Log.e(TAG, "onRefresh");
                loadData(0);
            }

        });
        GroupList.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO 加载更多
                Log.e(TAG, "onLoad");
                loadData(1);
            }
        });
//        //关闭下拉刷新
//        GroupList.setCanRefresh(!GroupList.isCanRefresh());
//        //关闭上拉刷新
//        GroupList.setCanLoadMore(!GroupList.isCanLoadMore());
    }


    /*
               ListView 的Adapter
             */
    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                LayoutInflater layoutInflater=activity.getLayoutInflater();
                convertView=layoutInflater.inflate(R.layout.find_group_list_item,parent,false);
            }
            Map<String,Object> itemData=(Map<String,Object>)getItem(position);
            ImageView GroupImage=(ImageView)convertView.findViewById(R.id.GroupImg);
            TextView GroupName=(TextView)convertView.findViewById(R.id.GroupName);
            TextView GroupNub=(TextView)convertView.findViewById(R.id.GroupNub);
            GroupName.setText(itemData.get("GroupName").toString());
            GroupNub.setText(itemData.get("GroupNub").toString());
            return convertView;
        }
    }
    /*
       上下拉刷新加载数据方法
     */
    public void loadData(final int type){
        new Thread(){
            @Override
            public void run() {
                switch (type) {
                    case 0://这里是下拉刷新

                        break;

                    case 1:
                    //这里是上拉刷新
                        int _Index = mCount + 10;
                        mCount = _Index;
                        break;
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(type==0){//下拉刷新
                    //通知Handler
                    myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
                }else if(type==1){//上拉刷新
                    //通知Handler
                    myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
                }
            }
        }.start();
    }
    /*
       handle
     */
    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_DATA_FINISH:
                    if(new MyAdapter()!=null){
                        new MyAdapter().notifyDataSetChanged();
                    }
                    GroupList.onRefreshComplete();	//下拉刷新完成
                    break;
                case LOAD_DATA_FINISH:
                    if(new MyAdapter()!=null){
                        new MyAdapter().notifyDataSetChanged();
                    }
                    GroupList.onLoadMoreComplete();	//加载更多完成
                    break;
                default:
                    break;
            }
        }

    };


}
