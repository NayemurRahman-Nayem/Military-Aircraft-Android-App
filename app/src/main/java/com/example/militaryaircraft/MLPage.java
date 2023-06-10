package com.example.militaryaircraft;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.militaryaircraft.ml.ModelUnquant;
import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;



public class MLPage extends AppCompatActivity {

    Button selectBtn, captureBtn;
    ImageView imageView;
    TextView result;
    Bitmap image;
    int imageSize = 224 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mlpage);
        getPermission();
        selectBtn = findViewById(R.id.selectBtn) ;
        captureBtn = findViewById(R.id.captureBtn) ;
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });
    }

    void getPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MLPage.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==11){
            if(grantResults.length>=0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void classifyImage(Bitmap image ) {
        try {
            ModelUnquant model = ModelUnquant.newInstance(MLPage.this);
            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3 ) ;
            byteBuffer.order(ByteOrder.nativeOrder()) ;

            int [] intValues = new int[imageSize*imageSize] ;
            image.getPixels(intValues,0,image.getWidth(),0,0,image.getWidth(),image.getHeight());
            int pixel = 0  ;
            for(int i=0 ;i<imageSize ; i++){
                for(int j=0;j<imageSize;j++) {
                    int val = intValues[pixel++] ;
                    byteBuffer.putFloat(((val>>16) & 0xFF) * (1.f/255.f)) ;
                    byteBuffer.putFloat(((val>>8) & 0xFF) * (1.f/255.f)) ;
                    byteBuffer.putFloat((val & 0xFF)  * (1.f / 255.f)) ;
                }
            }
            inputFeature0.loadBuffer(byteBuffer);
            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
            int maxPos = 0 ;                                                             // to find the index of the data where probability is maximum ;
            float maxConfidence = 0 ;
            float[] confidences = outputFeature0.getFloatArray() ;
            for(int i=0;i<confidences.length;i++){
                if(confidences[i]>maxConfidence) {
                    maxConfidence = confidences[i]  ;
                    maxPos = i ;
                }
            }
            String[] classes = {"AG600" , "E2" , "F16"} ;
            result.setText(classes[maxPos]);                                      // set the text of the value of the index maxPos
            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==10){
            if(data!=null){
                Uri uri = data.getData();
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    int dimention = Math.min(image.getWidth(),image.getHeight()) ;
                    image = ThumbnailUtils.extractThumbnail(image,dimention,dimention) ;
                    imageView.setImageBitmap(image);
                    image = Bitmap.createScaledBitmap(image , imageSize , imageSize , false  ) ;
                    classifyImage(image) ;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==12){
            image = (Bitmap) data.getExtras().get("data");
            int dimention = Math.min(image.getWidth(),image.getHeight()) ;
            image = ThumbnailUtils.extractThumbnail(image,dimention,dimention) ;
            imageView.setImageBitmap(image);
            image = Bitmap.createScaledBitmap(image , imageSize , imageSize , false  ) ;
            classifyImage(image) ;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}