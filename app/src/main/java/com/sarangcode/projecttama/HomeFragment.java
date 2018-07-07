package com.sarangcode.projecttama;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.sarangcode.projecttama.utilities.Utils;
import android.view.WindowManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SwipePlaceHolderView mSwipeView;
    private Context mContext;



    public HomeFragment() {
        // Required empty public constructor
    }
    public static HomeFragment newInstance() {
       HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_home_layout, container, false);
        mSwipeView =(SwipePlaceHolderView)rootview.findViewById(R.id.swipeView);
        mContext = rootview.getContext();
        int bottomMargin = Utils.dpToPx(160);
        Point windowSize = Utils.getDisplaySize(getActivity().getWindowManager());
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y - bottomMargin)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.swipe_out_msg_view));


        for(Profile profile : Utils.loadProfiles(this.getContext())){
            mSwipeView.addView(new card(mContext, profile, mSwipeView));
        }

        return rootview;
    }

}
