package com.example.navigation_smd_7a;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class ProductAdapter extends ArrayAdapter<Product> {
    Context context;
    int resource;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    @SuppressLint("NonConstantResourceId")
    @NonNull
    @Override
    public View getView(int position, @Nullable View v, @NonNull ViewGroup parent) {

        if (v == null) {
            v = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView tvTitle = v.findViewById(R.id.tvName);
        TextView tvPrice = v.findViewById(R.id.tvPrice);
        ImageView ivEdit = v.findViewById(R.id.ivEdit);
        ImageView ivDelete = v.findViewById(R.id.ivDelete);

//
        Product p = getItem(position);
        tvTitle.setText(Objects.requireNonNull(p).getTitle());
        tvPrice.setText(MessageFormat.format("${0}", p.getPrice()));



        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete the product");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProductDB db = new ProductDB(context);
                        db.open();
                        db.remove(p.getId());
                        db.close();
                        remove(p);
                        notifyDataSetChanged();
                    }
                });
                builder.show();

            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);


                View designView = LayoutInflater.from(context).inflate(R.layout.add_new_product_dialog_design, null, false);
                dialog.setView(designView);
                EditText etTitle = designView.findViewById(R.id.etTitle);
                EditText etPrice = designView.findViewById(R.id.etPrice);
                TextView tvHeading = designView.findViewById(R.id.tvHeading);
                etTitle.setText(p.getTitle());
                etPrice.setText("" + p.getPrice());
                tvHeading.setText("Update Product");
                dialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String title = etTitle.getText().toString().trim();
                        String price = etPrice.getText().toString();
                        ProductDB productDB = new ProductDB(context);
                        productDB.open();
                        productDB.updatePrice(p.getId(), title, Integer.parseInt(price));
                        productDB.close();
                        p.setTitle(title);
                        p.setPrice(Integer.parseInt(price));
                        notifyDataSetChanged();
                        Toast.makeText(context, "Product Added", Toast.LENGTH_SHORT).show();

                    }

                });
                dialog.show();
            }

        });

        return v;
    }
}