package com.example.navigation_smd_7a;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewOrderFragment extends Fragment {

    Context context;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewOrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewOrderFragment newInstance(String param1, String param2) {
        NewOrderFragment fragment = new NewOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lvNewOrderList = view.findViewById(R.id.lvNewOrdersList);
        FloatingActionButton fab = view.findViewById(R.id.fab_add);

        ProductDB productDB = new ProductDB(context);
        productDB.open();
        ArrayList<Product> products = productDB.fetchNewProducts();
        productDB.close();

        ProductAdapter adapter = new ProductAdapter(context, R.layout.product_item_design, products);
        lvNewOrderList.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View v = LayoutInflater.from(context).inflate(R.layout.add_new_product_dialog_design, null, false);
                dialog.setView(v);

                EditText etTitle = v.findViewById(R.id.etTitle);
                EditText etPrice = v.findViewById(R.id.etPrice);

                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = etTitle.getText().toString().trim();
                        String price = etPrice.getText().toString();

                        if (!title.isEmpty() && !price.isEmpty()) {
                            try {
                                int priceValue = Integer.parseInt(price);
                                ProductDB productDB = new ProductDB(context);
                                productDB.open();
                                productDB.insert(title, priceValue);
                                products.clear();
                                products.addAll(productDB.fetchNewProducts());
                                productDB.close();

                                adapter.notifyDataSetChanged();  // Notify adapter of changes
                                Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show();
                            } catch (NumberFormatException e) {
                                Toast.makeText(context, "Invalid price", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Title or price cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Dismiss dialog
                    }
                });

                dialog.show();
            }
        });
    }

}