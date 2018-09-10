package com.uberlandia.financas.filipe.exemploomdb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.uberlandia.financas.filipe.exemploomdb.R;
import com.uberlandia.financas.filipe.exemploomdb.databinding.ActivityMainTabsBinding;
import com.uberlandia.financas.filipe.exemploomdb.view.BuscaFragment;
import com.uberlandia.financas.filipe.exemploomdb.view.GaleriaFragment;
import com.uberlandia.financas.filipe.exemploomdb.viewmodel.MainTabsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainTabsActivity extends AppCompatActivity {

   /* private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;*/
    private static ViewPagerAdapter adapter;

    ActivityMainTabsBinding binding;
    MainTabsViewModel mainTabsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // App.get(this).component().inject(this);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_main_tabs);
         binding.setHandler(this);
        binding.setManager(getSupportFragmentManager());

        //binding.setManager(getSupportFragmentManager());

        mainTabsViewModel = new MainTabsViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_tabs);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);

        //viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(binding.viewpager);

        //tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(viewPager);
        binding.tabs.setupWithViewPager(binding.viewpager);
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        GaleriaFragment galeriaFragment = new GaleriaFragment();
        BuscaFragment buscaFragment = new BuscaFragment();
        adapter.addFragment(0, galeriaFragment, "Busca");
        //adapter.addFragment(0, buscaFragment, "Galeria");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(int position, Fragment fragment, String title) {
            mFragmentList.add(position, fragment);
            mFragmentTitleList.add(position, title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }
}
