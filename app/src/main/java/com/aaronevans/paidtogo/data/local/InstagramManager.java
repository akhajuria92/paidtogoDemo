package com.aaronevans.paidtogo.data.local;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

public class InstagramManager {

    private static final String PACKAGE_INSTAGRAM = "com.instagram.android";

    /**
     * Share content with Instagram
     *
     * @param context
     * @param uri
     * @param contentType
     * @return
     */
    public static boolean shareContentWithInstagram(Context context, Uri uri, ContentType contentType) {
        return shareContentWithSpecificApp(context, uri, contentType, PACKAGE_INSTAGRAM);
    }


    /**
     * Create an intent for share content
     *
     * @param uri
     * @param contentType
     * @return
     */
    private static Intent createGenericShare(Uri uri, ContentType contentType) {
        Intent share = new Intent(Intent.ACTION_SEND);
        // Set the MIME type
        share.setType(contentType.mimeType);
        // Add the URI and the caption to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);
        return share;
    }

    /**
     * Share content with an specifi app package
     *
     * @param context
     * @param uri
     * @param contentType
     * @param packageName
     * @return
     */
    private static boolean shareContentWithSpecificApp(Context context, Uri uri, ContentType contentType, String packageName) {
        try {
            // Create the new Intent using the 'Send' action.
            Intent share = createGenericShare(uri, contentType);
            share.setPackage(packageName);
            // Broadcast the Intent.
            context.startActivity(share);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Check if an app is installed
     *
     * @param context
     * @param uri
     * @return
     */
    public static boolean isAppInstalled(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /**
     * Check if Instagram is installed
     */
    public static boolean isInstagramInstalled(Context context) {
        return isAppInstalled(context, PACKAGE_INSTAGRAM);
    }

}
