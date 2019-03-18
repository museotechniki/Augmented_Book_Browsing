package gr.hubit.library;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.DialogFragment;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

public class ScanedBooksPopup extends DialogFragment {

    private ArrayList<Book> scanedBooks;
    private RecyclerView mRecyclerView;
    private RecyclerPopupAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.popup_scaned_books, null);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.scanedBooksRecycler);
        mRecyclerAdapter = new RecyclerPopupAdapter(scanedBooks);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mRecyclerAdapter);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final ScanFragment targetFragment = (ScanFragment)getTargetFragment();
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        targetFragment.addBook(mRecyclerAdapter.getSelectedBooks());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ScanedBooksPopup.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }

    public void setScannedBooksList(ArrayList<Book> scanedBooks){
        this.scanedBooks = scanedBooks;
    }
}
