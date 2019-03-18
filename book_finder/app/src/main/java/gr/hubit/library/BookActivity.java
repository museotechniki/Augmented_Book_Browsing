package gr.hubit.library;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.HashMap;

public class BookActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private Book selectedBook;

    private int pages = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        SpringDotsIndicator dotsIndicator = findViewById(R.id.spring_dots_indicator);
        dotsIndicator.setViewPager(mViewPager);

        Bundle data = getIntent().getExtras();
        selectedBook = data.getParcelable("book");

        TextView booktitle = findViewById(R.id.bookTitle);
        booktitle.setText(selectedBook.getTitle());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public Book getSelectedBook(){
        return selectedBook;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_book_details, container, false);
            TextView textView = rootView.findViewById(R.id.section_label);

            Book selectedBook = ((BookActivity)getActivity()).getSelectedBook();

            String bookText = "";
            if(getArguments().getInt(ARG_SECTION_NUMBER) == 1){
                bookText = "<b>Code:</b> "+selectedBook.getCode()+"<br/>";
                bookText += "<b>Title:</b> "+selectedBook.getTitle()+"<br/>";
                if(!selectedBook.getAuthor().equals(""))
                    bookText += "<b>Author:</b> "+selectedBook.getAuthor()+"<br/>";
                if(!selectedBook.getPublicationDate().equals(""))
                    bookText += "<b>Published Date:</b> "+selectedBook.getPublicationDate()+"<br/>";
                if(!selectedBook.getPlaceOfPublication().equals(""))
                    bookText += "<b>Place of publication:</b> "+selectedBook.getPlaceOfPublication()+"<br/>";
                if(!selectedBook.getPublisher().equals(""))
                    bookText += "<b>Publisher:</b> "+selectedBook.getPublisher()+"<br/>";
                if(!selectedBook.getOwnershipDetails().equals(""))
                    bookText += "<b>Ownership:</b> "+selectedBook.getOwnershipDetails()+"<br/>";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
                bookText = "<b>Description:</b> "+ selectedBook.getDescription();
                if(!selectedBook.getIllustrations().equals(""))
                    bookText += "Illustrations: "+selectedBook.getIllustrations()+"<br/>";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                bookText = "<b>Description:</b> "+ selectedBook.getBriefDescription();
                bookText = selectedBook.getComment();
            }else if(getArguments().getInt(ARG_SECTION_NUMBER) == 4){
                bookText = "<b>Bind Description:</b> "+ selectedBook.getBookDescription();
            }


            textView.setText(Html.fromHtml(bookText));
            ImageView image1 = rootView.findViewById(R.id.slideshowImage1);
            ImageView image2 = rootView.findViewById(R.id.slideshowImage2);
            ImageView image3 = rootView.findViewById(R.id.slideshowImage3);
            ImageView image4 = rootView.findViewById(R.id.slideshowImage4);

            HashMap<Integer, ArrayList<Integer>> imagesMap = selectedBook.getImages();

            if(imagesMap.containsKey(getArguments().getInt(ARG_SECTION_NUMBER))){

                ArrayList<Integer> images = imagesMap.get(getArguments().getInt(ARG_SECTION_NUMBER));


                for(int i = 0; i < images.size(); i++) {

                    final ImagePopup imagePopup = new ImagePopup(getContext());
                    imagePopup.setWindowHeight(600);
                    imagePopup.setWindowWidth(600);
                    imagePopup.setFullScreen(true);

                    Bitmap imgBitmap = MainActivity.decodeSampledBitmapFromResource(getResources(), images.get(i), 280, 280);

                    if(i == 0) {
                        image1.setImageBitmap(imgBitmap);
                        imagePopup.initiatePopup(image1.getDrawable());
                        image1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imagePopup.viewPopup();
                            }
                        });
                    }else if(i == 1){
                        image2.setImageBitmap(imgBitmap);
                        imagePopup.initiatePopup(image2.getDrawable());
                        image2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imagePopup.viewPopup();
                            }
                        });
                    }else if(i == 2){
                        image3.setImageBitmap(imgBitmap);
                        imagePopup.initiatePopup(image3.getDrawable());
                        image3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imagePopup.viewPopup();
                            }
                        });
                    }
                    else if(i == 3){
                        image4.setImageBitmap(imgBitmap);
                        imagePopup.initiatePopup(image4.getDrawable());
                        image4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imagePopup.viewPopup();
                            }
                        });
                    }

                }
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return pages;
        }
    }
}
