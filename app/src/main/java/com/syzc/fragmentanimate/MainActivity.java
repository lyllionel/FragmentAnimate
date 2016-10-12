package com.syzc.fragmentanimate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments;
    private ImageView find_group_but;
    private ImageView find_user_but;
    private FragmentManager mFragmentManager = null;
    private Fragment mTextFragmentOne = null;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.find_fragment_activity);
        initView();
    }
    /*
       初始化View
     */
    private void initView(){
        find_group_but=(ImageView)findViewById(R.id.find_group_but);
        find_user_but=(ImageView)findViewById(R.id.find_user_but);
        fragments=new Fragment[2];
        fragments[0]=getSupportFragmentManager().findFragmentById(R.id.fragment_FindGroup);
        fragments[1]=getSupportFragmentManager().findFragmentById(R.id.fragment_FindUser);
        removeFragment();
        addFragment();
        find_group_but.setEnabled(false);
        find_user_but.setEnabled(true);
        getSupportFragmentManager().beginTransaction().hide(fragments[1]).show(fragments[0]).commit();

    }



    //Fragment显示动画
    private void addFragment() {
        if (null == mFragmentManager) {
            mFragmentManager = getSupportFragmentManager();
        }


        android.support.v4.app.FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();
        if(fragments[0].isHidden()==true){
            System.out.println("<<<<<<<000000");
            fragmentTransaction.setCustomAnimations(
                    R.anim.push_right_in,
                    R.anim.push_right_out,
                    R.anim.push_right_in,
                    R.anim.push_right_out);
            mTextFragmentOne = new FindGroupFragment();
            fragmentTransaction.add(R.id.fragment_FindGroup,mTextFragmentOne);


        }if(fragments[1].isHidden()==true){
            System.out.println("<<<<<<<111111");
            fragmentTransaction.setCustomAnimations(
                    R.anim.push_left_in,
                    R.anim.push_left_out,
                    R.anim.push_left_in,
                    R.anim.push_left_out);
            mTextFragmentOne = new FindUserFragment();
            fragmentTransaction.add(R.id.fragment_FindUser,mTextFragmentOne);

        }

        mTextFragmentOne = new FindGroupFragment();
        fragmentTransaction.add(R.id.fragment_FindGroup,mTextFragmentOne);

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    //Fragment 消失动画
    private void removeFragment() {
        if (null == mFragmentManager) {
            mFragmentManager = getSupportFragmentManager();
        }
        mFragmentManager.popBackStack();
    }
    /*
       显示查找联盟页面
     */
    public void FindGroupClick(View view){
        removeFragment();
        addFragment();
        find_group_but.setEnabled(false);
        find_user_but.setEnabled(true);
        getSupportFragmentManager().beginTransaction().hide(fragments[1]).show(fragments[0]).commit();


    }
    /*
      显示查找用户页面
    */
    public void FindUserClick(View view){
        removeFragment();
        addFragment();
        find_group_but.setEnabled(true);
        find_user_but.setEnabled(false);
        getSupportFragmentManager().beginTransaction().hide(fragments[0]).show(fragments[1]).commit();

    }
}
