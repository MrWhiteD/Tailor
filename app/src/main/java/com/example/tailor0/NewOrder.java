package com.example.tailor0;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.ProductType;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewOrder extends AppCompatActivity {

    private static final String EXTRA_ORDER_ID = "order_id";
    private static final int REQUEST_CODE_PHOTO = 1;
    private static final int REQUEST_CODE_DRAW = 2;
    private static final int REQUEST_CODE_MERKI = 3;
    private int DIALOG_DATE = 1;
    private Long orderId, custId;

    EditText etDateStart, etDateFitting, etDateEnd, etNotes;
    TextView tvCustomer;
    private Spinner spinCustomer, spinProdType;
    private  ArrayAdapter packageTypesAdapter, spinProdTypeAdapter;

    Calendar dateAndTime= Calendar.getInstance();
    private String currentPhotoPath;
    File dir;

    public static Intent newIntent(Context context, Long orderId){
        Intent i = new Intent(context, NewOrder.class);
        i.putExtra(EXTRA_ORDER_ID, orderId);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        orderId = getIntent().getLongExtra(EXTRA_ORDER_ID, -1);
        custId = getIntent().getLongExtra("cust_id", -1);

        tvCustomer = findViewById(R.id.tvCustomer);
        spinProdType = findViewById(R.id.spinProdType);
        etDateStart = findViewById(R.id.etDateStart);
        etDateFitting = findViewById(R.id.etDateFitting);
        etDateEnd = findViewById(R.id.etDateEnd);
        etNotes = findViewById(R.id.etNotes);

        Toolbar tool = findViewById(R.id.tbOrderCard);
        spinCustomer = findViewById(R.id.spinCustomer);
        spinProdType = findViewById(R.id.spinProdType);
        final Button btnSaveOrder = findViewById(R.id.btnSaveOrder);

        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        spinProdTypeAdapter = new MyAdapter(this, R.layout.simple_spinner_item, "prod");
        spinProdType.setAdapter(spinProdTypeAdapter);
        productViewModel.getAllProd().observe(this, new Observer<List<ProductType>>() {
            @Override
            public void onChanged(@Nullable final List<ProductType> productTypes) {
                // Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                for(int i=0; i<productTypes.size(); i++) {
                    spinProdTypeAdapter.add(productTypes.get(i));
                }
            }
        });
        spinProdTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(orderId == -1){
            tool.setTitle("Новый заказ");
            findViewById(R.id.tvCustomer).setVisibility(View.GONE);
            findViewById(R.id.flImages).setVisibility(View.GONE);
            CustomerViewModel customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
//        final ArrayAdapter packageTypesAdapter = new ArrayAdapter(this, R.layout.simple_spinner_item);
            packageTypesAdapter = new MyAdapter(this, R.layout.simple_spinner_item, "cust");
//        spinCustomer.setAdapter(new MyAdapter(this, R.layout.simple_spinner_item));
            spinCustomer.setAdapter(packageTypesAdapter);
            customerViewModel.getAllCust().observe(this, new Observer<List<Customer>>() {
                @Override
                public void onChanged(@Nullable final List<Customer> customers) {
                    // Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                    for(int i=0; i<customers.size(); i++) {
                        packageTypesAdapter.add(customers.get(i));
                    }
                }
            });
            packageTypesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        } else {
            tool.setTitle("Заказ номер " + orderId.toString());
            spinCustomer.setVisibility(View.GONE);
            findViewById(R.id.tvCustomer).setVisibility(View.VISIBLE);
            tvCustomer.setText(getIntent().getStringExtra("cust_fio"));
            etDateStart.setText(getIntent().getStringExtra("dateStart"));
            etDateFitting.setText(getIntent().getStringExtra("dateFitting"));
            etDateEnd.setText(getIntent().getStringExtra("dateEnd"));
            etNotes.setText(getIntent().getStringExtra("note"));

            dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/orders/"+orderId);
            String[] list = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.matches(".+jpg");
                }
            });

            for (String file : list) {
                file = dir.getPath() + "/" + file;
                setPic(file);
            }
        }


        btnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
