package gr.hubit.library;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerPopupAdapter extends RecyclerView.Adapter<RecyclerPopupAdapter.RecyclerItemViewHolder> {

    private ArrayList<Book> myList;
    private ArrayList<Book> selectedBooks;
    int mLastPosition = 0;

    public RecyclerPopupAdapter(ArrayList<Book> myList) {
        this.myList = myList;
        selectedBooks = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerPopupAdapter.RecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_popup_item_layout, parent, false);
        RecyclerItemViewHolder holder = new RecyclerItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPopupAdapter.RecyclerItemViewHolder holder, int position) {
        holder.etTitleTextView.setText(myList.get(position).getTitle());
        holder.crossImage.setImageBitmap(
                MainActivity.decodeSampledBitmapFromResource(holder.itemView.getResources(), myList.get(position).getImage(), 100, 100));
        mLastPosition =position;
    }

    @Override
    public int getItemCount() {
        return(null != myList?myList.size():0);
    }

    public class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView etTitleTextView;
        private LinearLayout mainLayout;
        private CheckBox bookCheckBox;
        public ImageView crossImage;
        public RecyclerItemViewHolder(final View parent) {
            super(parent);
            etTitleTextView = parent.findViewById(R.id.bookPopupTitle);
            crossImage = parent.findViewById(R.id.bookPopupImage);
            bookCheckBox = parent.findViewById(R.id.selectPopupBook);
            bookCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addBook(bookCheckBox.isChecked(), getAdapterPosition());
                }
            });
            mainLayout = parent.findViewById(R.id.bookPopupLinear);
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addBook(!bookCheckBox.isChecked(), getAdapterPosition());
                    if(!bookCheckBox.isChecked()) {
                        bookCheckBox.setChecked(true);
                    }else {
                        bookCheckBox.setChecked(false);
                    }
                }
            });
        }


    }

    public void addBook(Boolean checked, int i){
        if(checked) {
            if(!selectedBooks.contains(myList.get(i)))
                selectedBooks.add(myList.get(i));
        }else {
            if(selectedBooks.contains(myList.get(i)))
                selectedBooks.remove(myList.get(i));
        }
    }

    public ArrayList<Book> getSelectedBooks() {
        return selectedBooks;
    }
}
