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
 * 查找用户页面
 */
public class FindUserFragment extends Fragment {
    private FragmentActivity activity;
    private CustomListView UserList;
    private ArrayList<HashMap<String,Object>> data=new ArrayList<HashMap<String, Object>>();
    private static final int LOAD_DATA_FINISH = 10;//上拉刷新
    private static final int REFRESH_DATA_FINISH = 11;//下拉刷新
    private static final String TAG = "FindGroupFragment";
    private int mCount = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.find_user_fragment,container,false);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        for (int i=0;i<20;i++){
            HashMap<String,Object> itemData=new HashMap<String, Object>();
            itemData.put("UserName","中超联盟");
            itemData.put("Distance","距离：500米");
            itemData.put("Time","10分钟之前");
            data.add(itemData);
        }
        UserList=(CustomListView)this.getView().findViewById(R.id.UserList);

        UserList.setAdapter(new MyAdapter());
        UserList.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO 下拉刷新
                Log.e(TAG, "onRefresh");
                loadData(0);
            }

        });
        UserList.setOnLoadListener(new CustomListView.OnLoadMoreListener() {

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
                convertView=layoutInflater.inflate(R.layout.find_user_list_item,parent,false);
            }
            Map<String,Object> itemData=(Map<String,Object>)getItem(position);
            ImageView UserImage=(ImageView)convertView.findViewById(R.id.UserImg);
            TextView UserName=(TextView)convertView.findViewById(R.id.UserName);
            TextView Distance=(TextView)convertView.findViewById(R.id.Distance);
            TextView Time=(TextView)convertView.findViewById(R.id.Time);
            UserName.setText(itemData.get("UserName").toString());
            Distance.setText(itemData.get("Distance").toString());
            Time.setText(itemData.get("Time").toString());
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

                    myHandler.sendEmptyMessage(REFRESH_DATA_FINISH);
                }else if(type==1){

                    myHandler.sendEmptyMessage(LOAD_DATA_FINISH);
                }
            }
        }.start();
    }
    /*
       handler
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
                    UserList.onRefreshComplete();	//下拉刷新完成
                    break;
                case LOAD_DATA_FINISH:
                    if(new MyAdapter()!=null){
                        new MyAdapter().notifyDataSetChanged();
                    }
                    UserList.onLoadMoreComplete();	//加载更多完成
                    break;
                default:
                    break;
            }
        }

    };


}
