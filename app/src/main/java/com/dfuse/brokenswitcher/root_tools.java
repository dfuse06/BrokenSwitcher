package com.dfuse.brokenswitcher;

import android.util.Log;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//thanks to DF1E for his SimpleExplorer and this code own thur

/*
 * class with various functions and what-not
 */
public class root_tools {

    /*
     * executes a command as super user in an interactive shell
     */
    public static BufferedReader execute(String cmd) {
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(
                    process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String err = (new BufferedReader(new InputStreamReader(
                    process.getErrorStream()))).readLine();
            os.flush();
            if (process.waitFor() != 0 || (!"".equals(err) && null != err && containsIllegals(err) != true) ) {
                Log.e("Root Error", err);
                return null;
            }

            return reader;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * Executes command as sh (not r00t) in an interactive shell
     */
    public static BufferedReader executeAsSH(String shcmd) {
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec("/system/bin/sh");
            DataOutputStream os = new DataOutputStream(
                    process.getOutputStream());
            os.writeBytes(shcmd + "\n");
            os.writeBytes("exit\n");
            reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            String err = (new BufferedReader(new InputStreamReader(
                    process.getErrorStream()))).readLine();
            os.flush();

            if (process.waitFor() != 0 || (!"".equals(err) && null != err)) {
                Log.e("SH error", err);
                return null;
            }

            return reader;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /*
     * works by using getprop to retrieve android prop
     */
    public static String readProp(String prop) {
        //this reads a prop as SH (not root) into a string
        Process p = null;
        String line = "";
        String propRead = "";


        try {
            p = new ProcessBuilder("/system/bin/getprop", prop).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line=br.readLine()) != null){
                propRead = line;
            }
            p.destroy();

        } catch (IOException e) {
            Log.e("Get Prop", "Failed to read prop");
        }
        return propRead;

    }

    /*
     * Checks if is integer returns true if is
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    /*
     * Checks to see if file FileToCheck exists
     */
    public static boolean fileExists(String FileToCheck){
        File f = new File(FileToCheck);
        if(f.exists()){
            return true;
        }
        else return false;
    }

    /*
     * checks for illegal characters that would throw an error in root_tools.execute
     */
    public static boolean containsIllegals(String toExamine) {
        Pattern pattern = Pattern.compile("[+]");
        Matcher matcher = pattern.matcher(toExamine);
        return matcher.find();
    }

    public static void disableOTA() {

    }


        }



