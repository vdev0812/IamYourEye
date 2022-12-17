package com.example.iamyoureye;

import static android.hardware.camera2.CameraMetadata.LENS_FACING_BACK;
import static com.example.iamyoureye.ColorNameUtils.getColorNameFromRgb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.VideoCapture;
import androidx.camera.core.impl.ImageAnalysisConfig;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.internal.Ref;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;

public class DetectColorFragment extends Fragment implements ImageAnalysis.Analyzer{

    Button btnPermission;
    RelativeLayout layoutPermission;
    ConstraintLayout layoutCamera;
    PreviewView cameraPreview;
    ListenableFuture<ProcessCameraProvider> provider;
    ProcessCameraProvider cameraProvider = null;
    View pointer;
    MaterialCardView containerColor;
    UserColor currentColor;
    ColorDetectHandler detectHandler;
    CardView color;
    TextView nameColor;
    ImageView btnChangeCamera;
    ImageView btnPickImage;
    ImageView viewImage;
    Guideline left;
    Guideline right;
    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;
    int cameraDefault = CameraSelector.LENS_FACING_BACK;
    long timeOld = 0l;
    long timeOldCam = 0l;
    boolean camera = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detect_color, container, false);
        initWidgets(view);
        provider = ProcessCameraProvider.getInstance(requireActivity());
        actionPermission();
        changeCamera();
        btnPickImage.setOnClickListener(view1 -> {
            Intent gallery = new Intent(Intent.ACTION_PICK);
            gallery.setType("image/*");
            startActivityForResult(gallery, 102);
            btnPickImage.setEnabled(false);
        });
        return view;
    }

    private void changeCamera() {
        btnChangeCamera.setOnClickListener(view -> {
            long timeCurent2 = System.currentTimeMillis();
            if(timeCurent2 - timeOldCam >= TimeUnit.SECONDS.toMillis(2)) {
                if(cameraDefault == 1) {
                    cameraDefault = CameraSelector.LENS_FACING_FRONT;
                } else
                    cameraDefault = CameraSelector.LENS_FACING_BACK;
                try {
                    cameraProvider = provider.get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                timeOldCam = timeCurent2;
                startCamera(cameraProvider);
            }
        });
    }

    private void actionPermission() {
        checkPermisson();
        btnPermission.setOnClickListener(view1 -> {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if(getFromPerf(requireActivity(), "ALLOW_KEY")) {
                        showSetting();
                    } else if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[] {Manifest.permission.CAMERA}, 101);
                    }
                } else {
                changeLayout();
                }
            } else {
                changeLayout();
            }
        });
    }



    private void changeLayout() {
        layoutPermission.setVisibility(View.GONE);
        layoutCamera.setVisibility(View.VISIBLE);
        try {
            cameraProvider = provider.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        startCamera(cameraProvider);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(camera) {
            try {
                cameraProvider = provider.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            startCamera(cameraProvider);
        }
    }

    @SuppressLint("RestrictedApi")
    private void startCamera(ProcessCameraProvider cameraProvider) {
        provider.addListener(() -> {
            if (cameraProvider != null) {
                cameraProvider.unbindAll();
                Preview preview = new Preview.Builder().build();
                @SuppressLint("WrongConstant") CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(cameraDefault)
                        .build();
                preview.setSurfaceProvider(cameraPreview.createSurfaceProvider());

                // Image capture use case
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                // Image analysis use case
                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();
                imageAnalysis.setAnalyzer(cameraExecutor, this);
                cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, imageCapture, imageAnalysis);
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }
    @Override
    public void analyze(@NonNull ImageProxy image) {
        // image processing here for the current frame
        Log.d("TAG", "analyze: got the frame at: " + image.getImageInfo().getTimestamp());
        long timeCurent = System.currentTimeMillis();
        detectHandler = new ColorDetectHandler();
        if(timeCurent - timeOld >= TimeUnit.MILLISECONDS.toMillis(800)) {
            currentColor = detectHandler.detect(cameraPreview, pointer);
            color.setCardBackgroundColor(Color.parseColor(currentColor.hex));
            nameColor.setText(getColorNameFromRgb(Integer.parseInt(currentColor.red), Integer.parseInt(currentColor.green), Integer.parseInt(currentColor.blue)));
            timeOld = timeCurent;
        }
        image.close();
    }

    private void initWidgets(View view) {
        cameraExecutor = Executors.newSingleThreadExecutor();
        btnPermission = view.findViewById(R.id.btn_permission);
        layoutCamera = view.findViewById(R.id.layout_camera);
        layoutPermission = view.findViewById(R.id.layout_permission);
        cameraPreview = view.findViewById(R.id.camera_preview);
        pointer = view.findViewById(R.id.pointer);
        color = view.findViewById(R.id.card_color);
        nameColor = view.findViewById(R.id.txt_hex);
        btnChangeCamera = view.findViewById(R.id.btn_change_camera);
        btnPickImage = view.findViewById(R.id.btn_pick_image);
        viewImage = view.findViewById(R.id.image_view);
        left = view.findViewById(R.id.guideline_left);
        right = view.findViewById(R.id.guideline_right);
        containerColor = view.findViewById(R.id.card_color_preview);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            cameraProvider = provider.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cameraProvider.unbindAll();
    }

    private void checkPermisson() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, 101);
        } else {
            changeLayout();
        }
    }

    private void showSetting() {
        Intent accessSetting = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        accessSetting.setData(uri);
        startActivityForResult(accessSetting, 103);
    }

    private static Boolean getFromPerf(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("ALLOW_KEY", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    private static void saveToPreferences(Context context, String key, Boolean allowed) {
        SharedPreferences preferences = context.getSharedPreferences("ALLOW_KEY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("ALLOW_KEY", allowed);
        editor.commit();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    changeLayout();
                    break;
                }

                for(int i = 0, len = permissions.length; i < len; i++) {
                    String permisson = permissions[i];
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRotinable = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permisson);
                        if(!showRotinable) {
                            saveToPreferences(getActivity(), "ALLOW_KEY", true);
                        }
                    }
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    Bitmap img;
    float dX, dY, cX, cY;
    int lastAction;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 102 && data != null && resultCode == -1) {
            camera = false;
            btnPickImage.setEnabled(true);
            btnChangeCamera.setEnabled(false);
            Uri uri = data.getData();
            viewImage.setImageURI(uri);
            viewImage.setDrawingCacheEnabled(true);
            viewImage.buildDrawingCache(true);

//            int pixel1 = img.getPixel((int) pointer.getX(), (int) pointer.getY());
//            //getting RGB values
//            int r1 = Color.red(pixel1);
//            int g1 = Color.green(pixel1);
//            int b1 = Color.blue(pixel1);
//
//            String hex1 = "#" + Integer.toHexString(pixel1);
//            color.setBackgroundColor(Color.parseColor(hex1));
//            nameColor.setText(getColorNameFromRgb(r1, g1, b1));
            viewImage.setOnTouchListener((view, event) -> {
                img = viewImage.getDrawingCache();
                int pixel = img.getPixel((int) event.getX(), (int) event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dX = pointer.getX() - event.getRawX();
                        dY = pointer.getY() - event.getRawY();
                        cX = containerColor.getX() - event.getRawX();
                        cY = containerColor.getY() - event.getRawY();
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        pointer.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        containerColor.animate()
                                .x(event.getRawX() + cX)
                                .y(event.getRawY() + cY)
                                .setDuration(0)
                                .start();
                        lastAction = MotionEvent.ACTION_MOVE;

                        //getting RGB values
                        int r = Color.red(pixel);
                        int g = Color.green(pixel);
                        int b = Color.blue(pixel);

                        String hex = "#" + Integer.toHexString(pixel);
                        color.setBackgroundColor(Color.parseColor(hex));
                        nameColor.setText(getColorNameFromRgb(r, g, b));
                        break;
                    default:
                        return false;
                }
                return true;
            });
        }
    }

}