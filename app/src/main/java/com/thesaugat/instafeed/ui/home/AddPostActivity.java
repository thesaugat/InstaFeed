package com.thesaugat.instafeed.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iceteck.silicompressorr.SiliCompressor;
import com.thesaugat.instafeed.R;
import com.thesaugat.instafeed.pojo.ServerResponse;
import com.thesaugat.instafeed.utils.APIClient;
import com.thesaugat.instafeed.utils.Constants;
import com.thesaugat.instafeed.utils.SharedPreferencesUtils;
import com.thesaugat.instafeed.utils.UriHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostActivity extends AppCompatActivity {
    private static final int PICK_FILE = 0;
    private static final int TAKE_PICTURE = 1;
    private static final String TAG = "Permissions Request: ";
    private static final int REQ_CODE = 324;
    private String cacheDir ;
    ImageView postIV;
    EditText etDesc;
    TextView postTV;
    ProgressBar loadingProgress;
    File finalFile = null;
    Uri selectedUri = null;

    Uri imageUri;
    String iconsStoragePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        postIV = findViewById(R.id.postIV);
        

        etDesc = findViewById(R.id.etDesc);
        postTV = findViewById(R.id.postTV);
        loadingProgress = findViewById(R.id.loadingProgress);
        iconsStoragePath = this.getExternalFilesDir(null).getAbsolutePath() + "/Pictures/instafeed";
        cacheDir = this.getExternalFilesDir(null).getAbsolutePath() + "/Pictures/instafeed/cache";

        postIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        postTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUri == null)
                    Toast.makeText(AddPostActivity.this, "Please Select or Take a photo", Toast.LENGTH_SHORT).show();
                else {
                    if (finalFile == null) {
                        String path = UriHelper.getPath(AddPostActivity.this, selectedUri);
                        uploadFile(new File(path), MediaType.parse(getContentResolver().getType(selectedUri)));
                    } else
                        uploadFile(finalFile, MediaType.parse(getContentResolver().getType(selectedUri)));

                }

            }
        });
    }

    private void openDialog() {
        ImageView camera, files;
        AlertDialog.Builder builder = new AlertDialog.Builder((this));
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_layout_dialog_choose_photo, null);
        camera = view.findViewById(R.id.cameraIV);
        files = view.findViewById(R.id.pictureIV);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        builder.setCancelable(true);
// Add action buttons
        builder.create();
        AlertDialog a = builder.show();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions())
                    openCamera();
                a.dismiss();


            }
        });

        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions())
                    openFiles();
                a.dismiss();

            }
        });

    }

    private void openFiles() {
        Intent chooseFile = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, PICK_FILE);
    }

    private void openCamera() {
        finalFile = getOutputMediaFile();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageName = finalFile.getName();


        imageUri = FileProvider.getUriForFile(
                this,
                this.getApplicationContext()
                        .getPackageName() + ".provider", finalFile);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                imageUri);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    public File getOutputMediaFile() {


        File mediaStorageDir = new File(iconsStoragePath);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(iconsStoragePath + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    setImageToImageView(selectedImage);
                    selectedUri = selectedImage;
                }
            case PICK_FILE:
                if (requestCode == PICK_FILE)
                    if (resultCode == Activity.RESULT_OK) {
                        Uri uri = data.getData();
                        setImageToImageView(uri);
                        selectedUri = uri;
                    }
        }

    }

    Boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission is granted");
                return true;
            } else {

                Log.d(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQ_CODE);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d(TAG, "Permission is granted");
            return true;
        }

    }

    void setImageToImageView(Uri uri) {
        postIV.setImageURI(uri);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();

            }

        }
    }


    void uploadFile(File file, MediaType mimType) {
        toggleProgress(true);
        String desc = "";
        desc = etDesc.getText().toString();
//        File file = Compressor.getDefault(this).compressToFile(orgFile);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), desc);
        RequestBody reqFile = RequestBody.create(mimType, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        String token = null;
        token = SharedPreferencesUtils.getStringPreference(this, Constants.TOKEN_KEY);
        if (token != null) {
            Call<ServerResponse> serverResponseCall = APIClient.getClient().uploadAPost(token, description, body);
            serverResponseCall.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    toggleProgress(false);
                    if (response.isSuccessful()) {
                        finish();
                        if (!response.body().getError()) {
                            Toast.makeText(AddPostActivity.this, "Post Successfully uploaded", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddPostActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    toggleProgress(false);
                    Toast.makeText(AddPostActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    File getCompressedFile(File file) {

        File destinationDirectory = new File(cacheDir);
        if (!destinationDirectory.exists()) {
            if (!destinationDirectory.mkdirs()) {
                return null;
            }
        }
        return new File(SiliCompressor.with(this).compress(file.toString(), destinationDirectory));

    }


    void toggleProgress(Boolean toggle) {
        if (toggle)
            loadingProgress.setVisibility(View.VISIBLE);
        else
            loadingProgress.setVisibility(View.GONE);
    }

}