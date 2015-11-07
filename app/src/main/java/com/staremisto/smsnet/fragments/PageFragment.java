package com.staremisto.smsnet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.staremisto.smsnet.R;
import com.staremisto.smsnet.adapters.DirectionsCardViewAdapter;
import com.staremisto.smsnet.data.RouteInstruction;

import java.util.ArrayList;
import java.util.List;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;
    private RecyclerView mRecyclerView;
    private DirectionsCardViewAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.directives_card_list);
        List<RouteInstruction> route = new ArrayList<>();
        mAdapter = new DirectionsCardViewAdapter(getActivity().getApplicationContext(), R.layout.direction_card_view_item_w,route);


        layoutManager =new GridLayoutManager(getActivity().getApplicationContext(), 1);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void updateInfoFromSMS(String text) {
        List<RouteInstruction> array= new ArrayList<>();
        String[] textArray = text.split("\\|");
        for (String inst : textArray) {
            String[] arr = inst.split(";");

            array.add(new RouteInstruction(arr[0], arr[1], Html.fromHtml(arr[2]).toString()));
        }
        mAdapter.setTitles(array);
        mAdapter.notifyDataSetChanged();
    }
}