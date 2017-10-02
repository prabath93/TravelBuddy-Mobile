package com.example.prabaths.ImageHandling;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.prabaths.TravelBuddy.MainActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by prabath s on 5/28/2016.
 */
public class CopyImage {

    /**
     * This method returns the path of a file
     * @param uri uri of the file
     * @return path of the file
     */
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = MainActivity.contentResolver.query(uri, projection, null, null, null);
        int column_index ;
        try {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        catch (NullPointerException e){
            return uri.toString();
        }
        cursor.moveToFirst();
        try {
            return cursor.getString(column_index);
        }
        catch (CursorIndexOutOfBoundsException e){
            return "";
        }
    }

    /**
     * This method copy a file in to a folder and return the uri of the folder
     * @param uri uri of the file
     * @return new uri of the file
     * @throws IOException
     */
    public Uri copyFile(Uri uri) throws IOException {
        String selectedImagePath=getPath(uri);
        File folder = new File(Environment.getExternalStorageDirectory() + "/TravelBuddy/Images");

        if(selectedImagePath.contains(Environment.getExternalStorageDirectory()+"/TravelBuddy/Images/")){

            return uri;
        }

        int fileNum=1;

        for(File file:folder.listFiles()){
            if(file.getName().equals(fileNum+".jpg")){
                fileNum=fileNum+1;
            }
            else{break;}
            System.out.println("file names:"+file.getName());
        }

        String outPutPath=Environment.getExternalStorageDirectory()+"/TravelBuddy/Images/"+String.valueOf(fileNum)+".jpg";
        InputStream in = new FileInputStream(selectedImagePath);
        OutputStream out = new FileOutputStream(outPutPath);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
        Uri outputUri=Uri.parse(new File(outPutPath).toString());
        return outputUri;

    }

}
