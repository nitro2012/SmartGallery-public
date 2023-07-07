package com.google.mlkit.vision.smartGallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.mlkit.vision.smartGallery.java.Database.AppDatabase;
import com.google.mlkit.vision.smartGallery.java.Database.Pics;
import com.google.mlkit.vision.smartGallery.java.Database.PictTagDao;
import com.google.mlkit.vision.smartGallery.java.Database.TagsPicPair;
import com.google.mlkit.vision.smartGallery.java.StillImageActivity;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener  {

    Toolbar toolbar;
    FloatingActionButton fab;
    MenuItem menuitem;
    SearchView searchView;
    private List<String> lastSearches;

    private MaterialSearchBar searchBar;
    public RecyclerView galleryRecyclerView;
GalleryAdapter adapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBar = findViewById(R.id.searchBar);
        searchBar.setOnSearchActionListener(this);
        lastSearches=new ArrayList<>();
        lastSearches.add("default");
        searchBar.setSpeechMode(false);

        searchBar.setSuggstionsClickListener(new SuggestionsAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClickListener(int position, View v) {
            }

            @Override
            public void OnItemDeleteListener(int position, View v) {

            }
        });
        searchBar.setLastSuggestions(lastSearches);
        searchBar.setHint("Search");


        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }



        });




        toolbar = findViewById(R.id.toobar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.cam_float);

        galleryRecyclerView=findViewById(R.id.galleryRecycler);
        getImages();

        fab.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Camera Selected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, StillImageActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        getImages();
    }

    private void getImages() {

        ImageSaver is=new ImageSaver(this);
        File dir=is.getAlbumStorageDir("smartGallery");

        String path = dir.getPath();//Environment.getExternalStorageDirectory().toString()+"/smartGallery";

        File directory = new File(path);
        File[] files = directory.listFiles();

        if(files != null) {
            List<File> imageList = new ArrayList<>(Arrays.asList(files).subList(0, Objects.requireNonNull(files).length));

            if(imageList.size()!=0)
            { adapter=new GalleryAdapter(MainActivity.this,imageList);
                galleryRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                galleryRecyclerView.setAdapter(adapter);}
        }




    }


    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_NAVIGATION:
                Toast.makeText(MainActivity.this, "Clicked!! " , Toast.LENGTH_SHORT).show();
                break;
            case MaterialSearchBar.BUTTON_SPEECH:
                break;
            case MaterialSearchBar.BUTTON_BACK:
                getImages();
                break;
        }
    }
    @Override
    public void onSearchStateChanged(boolean enabled) {
        String s = enabled ? "enabled" : "disabled";
        Toast.makeText(MainActivity.this, "Search " + s, Toast.LENGTH_SHORT).show();
        if(!enabled)
            getImages();
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        if(text==null)
            return;
        AtomicReference<Boolean> fl= new AtomicReference<>(false);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        AppDatabase db = Room.databaseBuilder(this,
                AppDatabase.class, "database-name").enableMultiInstanceInvalidation().build();
        PictTagDao dao= db.pictTagDao();

        executor.execute(() -> {
            List<TagsPicPair> tpp=null;
            try {
                 tpp=dao.getTagPicPair(text.toString().toLowerCase());

            }
            catch (Exception e){
                Toast.makeText(this,"not found",Toast.LENGTH_SHORT).show();
            }
            List<String> imageList=new ArrayList<>();

            if(tpp.size()!=0){
            fl.set(true);
            for(Pics tagpic : tpp.get(0).picsArray){
                imageList.add(tagpic.PicId);
            }

            handler.post(() -> {

                if(imageList.size()!=0)
                { adapter=new GalleryAdapter(MainActivity.this,true,imageList);
                    galleryRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
                    galleryRecyclerView.setAdapter(adapter);}
            });

                startSearch(text.toString(), true, null, true);
            }

        });
if(!fl.get())
        Toast.makeText(MainActivity.this, "not found" , Toast.LENGTH_SHORT).show();


    }

}