package com.thesaugat.instafeed.ui.home.story;

import android.os.Bundle;

import androidx.annotation.IntDef;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.thesaugat.instafeed.R;


public class SingleStoryFragment extends Fragment {


    @IntDef({NODIR, UP, DOWN, LEFT, RIGHT})
    public @interface AnimationDirection {
    }

    public static final int NODIR = 0;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    private static final long DURATION = 500;


    private static final String ARG_PARAM1 = "direction";


    private int direction;

    public SingleStoryFragment() {
        // Required empty public constructor
    }


    public static SingleStoryFragment newInstance(@AnimationDirection int direction) {
        SingleStoryFragment fragment = new SingleStoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, direction);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            direction = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_single_story, container, false);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        switch (getArguments().getInt("direction")) {
            case LEFT:
                return MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION);
            case RIGHT:
                return MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION);
        }
        return null;
    }

}