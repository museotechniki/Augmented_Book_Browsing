package gr.hubit.library;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ScanFragment extends Fragment {
    private ArrayList<Book> scanedBooks;

    private HashMap<String, Book> bookMap;
    private HashMap<String, Integer> collectionMap;

    private OnFragmentInteractionListener mListener;

    private LinearLayout topLayout;

    private int imagePosition = 0;

    private View thisView;

    private CameraFragment cameraFragment;

    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_scan,
                container, false);
        thisView = view;
        cameraFragment = new CameraFragment(this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.cameraFrame, cameraFragment);
        ft.commit();
        scanedBooks = new ArrayList<>();
        bookMap = new HashMap<>();
        collectionMap = new HashMap<>();
        topLayout = view.findViewById(R.id.bookstocklinear);
        getData();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getData(){
        String jsonArray = ((MainActivity)getActivity()).getJsonData();
        try {
            JSONObject jr = new JSONObject(jsonArray);
            JSONArray booksArray = jr.getJSONArray("Books");
            for(int i=0;i<booksArray.length();i++)
            {
                JSONObject bookJson = booksArray.getJSONObject(i);
                Book book = new Book();
                book.setCode(bookJson.getString("id"));
                book.setTitle(bookJson.getString("title"));
                book.setAuthor(bookJson.getString("author"));
                book.setImage(bookJson.getInt("image"));
                book.setPublicationDate(bookJson.getString("publicationdate"));
                book.setDescription(bookJson.getString("description"));
                book.setBookDescription(bookJson.getString("bookDescription"));
                book.setBriefDescription(bookJson.getString("briefDescription"));
                book.setComment(bookJson.getString("comment"));
                book.setIllustrations(bookJson.getString("illustrations"));
                book.setPublisher(bookJson.getString("publisher"));
                book.setOwnershipDetails(bookJson.getString("ownershipDetails"));
                book.setPlaceOfPublication(bookJson.getString("placeofpublication"));
                HashMap<Integer, ArrayList<Integer>> imageMap = new HashMap();
                JSONArray tmpImagesArray = bookJson.getJSONArray("images");
                for (int j = 0; j<tmpImagesArray.length();j++){
                    ArrayList<Integer> list = new ArrayList<>();
                    JSONObject imageJson = tmpImagesArray.getJSONObject(j);
                    int page = imageJson.getInt("page");
                    JSONArray tmpImages = imageJson.getJSONArray("images");
                    for (int k = 0; k<tmpImages.length();k++){
                        list.add((Integer)tmpImages.get(k));
                    }
                    imageMap.put(page, list);
                }
                book.setimages(imageMap);
                bookMap.put(book.getCode(), book);
                // loop and add it to array or arraylist
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addBook(ArrayList<Book> selectedBooks){
        for(int i=0; i < selectedBooks.size(); i++){
            addImageToTopLayout(selectedBooks.get(i).getImage());
        }
        BookFragment bookFragment = (BookFragment)getFragmentManager().findFragmentById(R.id.list);

        if (bookFragment != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            bookFragment.setSelectedBooks(selectedBooks);
        } else {
            ((MainActivity)getActivity()).setSelectedBooks(selectedBooks);
        }
    }

    public void addBook(String bookCode){

        String corectedCode = bookCode.replace("Kk", "Kk.");
        if(bookCode.contains("Aa")) {
            corectedCode = bookCode.replace("Aa", "Aa.");
        }
        corectedCode = corectedCode.replace("-", ".");
        String[] codes = corectedCode.split("_");

        Book tmpBook = bookMap.get(codes[0]);
        if(tmpBook != null && !scanedBooks.contains(tmpBook)){
            scanedBooks.add(tmpBook);
            if(codes.length > 1) {
                collectionMap.put(codes[0], Integer.valueOf(codes[1]));
            }
            addImageToTopLayout(tmpBook.getImage());
        }
    }

    private void addImageToTopLayout(int resource){
        ImageView newImage = new ImageView(thisView.getContext());
        newImage.setImageBitmap(
                MainActivity.decodeSampledBitmapFromResource(getResources(), resource, 100, 100));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, 160);
        params.setMargins(10,0,0,0);
        newImage.setLayoutParams(params);
        newImage.setId(imagePosition++);
        newImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Book tmpBook = scanedBooks.get(v.getId());
                Intent intent;
                if(collectionMap.containsKey(tmpBook.getCode())){

                    ArrayList<Book> collectionArray = new ArrayList<>();
                    int collectionNum = collectionMap.get(tmpBook.getCode());
                    String[] tmpcode = tmpBook.getCode().split("\\.");
                    for(int i = Integer.valueOf(tmpcode[2]); i < collectionNum; i++){
                        collectionArray.add(bookMap.get(tmpcode[0]+"."+tmpcode[1]+"."+i));
                    }

                    intent = new Intent(getActivity(), BookCollection.class);
                    intent.putParcelableArrayListExtra("collectionArray", collectionArray);
                }else{
                    intent = new Intent(getActivity(), BookActivity.class);
                    intent.putExtra("book", tmpBook);
                }
                startActivity(intent);
            }
        });
        topLayout.addView(newImage);
    }

    private MappedByteBuffer loadModelFile(Activity activity, String MODEL_FILE) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(MODEL_FILE);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}
