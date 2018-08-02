package com.agenciaalcateia.minhapizzaria.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.agenciaalcateia.minhapizzaria.CardapioFragment;
import com.agenciaalcateia.minhapizzaria.PedidosFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numeroDeTabs;

    public PagerAdapter(FragmentManager fm, int numeroDeTabs) {
        super(fm);
        this.numeroDeTabs = numeroDeTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new CardapioFragment();
            case 1:
                return new PedidosFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numeroDeTabs;
    }
}
