package kz.growit.altynorda;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Drawer getDrawer(final Activity activity, Toolbar toolbar) {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(getResources().getDrawable(R.drawable.profile_img))

                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        , new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new GitHub Account")
                                .withIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_upload)
                                        .actionBar().paddingDp(5).colorRes(R.color.material_drawer_primary_text)),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(FontAwesome.Icon.faw_search)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();
        return new DrawerBuilder()
                .withAccountHeader(headerResult)
                .withActivity(activity)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName("Поиск")
                                .withIcon(FontAwesome.Icon.faw_home)
                                .withIdentifier(1)
                                .withBadge("99"),
                        new PrimaryDrawerItem()
                                .withName(R.string.drawer_item_agents_agent)
                                .withIdentifier(2)

                                .withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem()
                                .withName("Избранное")
                                .withIdentifier(3)

                                .withIcon(FontAwesome.Icon.faw_eye)
                                .withBadge("6"),

                        new PrimaryDrawerItem()
                                .withName("Лента новостей")
                                .withIdentifier(3)

                                .withIcon(FontAwesome.Icon.faw_eye),
                        new PrimaryDrawerItem()
                                .withName("Сообщения")
                                .withIdentifier(4)

                                .withIcon(FontAwesome.Icon.faw_eye),

                        new SectionDrawerItem()
                                .withIdentifier(4)

                                .withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem()
                                .withName("Помощь")
                                .withIdentifier(5)

                                .withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem()
                                .withName("О программе")
                                .withIdentifier(6)

                                .withIcon(FontAwesome.Icon.faw_question),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem()
                                .withName(R.string.drawer_item_login)
                                .withIdentifier(7)

                                .withIcon(FontAwesome.Icon.faw_user)
                                .withBadge("12+")
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        switch (iDrawerItem.getIdentifier()) {
                            case 1:
                                activity.startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                return false;
                            case 2:
                                activity.startActivity(new Intent(getApplicationContext(), AgenciesActivity.class));
                                return false;
                            case 3:
                                activity.startActivity(new Intent(getApplicationContext(), FavoritesActivity.class));
                                return false;
                            case 4:
                                return false;
                            case 6:
                                Toast.makeText(getApplicationContext(), "open choose", Toast.LENGTH_SHORT).show();
                                return false;
                            case 7:
                                activity.startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                return false;

                            default:
                                return false;
                        }
                    }
                })
                .build();
    }

}
