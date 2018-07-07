package com.sarangcode.projecttama;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

@Layout(R.layout.card_view)

public class card {
    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.contactTxt)
    private TextView contactTxt;

    private Profile mProfile;
    private Context mContext;
    private SwipePlaceHolderView mSwipeView;

    public card(Context context, Profile profile, SwipePlaceHolderView swipeView) {
        mContext = context;
        mProfile = profile;
        mSwipeView = swipeView;
    }

    @Resolve
    private void onResolved(){
       // Glide.with(mContext).load(mProfile.getImageUrl()).into(profileImageView);
       //nameTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
        //Glide.with(mContext).load(R.drawable.dwarikahotel).into(profileImageView);
        nameTxt.setText(mProfile.getName());
       // contactTxt.setText(mProfile.getLocation());
        contactTxt.setText(mProfile.getContact());
    }

    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);
    }

    @SwipeCancelState
    private void onSwipeCancelState(){
        Log.d("EVENT", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn(){
        Log.d("EVENT", "onSwipedIn");
        Toast.makeText(mContext,"Detail",Toast.LENGTH_SHORT).show();
    }

    @SwipeInState
    private void onSwipeInState(){
        //Log.d("EVENT", "onSwipeInState");
    //

    }

    @SwipeOutState
    private void onSwipeOutState(){
        Log.d("EVENT", "onSwipeOutState");
    }
}
