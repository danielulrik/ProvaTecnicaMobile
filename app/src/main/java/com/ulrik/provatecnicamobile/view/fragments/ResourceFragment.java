package com.ulrik.provatecnicamobile.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ulrik.provatecnicamobile.R;
import com.ulrik.provatecnicamobile.model.Post;
import com.ulrik.provatecnicamobile.view.SimpleDividerItemDecoration;
import com.ulrik.provatecnicamobile.viewmodel.ResourcesViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class ResourceFragment extends Fragment {

    protected ResourcesViewModel resourcesViewModel;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public ResourceFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        resourcesViewModel = new ResourcesViewModel();
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(view.getContext()));
        load();
        return view;
    }

    public abstract void load();

}
