package com.example.user.quancafe.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.adapter.MenuAdapter;
import com.example.user.quancafe.activity.fragment.DasboardFragment;
import com.example.user.quancafe.activity.fragment.HomeFragment;
import com.example.user.quancafe.activity.fragment.NotificationFragment;
import com.example.user.quancafe.activity.fragment.ProfileFragment;
import com.example.user.quancafe.activity.model.Menu;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.user.quancafe.R.id.drawerLayout;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayoutMain;
    private Fragment fragmentMain;
    private Toolbar toolbar;
    private NavigationView naviView;
    private BottomNavigationView naviBottom;

    private ArrayList<Menu> arrayMenu;
    private ArrayList<Menu> arrayCauHinh;
    private MenuAdapter adapterMenu;
    private MenuAdapter adapterCauHinh;
    private ListView listViewMenu, listViewCauHinh;
    private ImageButton imageViewCauHinh;
    private ImageButton imageViewMenu;
    public static Context context;

    private CircleImageView circleImageUserSignIn;
    private TextView textUserSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ActionBar();
        CatchChonItemListViewMenu();
        CatchChonITemListVIewSetting();


        ActionClickIconListView();
        ActionClickIconAgainView();
        ActionNavigationBottom();
        SetUpBottomNavigationCounter(2);

    }

    private void SetUpBottomNavigationCounter(int count) {
        BottomNavigationMenuView bottomNavigationMenuView =
                (BottomNavigationMenuView) naviBottom.getChildAt(0);
        View v = bottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        View badge = LayoutInflater.from(this)
                .inflate(R.layout.notificiation_badge, bottomNavigationMenuView, false);

        itemView.addView(badge);
        TextView txtnotification = (TextView) findViewById(R.id.txtnotificationsbadge);

        // set count
        if (count >= 99){
               txtnotification.setText(99+"+");
            // set Background
            txtnotification.setBackgroundResource(R.drawable.custom_circle_shape);

        }else{
            if(count == 0){

            }else{
                // set count
                txtnotification.setText(count+"");
                // set Background
                txtnotification.setBackgroundResource(R.drawable.custom_circle_shape);


            }
        }

    }

    private void ActionNavigationBottom() {
        //naviView.setNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
        naviBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragmentMain = new HomeFragment();
                        break;
                    case R.id.navigation_dashboard:
                        fragmentMain = new DasboardFragment();
                        break;
                    case R.id.navigation_notifiaction:
                        fragmentMain = new NotificationFragment();
                        break;
                    case R.id.navigation_profile:
                        fragmentMain = new ProfileFragment();
                }
                return loadFragment(fragmentMain);
            }
        });


//
//        BottomNavigationMenuView bottomNavigationMenuView =
//                (BottomNavigationMenuView) navigationView.getChildAt(0);
//        View v = bottomNavigationMenuView.getChildAt(3);
//        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
//
//        View badge = LayoutInflater.from(this)
//                .inflate(R.layout.notification_badge, bottomNavigationMenuView, false);
//
//        itemView.addView(badge);
//


    }
    private boolean loadFragment(Fragment fragment){
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutMain, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void ActionClickIconAgainView() {
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewCauHinh.setVisibility(View.INVISIBLE);
                listViewMenu.setVisibility(View.VISIBLE);
                imageViewMenu.setVisibility(View.INVISIBLE);
                imageViewCauHinh.setVisibility(View.VISIBLE);
            }
        });
    }

    private void ActionClickIconListView() {
        imageViewCauHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewCauHinh.setVisibility(View.VISIBLE);
                listViewMenu.setVisibility(View.INVISIBLE);
                imageViewMenu.setVisibility(View.VISIBLE);
                imageViewCauHinh.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void CatchChonItemListViewMenu() {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        //đóng drawer
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
//                        Intent intentDSDK = new Intent(MainActivity.this, DanhSachDangKyActivity.class);
//                        startActivity(intentDSDK);
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
//                        Intent intentQLSK = new Intent(MainActivity.this, XemSuKienActivity.class);
//                        startActivity(intentQLSK);
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                }
            }
        });

    }
    private void CatchChonITemListVIewSetting() {
        listViewCauHinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        //đóng drawer
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        SendToLogIn();
                        drawerLayoutMain.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void SendToLogIn() {
        LogInActivity.isLogin = false;
        Intent intent = new Intent(getApplicationContext(),LogInActivity.class);
        startActivity(intent);
        finish();
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayoutMain.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        drawerLayoutMain = (DrawerLayout) findViewById(drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolBarMain);
        naviView = (NavigationView) findViewById(R.id.navigationViewMain);
        naviBottom = (BottomNavigationView) findViewById(R.id.bottomNavigationMain);

        imageViewCauHinh = (ImageButton) findViewById(R.id.imge_user_main_sua);
        imageViewMenu = (ImageButton) findViewById(R.id.imge_user_main_menu);


        circleImageUserSignIn = (CircleImageView) findViewById(R.id.image_main_user_sign_in);
        textUserSignIn = (TextView) findViewById(R.id.textview_main_user_sign_in);

        listViewMenu = (ListView) findViewById(R.id.listViewMainNavigation);
        listViewCauHinh = (ListView) findViewById(R.id.listViewCauHinhTaiKhoan);

        arrayCauHinh = new ArrayList<>();
        arrayCauHinh.add(0, new Menu("Cấu hình", R.drawable.home));
        arrayCauHinh.add(1, new Menu("Thoát tài khoản", R.drawable.home));
        adapterCauHinh = new MenuAdapter(arrayCauHinh, getApplicationContext());
        listViewCauHinh.setAdapter(adapterCauHinh);




        arrayMenu = new ArrayList<>();
        arrayMenu.add(0, new Menu("Trang chủ", R.drawable.home));
        arrayMenu.add(1,new Menu("Quản lý người dùng",R.drawable.search));
        arrayMenu.add(2, new Menu("Quản lý sự kiện", R.drawable.contact));

        // đưa dữ liệu vào adapter
        adapterMenu = new MenuAdapter(arrayMenu,getApplicationContext());
        // đổi các adapter vào lít view
        listViewMenu.setAdapter(adapterMenu);


        listViewCauHinh.setVisibility(View.INVISIBLE);
        listViewMenu.setVisibility(View.VISIBLE);
        imageViewMenu.setVisibility(View.INVISIBLE);
        imageViewCauHinh.setVisibility(View.VISIBLE);
    }

}
