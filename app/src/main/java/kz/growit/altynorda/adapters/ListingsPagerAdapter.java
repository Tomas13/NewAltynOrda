package kz.growit.altynorda.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import kz.growit.altynorda.R;
import kz.growit.altynorda.fragments.ListingsListFragment;
import kz.growit.altynorda.fragments.ListingsMapFragment;

/**
 * Created by jean on 9/7/2015.
 */
public class ListingsPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[]{"СПИСОК", "КАРТА"};
    private Context context;
    private Fragment f1,f2;
    private FragmentManager fragmentManager;

    public ListingsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
        f1 = new ListingsListFragment();
        f2 = new ListingsMapFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return f1;
            case 1:
                return f2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        Drawable image;
        int IconSize = 18;
        switch (position) {
            case 0:
                image = new IconicsDrawable(context)
                        .icon(FontAwesome.Icon.faw_list)
                        .color(context.getResources().getColor(R.color.colorAccent))
                        .sizeDp(IconSize);
                break;
            case 1:
                image = new IconicsDrawable(context)
                        .icon(FontAwesome.Icon.faw_globe)
                        .color(context.getResources().getColor(R.color.colorAccent))
                        .sizeDp(IconSize);
                break;
            default:
                image = new IconicsDrawable(context)
                        .icon(FontAwesome.Icon.faw_globe)
                        .color(context.getColor(R.color.colorAccent))
                        .sizeDp(IconSize);
                break;
        }

        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
    }
}
