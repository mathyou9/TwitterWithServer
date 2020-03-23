package edu.byu.cs.tweeter.client.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.view.main.Login.LoginFragment;
import edu.byu.cs.tweeter.client.view.main.Login.RegisterFragment;

public class LoginPagerAdapter extends FragmentPagerAdapter {
    private static final int LOGIN_FRAGMENT_POSITION = 0;
    private static final int REGISTER_FRAGMENT_POSITION = 1;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.Login, R.string.signUp};
    private final Context mContext;

    public LoginPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == LOGIN_FRAGMENT_POSITION){
            return new LoginFragment();
        } else {
            return new RegisterFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
