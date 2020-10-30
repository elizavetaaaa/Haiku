package com.example.besmart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.besmart.adapter.DataAdapter;
import com.example.besmart.adapter.ListItem;
import com.example.besmart.adapter.RecOnClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavItemSelectedListener{
    private RecOnClickListener recOnClickListener;
    private DataAdapter adapter;
    private List<ListItem> listData;
    private RecyclerView rcView;
    private String category = "";
    private SharedPreferences pref;
    private final String MOON = "moon";
    private final String LOVE = "love";
    private final String NATURE = "nature";
    private final String LIFE = "life";
    private final String WINTER = "winter";
    private final String SPRING = "spring";
    private final String SUMMER = "summer";
    private final String AUTUMN = "autumn";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupMenu();
        setRecOnClickListener();
        init();
    }
    private void setupMenu() {
        FragmentManager fm = getSupportFragmentManager();
        MenuFragmentList mMenuFragment = (MenuFragmentList) fm.findFragmentById(R.id.id_container_menu);
        if (mMenuFragment == null) {
            mMenuFragment = new MenuFragmentList();
            mMenuFragment.setNavItemSelectedListener(this);
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment).commit();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onNavItemSelectedListener(MenuItem item) {
        //Toast.makeText(this, item.getTitle(),Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.id_favorite:
                updateFav();
                break;
            case R.id.id_moon:
                updateMainList(getResources().getStringArray(R.array.moon),MOON);
                break;
            case R.id.id_love:
                updateMainList(getResources().getStringArray(R.array.love),LOVE);
                break;
           case R.id.id_life:
                updateMainList(getResources().getStringArray(R.array.life),LIFE);
                break;
            case R.id.id_nature:
                updateMainList(getResources().getStringArray(R.array.nature),NATURE);
                break;
            case R.id.id_winter:
                updateMainList(getResources().getStringArray(R.array.winter),WINTER);
                break;
            case R.id.id_spring:
                updateMainList(getResources().getStringArray(R.array.spring),SPRING);
                break;
           case R.id.id_summer:
                updateMainList(getResources().getStringArray(R.array.summer),SUMMER);
                break;
           /* case R.id.id_autumn:
                updateMainList(getResources().getStringArray(R.array.autumn),AUTUMN);
                break;*/

        }

    }

    private void updateMainList(String[] array,String cat)
    {
        category = cat;
        StringBuilder stringBuilder;
        stringBuilder = new StringBuilder();
        String tempCat = pref.getString(cat,"none");
        if(tempCat != null)
        {
            if(tempCat.equals("none"))
            {
                for(int i = 0; i < array.length; i++)
                {
                    stringBuilder.append("0");
                }
                Log.d("MyLog",cat + " " + stringBuilder.toString());
                saveString(stringBuilder.toString());
            }
            else
            {

            }
        }
        List<ListItem> list = new ArrayList<>();
        for(int i = 0; i < array.length; i++)
        {
            ListItem item = new ListItem();
            item.setText(array[i]);
            item.setCat(cat);
            item.setPosition(i);
            list.add(item);
        }

        adapter.updateList(list, false);
    }
    private void updateFav() {

        List<ListItem> listFav = new ArrayList<>();
        List<String[]> listData = new ArrayList<>();
        listData.add(getResources().getStringArray(R.array.moon));
        listData.add(getResources().getStringArray(R.array.love));
        listData.add(getResources().getStringArray(R.array.nature));
        listData.add(getResources().getStringArray(R.array.life));
        listData.add(getResources().getStringArray(R.array.winter));
        listData.add(getResources().getStringArray(R.array.spring));
        listData.add(getResources().getStringArray(R.array.summer));
        //listData.add(getResources().getStringArray(R.array.autumn));


        //String[] cat_array = {MOON, LOVE, NATURE, LIFE,WINTER, SPRING, SUMMER, AUTUMN};
       String[] cat_array = {MOON, LOVE, NATURE, LIFE, WINTER, SPRING, SUMMER};


        for (int i = 0; i < listData.size(); i++)
        {

            for(int p = 0; p < listData.get(i).length; p++)
            {
                String d = pref.getString(cat_array[i],"none");
                if(d != null)if(d.charAt(p) == '1')
                {
                    ListItem item = new ListItem();
                    item.setText(listData.get(i)[p]);
                    item.setPosition(p);
                    item.setCat(cat_array[i]);
                    listFav.add(item);
                }

            }
        }
        adapter.updateList(listFav, true);

    }
    private void init()
    {
        pref = getSharedPreferences("CAT",MODE_PRIVATE);
        rcView = findViewById(R.id.rcView);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        listData = new ArrayList<>();
        String[] moonArray = getResources().getStringArray(R.array.moon);
        adapter = new DataAdapter(this,recOnClickListener,listData);
        updateMainList(moonArray,"moon");
        rcView.setAdapter(adapter);
    }
    private void setRecOnClickListener()
    {
        recOnClickListener = new RecOnClickListener()
        {
            @Override
            public void onItemClicked(int pos)
            {
                //Toast.makeText(MainActivity.this, "Position = " + pos, Toast.LENGTH_SHORT).show();
                String tempCat = pref.getString(category,"none");
                if(tempCat != null)
                {
                    if(tempCat.charAt(pos) == '0')
                    {
                        saveString(replaceCharAtPosition(pos,'1',tempCat));
                    }
                    else
                    {
                        saveString(replaceCharAtPosition(pos,'0',tempCat));
                    }
                }

            }
        };
    }
    private String replaceCharAtPosition(int position, char ch, String st)
    {
        char[] charArray = st.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }
    private void saveString(String stToSave)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(category,stToSave);
        editor.apply();
        Log.d("MyLog","Saved data fav : " + pref.getString(category,"none"));
    }

}
