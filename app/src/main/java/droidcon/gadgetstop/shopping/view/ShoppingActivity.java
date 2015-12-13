package droidcon.gadgetstop.shopping.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import droidcon.gadgetstop.R;
import droidcon.gadgetstop.shopping.cart.model.ProductInCart;

public class ShoppingActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private long numOfProductsInTheCart = 0;
  private List<Fragment> tabs;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.shopping_activity);
    setupActionBar();
    createFragments();
    setupViewPager();
  }

  @Override
  protected void onResume() {
    super.onResume();
    setNumOfProductsInCart();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.cart_menu, menu);

    final MenuItem item = menu.findItem(R.id.cart);
    MenuItemCompat.setActionView(item, R.layout.cart_update_count);
    View count = item.getActionView();
    Button cart = (Button) count.findViewById(R.id.num_of_products);
    cart.setText(String.valueOf(numOfProductsInTheCart));
    return super.onCreateOptionsMenu(menu);
  }

  private void createFragments() {
    tabs = new ArrayList<>();
    tabs.add(new ElectronicsFragment());
    tabs.add(new AccessoriesFragment());
  }

  private void setupViewPager() {
    TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
    tabLayout.addTab(tabLayout.newTab().setText(R.string.electronics));
    tabLayout.addTab(tabLayout.newTab().setText(R.string.accessories));
    tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    viewPager = (ViewPager) findViewById(R.id.viewPager);
    viewPager.setAdapter(getCategoryAdapter(tabLayout.getTabCount()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  private void setupActionBar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  private void setNumOfProductsInCart(){
    numOfProductsInTheCart = ProductInCart.count(ProductInCart.class, null, null);
    invalidateOptionsMenu();
  }

  @NonNull
  private FragmentPagerAdapter getCategoryAdapter(final int count) {
    return new FragmentPagerAdapter(getSupportFragmentManager()) {
      @Override
      public Fragment getItem(int position) {
        return tabs.get(position);
      }

      @Override
      public int getCount() {
        return count;
      }
    };
  }
}
