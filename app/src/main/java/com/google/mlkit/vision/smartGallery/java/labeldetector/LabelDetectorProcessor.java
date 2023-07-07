
package com.google.mlkit.vision.smartGallery.java.labeldetector;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.smartGallery.GraphicOverlay;
import com.google.mlkit.vision.smartGallery.ImageSaver;
import com.google.mlkit.vision.smartGallery.java.Database.AppDatabase;
import com.google.mlkit.vision.smartGallery.java.Database.Pics;
import com.google.mlkit.vision.smartGallery.java.Database.PictTagCrossRef;
import com.google.mlkit.vision.smartGallery.java.Database.PictTagDao;
import com.google.mlkit.vision.smartGallery.java.Database.Tags;
import com.google.mlkit.vision.smartGallery.java.VisionProcessorBase;
import com.google.mlkit.vision.smartGallery.java.savingUtility;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabelerOptionsBase;
import com.google.mlkit.vision.label.ImageLabeling;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

/** Custom InputImage Classifier Demo. */
public class LabelDetectorProcessor extends VisionProcessorBase<List<ImageLabel>> {

  private static final String TAG = "LabelDetectorProcessor";
  private ProgressDialog pd;

  private final ImageLabeler imageLabeler;
  private final Context cont;

  public LabelDetectorProcessor(Context context, ImageLabelerOptionsBase options) {
    super(context);
    cont=context;
    imageLabeler = ImageLabeling.getClient(options);
  }

  @Override
  public void stop() {
    super.stop();
    imageLabeler.close();
  }

  @Override
  protected Task<List<ImageLabel>> detectInImage(InputImage image) {
    return imageLabeler.process(image);
  }

  @Override
  protected void onSuccess(
      @NonNull List<ImageLabel> labels, @NonNull GraphicOverlay graphicOverlay) {//image label class attributes ->text,index,confidence
                                       StringBuilder name= new StringBuilder();                                         //labels list has image labels with confidence
    pd=new ProgressDialog(cont);

    AppDatabase db = Room.databaseBuilder(cont,
            AppDatabase.class, "database-name").enableMultiInstanceInvalidation().build();
    //save image with label data here

    for (ImageLabel label : labels) {
        String text;
      // label text and confidence for generating tags


      text = label.getText();
      name.append(String.format("%s_", label.getText()));

    }
    if(name.length()<3)
      name.append("default");
//saving and retrieving create new class separately
    ImageSaver saver=new ImageSaver(cont);
    saver.setFileName(String.valueOf(name));
    saver.setExternal(true);
    File fileReference=saver.save(savingUtility.current);
    String imageURL =Uri.fromFile(fileReference).toString();
    PictTagDao dao= db.pictTagDao();

    for (ImageLabel label : labels) {
      // label text and confidence for generating tags

      PictTagCrossRef pt=new PictTagCrossRef();
      Tags tag=new Tags();
      Pics pic=new Pics();
      tag.tagName=label.getText().toLowerCase();
      pic.PicId=imageURL;
      pt.PicId=pic.PicId;

pt.tagName=tag.tagName;
      ExecutorService executor = Executors.newSingleThreadExecutor();
      Handler handler = new Handler(Looper.getMainLooper());
      pd.setMessage("Loading...");
      pd.show();
      executor.execute(() -> {

        //Background work here
    //    InputStream inputStream = null;
        dao.insertData(pt);
        dao.insertData(tag);
        dao.insertData(pic);


       // InputStream finalInputStream = inputStream;
        handler.post(() -> {
          //UI Thread work here


          pd.dismiss();

        });
      });
     //java.lang.IllegalStateException: Cannot access database on the main thread since it may potentially lock the UI for a long period of time.

    }

    graphicOverlay.add(new LabelGraphic(graphicOverlay, labels));
    logExtrasForTesting(labels);
  }

  private static void logExtrasForTesting(List<ImageLabel> labels) {
    if (labels == null) {
      Log.v(MANUAL_TESTING_LOG, "No labels detected");
    } else {
      for (ImageLabel label : labels) {
        Log.v(
            MANUAL_TESTING_LOG,
            String.format("Label %s, confidence %f", label.getText(), label.getConfidence()));
      }
    }
  }

  @Override
  protected void onFailure(@NonNull Exception e) {
    Log.w(TAG, "Label detection failed." + e);
  }
}

