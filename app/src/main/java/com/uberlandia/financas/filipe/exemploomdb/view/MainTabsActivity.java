package com.uberlandia.financas.filipe.exemploomdb.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentStatePagerAdapter;
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
        mainTabsViewModel = new MainTabsViewModel();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_tabs);
        binding.setMainTabsViewModel(mainTabsViewModel);
        binding.tabs.setupWithViewPager(binding.viewpager);
        binding.setHandler(this);
        binding.setManager(getSupportFragmentManager());
        setSupportActionBar(binding.toolbar);
        setupViewPager();
    }


    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BuscaFragment(), "Busca");
        adapter.addFragment(new GaleriaFragment(), "Galeria");
        binding.viewpager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList = new ArrayList<>();
        private List<String> mFragmentTitleList = new ArrayList<>();

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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
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