//                packageTypesAdapter.getPosition()
/*
*/
            }
        });

    }

    private void onSave() {
        Intent replyIntent = new Intent();

        if(orderId != -1){
            replyIntent.putExtra("orderId", orderId);
            replyIntent.putExtra("cust_id", custId);
        }else {
//            SpinnerAdapter spa = spinCustomer.getAdapter();
            int position = spinCustomer.getSelectedItemPosition();
            Customer customer = (Customer) packageTypesAdapter.getItem(position);
            replyIntent.putExtra("cust_id", customer.id);
        }
        ProductType productType = (ProductType) spinProdType.getSelectedItem();
//        SpinnerAdapter spa1 = spinProdType.getAdapter();
        ;
        replyIntent.putExtra("dateStart", etDateStart.getText().toString());
        replyIntent.putExtra("dateFitting", etDateFitting.getText().toString());
        replyIntent.putExtra("dateEnd", etDateEnd.getText().toString());
        replyIntent.putExtra("note", etNotes.getText().toString());
        replyIntent.putExtra("productType_id", productType.id);

        setResult(RESULT_OK, replyIntent);

        finish();
    }

    public void OnPhotoClick(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
//                Log.d(TAG, "Ошибка");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.tailor.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES+"/orders/"+orderId);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void setPic(String currentPhotoPath) {
        ImageView ivPhoto = new ImageView(this);
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewOrder.this, Photo_fullSreen.class);
                intent.putExtra("dir", dir.getPath());
                startActivity(intent);
            }
        });
        ivPhoto.setVisibility(View.VISIBLE);
//            ViewGroup.LayoutParams params = img.getLayoutParams();
//            params.width = 120;
//            params.height = 120;
        ivPhoto.setLayoutParams(new ViewGroup.LayoutParams(350, 400));
        LinearLayout imgCont = findViewById(R.id.imgCont);
        imgCont.addView(ivPhoto);
        // Get the dimensions of the View
        int targetW = ivPhoto.getLayoutParams().width;
        int targetH = ivPhoto.getLayoutParams().height;
//        int targetW = 150;
//        int targetH = 150;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        ivPhoto.setImageBitmap(bitmap);
    }

    public void OnDrawClick(View view) {
        Intent intent = new Intent(this, DrawActivity.class);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
//                Log.d(TAG, "Ошибка");
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.tailor.fileprovider",
                    photoFile);
            intent.putExtra("currentPhotoPath", currentPhotoPath);
            startActivityForResult(intent, REQUEST_CODE_DRAW);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_PHOTO) {
            if (resultCode == RESULT_OK) {
                setPic(currentPhotoPath);
            } else {
                File file = new File(currentPhotoPath);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        if (requestCode == REQUEST_CODE_DRAW) {
            if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DRAW) {
                setPic(currentPhotoPath);
            }else{
                File file = new File(currentPhotoPath);
                if (file.exists()) {
                    file.delete();
                }
            }

        }
    }
    public void OnDateClick(final View view) {
        new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker dpview, int year, int month, int dayOfMonth) {
                        month++;
                        if(view.getId() == R.id.etDateStart){
                            etDateStart.setText(dayOfMonth + "." + month + "." + year);
                        } else if(view.getId() == R.id.etDateFitting){
                            etDateFitting.setText(dayOfMonth + "." + month + "." + year);
                        }if(view.getId() == R.id.etDateEnd){
                            etDateEnd.setText(dayOfMonth + "." + month + "." + year);
                        }
                    }
                },
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
        Log.d("sss", dateAndTime.toString());
    }

    // установка начальных даты и времени
    private void setInitialDateTime(View view) {
        etDateStart.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime(view);
        }
    };

    public void OnMerkiClick(View view) {
        Intent intent = new Intent(this, Cust2MerkiActivity.class);
        int position = spinCustomer.getSelectedItemPosition();
        if (custId == -1) {
            Customer customer = (Customer) packageTypesAdapter.getItem(position);
            intent.putExtra("custId", customer.id);
        }else
            intent.putExtra("custId", custId);
        startActivityForResult(intent, REQUEST_CODE_MERKI);
    }

    // Creating an Adapter Class
    class MyAdapter extends ArrayAdapter {

//        private List<Customer> mCustomers = Collections.emptyList(); // Cached copy of words
        private String spinnerType;

        public MyAdapter(Context context, int textViewResourceId, String spinnerType) {
            super(context, textViewResourceId);
            this.spinnerType = spinnerType;
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            // Inflating the layout for the custom Spinner
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.simple_spinner_item, parent, false);

            // Declaring and Typecasting the textview in the inflated layout
            TextView tvLanguage = layout .findViewById(R.id.tvLanguage);

            // Setting the text using the array
            if (spinnerType == "cust") {
                tvLanguage.setText(((Customer) this.getItem(position)).fio);
            }
            if (spinnerType == "prod"){
                tvLanguage.setText(((ProductType) this.getItem(position)).name);
            }
//            Customer current = mCustomers.get(position);
//            tvLanguage.setText(current.fio);

            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }
}
