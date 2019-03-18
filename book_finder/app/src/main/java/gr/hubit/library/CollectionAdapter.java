package gr.hubit.library;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CollectionAdapter extends ArrayAdapter<Book> {
    private Context mContext;
    List<Book> mylist;

    public CollectionAdapter(Context _context, List<Book> _mylist) {
        super(_context, R.layout.collection_item, _mylist);

        mContext = _context;
        this.mylist = _mylist;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Book book = getItem(position);

        BookViewHolder holder;

        if (convertView == null) {
            convertView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            convertView = vi.inflate(R.layout.collection_item, parent, false);

            //
            holder = new BookViewHolder();
            holder.img = convertView.findViewById(R.id.collectionimage);
            holder.title = convertView.findViewById(R.id.collectiontitle);

            //
            convertView.setTag(holder);
        }
        else{
            holder = (BookViewHolder) convertView.getTag();
        }

        holder.populate(book);
        //
        return convertView;
    }


    class BookViewHolder {
        public ImageView img;
        public TextView title;

        void populate(Book b) {
            title.setText(b.getTitle());
            img.setImageBitmap(
                    MainActivity.decodeSampledBitmapFromResource(mContext.getResources(), b.getImage(), 100, 100));
        }
    }

}
