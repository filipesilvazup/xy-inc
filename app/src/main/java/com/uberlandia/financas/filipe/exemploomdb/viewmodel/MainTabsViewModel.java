package com.uberlandia.financas.filipe.exemploomdb.viewmodel;

import android.databinding.ObservableField;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toolbar;

public class MainTabsViewModel {
    public ObservableField<Toolbar> toolbar;
    public ObservableField<TabLayout> tabLayout;
    public ObservableField<ViewPager> viewPager;
}
