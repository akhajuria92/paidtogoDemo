package com.aaronevans.paidtogo.ui.ComplaintContactActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.aaronevans.paidtogo.R;
import com.aaronevans.paidtogo.ui.BaseFragment;
import com.aaronevans.paidtogo.ui.GlideApp;
import com.aaronevans.paidtogo.ui.Utils.FilePath;
import com.jaredrummler.android.device.DeviceName;

import java.io.File;
import java.io.IOException;
import androidx.annotation.RequiresApi;
import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.aaronevans.paidtogo.ui.main.MainActivity.mToolbar;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_image;
import static com.aaronevans.paidtogo.ui.main.MainActivity.toolbar_title;

public class ComplaintContactFragment extends BaseFragment implements ComplaintContract.View {

    EditText etname;
    EditText etemail;
    EditText ettitle;
    EditText etreason;
    EditText etdesciption;

    LinearLayout ll__img_vdeo;
    private static final int SELECT_FILE = 200;
    private String image_path_profile = "";
    ImageView iv_back;
    TextView tv_submit;
    Bitmap bitmap;
    MyProgressBar myProgressBar = null;
    private ComplaintContract.Presenter mpresenter;
    private ProgressDialog mProgressDialog = null;
    File finalFile = null;
    Uri selectedImageUri;
    private static final int PICK_FILE_REQUEST = 1;
    private String selectedFilePath = "";
    private String TAG = "ComplaintFragment";

    String device_details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_complaint_contact, container, false);
        ll__img_vdeo = view.findViewById(R.id.ll__img_vdeo);
        iv_back = view.findViewById(R.id.iv_back);
        tv_submit = view.findViewById(R.id.tv_submit);

        etname = view.findViewById(R.id.etname);
        etemail = view.findViewById(R.id.etemail);
        ettitle = view.findViewById(R.id.ettitle);
        etreason = view.findViewById(R.id.etreason);
        etdesciption = view.findViewById(R.id.etdesciption);
        mpresenter = new ComplaintPresenter().start(this);

        ll__img_vdeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(getActivity(), "Choose an option", "");
            }
        });

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etname.getText().toString())||
                        TextUtils.isEmpty(etemail.getText().toString()) ||
                        TextUtils.isEmpty(ettitle.getText().toString()) ||
                        TextUtils.isEmpty(etreason.getText().toString()) ||
                        TextUtils.isEmpty(etdesciption.getText().toString()))
                {
                    showErrorAlert(getResources().getString(R.string.error_complete_all_fields));
                }
               else if (!isValidEmail(etemail.getText().toString()))
               {
                    showErrorAlert("Please enter valid email id");
                }
                else
                {
                    mpresenter.postComplaint(etname.getText().toString(),
                            etemail.getText().toString(),
                            ettitle.getText().toString(),
                            etreason.getText().toString(),
                            etdesciption.getText().toString()+"/n"+"Device Details :- "+device_details
                            , selectedImageUri);
                }
            }
        });

        DeviceName.init(getActivity());



        DeviceName.with(getActivity()).request(new DeviceName.Callback() {

            @Override public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                String name = info.marketName;            // "Galaxy S8+"
                String model = info.model;                // "SM-G955W"
                String codename = info.codename;          // "dream2qltecan"
                String deviceName = info.getName();

                device_details = deviceName+" "+name+" "+model+" "+codename;

                Log.e("device", deviceName+" "+name+" "+model+" "+codename);

            }
        });


        return view;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBack() {
        //getActivity().onBackPressed();
        refreshFragment();
        clearEditText(etname, etemail, ettitle, etreason, etdesciption);
    }

    @Override
    public void startPresenter() {
        //  mpresenter = new ComplaintPresenter().start(this);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setTitle(getResources().getString(R.string.app_name));
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showErrorAlert(String msg) {
        new androidx.appcompat.app.AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setPositiveButton(R.string.alert_accept, null)
                .create()
                .show();
    }

    protected void progressBar_status() {
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
        Toast.makeText(getActivity(), "Complaint Submitted Successfully", Toast.LENGTH_SHORT).show();
    }

    public void showAlert(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // On pressing Settings button
        alertDialog.setPositiveButton("Image",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openGallery();
                        dialog.cancel();
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton("Video",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showFileChooser();

                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose File to Upload.."), PICK_FILE_REQUEST);
    }


    private void openGallery() {
        Intent profile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(profile, SELECT_FILE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            try {
                if (requestCode == SELECT_FILE) {
                    if (data != null) {
                        selectedImageUri = data.getData();
                        setProfileImage(selectedImageUri);
                    }
                }

                if (requestCode == PICK_FILE_REQUEST) {
                    if (data == null) {
                        //no data present
                        return;
                    }

                    Uri selectedFileUri = data.getData();
                    selectedFilePath = FilePath.getPath(getActivity(), selectedFileUri);
                    Log.e(TAG, "Selected File Path:" + selectedFilePath);

                    if (selectedFilePath != null && !selectedFilePath.equals("")) {

                    } else {
                        Toast.makeText(getActivity(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }


                    GlideApp
                            .with(getActivity())
                            .asBitmap()
                            .load(Uri.fromFile(new File(selectedFilePath)))
                            .into(iv_back);

                    iv_back.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                    iv_back.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        toolbar_image.setVisibility(View.GONE);
        toolbar_title.setVisibility(View.VISIBLE);
        toolbar_title.setText("Complaint");
        mToolbar.setBackgroundColor(getResources().getColor(R.color.green_balance));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setProfileImage(Uri resultUri) {
        image_path_profile = getPath(getActivity(), resultUri);

        finalFile = new File(getRealPathFromURI(resultUri));
        Log.e("FIlePath", ">>>312>>>>>>>>>" + finalFile.getPath());
        bitmap(resultUri);
        iv_back.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        iv_back.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        Glide.with(getContext()).load(resultUri).dontAnimate().into(iv_back);
    }

    private void bitmap(Uri resultUri) {
        bitmap = null;
        try {
            bitmap = (MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), resultUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKatOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKatOrAbove && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {

                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video1".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]
                        {split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);

            }
        }

        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }

        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (myProgressBar != null) {
            myProgressBar.dismiss();
            myProgressBar = null;
        }
    }

    public void refreshFragment() {
        getFragmentManager().beginTransaction()
                .detach(this)
                .attach(this)
                .commit();
    }

}