package com.dfuse.brokenswitcher;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by dfuse on 4/4/16.
 */
public class RestoreFrag extends Fragment {


    String tagname = "Restore";
    String dir = Environment.getExternalStorageDirectory() + "/BrokenSwitcher";

    Button switcher;
    View.OnClickListener dOnClickListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.restore, null);
        switcher = (Button) view.findViewById(R.id.button3);


        switcher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startInstall();

            }

        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Reboot to apply changes", Snackbar.LENGTH_LONG)
                        .setAction("Push me", dOnClickListener)
                        .setActionTextColor(Color.RED)
                        .show();

            }
        });

        dOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runtime runtime = Runtime.getRuntime();
                Process proc = null;
                OutputStreamWriter osw = null;
                StringBuilder sbstdOut = new StringBuilder();
                StringBuilder sbstdErr = new StringBuilder();

                String command = "/system/bin/reboot";

                try { // Run Script

                    proc = runtime.exec("su");
                    osw = new OutputStreamWriter(proc.getOutputStream());
                    osw.write(command);
                    osw.flush();
                    osw.close();

                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (osw != null) {
                        try {
                            osw.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    if (proc != null)
                        proc.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sbstdOut.append(ReadBufferedReader(new InputStreamReader(proc
                        .getInputStream())));
                sbstdErr.append(ReadBufferedReader(new InputStreamReader(proc
                        .getErrorStream())));
                if (proc.exitValue() != 0) {
                }

            }

            private Object ReadBufferedReader(
                    InputStreamReader inputStreamReader) {
                // TODO Auto-generated method stub
                return null;
            }
        };


        return view;
    }


    void install() {
        String cmd_recovery = "busybox dd if=" + dir + "/recovery.img" + "  of=/dev/block/platform/msm_sdcc.1/by-name/recovery";

        String cmd_boot = "busybox dd if=" + dir + "/boot.img" + " of=/dev/block/platform/msm_sdcc.1/by-name/boot";

        root_tools.execute(cmd_recovery);
        root_tools.execute(cmd_boot);

        Log.i(tagname, "Restore");
    }

    private void startInstall() {

        final ProgressDialog RingProgressDialog = new ProgressDialog(getActivity());
        RingProgressDialog.setTitle("Please Wait");
        RingProgressDialog.setMessage("Installing");
        RingProgressDialog.setCancelable(false);
        RingProgressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //this is the runnable stuff for the progress bar
                    install();
                } catch (Exception e) {
                    Log.e("Installer", "something went wrong");

                }
                RingProgressDialog.dismiss();

            }
        }).start();

    }

}
