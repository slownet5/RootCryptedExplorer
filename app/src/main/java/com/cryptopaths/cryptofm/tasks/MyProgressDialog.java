package com.cryptopaths.cryptofm.tasks;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cryptopaths.cryptofm.R;
import com.cryptopaths.cryptofm.filemanager.utils.SharedData;
import com.cryptopaths.cryptofm.services.CleanupService;

/**
 * Created by tripleheader on 1/8/17.
 * custom progress viewer for all the tasks
 */

public class MyProgressDialog {
    private Dialog                      dialog;
    private TextView                    mProgressTextView;
    private Context                     mContext;
    private String                      mContentTitle;
    private String                      mCurrentFilename;
    private Boolean                     isInNotificationMode;
    private NotificationManager         mNotificationManager;
    private NotificationCompat.Builder  mNotBuilder;
    private static int                  NOTIFICATION_NUMBER=0;
    private int                         thisNotificationNumber=0;
    private ProgressBar                 mProgressBar;

    MyProgressDialog(Context context, String title, final AsyncTask task){
        dialog=new Dialog(context);
        Log.d("dialog", "MyProgressDialog: not cancelable");
        dialog.setCanceledOnTouchOutside(false);
        this.mContext=context;
        this.mContentTitle=title;
        this.isInNotificationMode=false;
        dialog.setContentView(R.layout.task_progress_layout);
        ((TextView)dialog.findViewById(R.id.progress_dialog_title)).setText(title);
        mProgressTextView=((TextView)dialog.findViewById(R.id.filename_progress_textview));
        mProgressBar= (ProgressBar) dialog.findViewById(R.id.dialog_progressbar);
        mProgressBar.setMax(100);
        dialog.findViewById(R.id.runin_background_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                buildNotification();
            }
        });
        dialog.findViewById(R.id.cancel_background_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("cancel","Canceling the task");
                SharedData.IS_TASK_CANCELED=true;
                task.cancel(true);
                dialog.dismiss();

            }
        });
    }

    private void buildNotification() {
        thisNotificationNumber=NOTIFICATION_NUMBER++;
        isInNotificationMode=true;
        mNotificationManager=(NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotBuilder=new NotificationCompat.Builder(mContext);
        mNotBuilder.setContentTitle(mContentTitle);
        mNotBuilder.setContentText(mCurrentFilename);
        mNotBuilder.setSmallIcon(R.drawable.logo);
        Log.d("notification","yoo nigga showing notification");
        mNotBuilder.setProgress(0,0,true);
        mNotBuilder.setOngoing(true);
        mNotificationManager.notify(thisNotificationNumber,mNotBuilder.build());
        CleanupService.NOTIFICATION_BUILDER=mNotBuilder;
        CleanupService.NOTIFICATION_MANAGER=mNotificationManager;
    }

    public void setmProgressTextViewText(String text) {
        mCurrentFilename=text;
        if(isInNotificationMode){
             mNotBuilder.setContentText(text);
            mNotificationManager.notify(thisNotificationNumber,mNotBuilder.build());
        }else{
            this.mProgressTextView.setText(text);
        }
    }
    void dismiss(String text){
        if(isInNotificationMode){
            mNotBuilder.setContentText(text);
            mNotBuilder.setOngoing(false);
            mNotBuilder.setProgress(100,100,false);
            mNotificationManager.notify(thisNotificationNumber,mNotBuilder.build());
            this.mNotBuilder=null;
        }else{
            dialog.dismiss();
        }
    }
    void show(){
        dialog.show();
    }

    public void setProgress(Integer integer) {
        mProgressBar.setProgress(integer);
    }

    public void setMessage(String s) {
        this.mProgressTextView.setText(s);
    }

    public void setIndeterminate(boolean b) {
        this.mProgressBar.setIndeterminate(b);
    }
}
