package br.org.fundatec.instavelhoapp;

import android.support.v4.app.ActivityCompat;

import permissions.dispatcher.PermissionUtils;

/**
 * Created by tecnico on 21/11/2017.
 */

class FotosActivityPermissionsDispatcher {
    private static final int REQUEST_DISPATCHTAKEPICTUREINTENT = 0;

    private static final String[] PERMISSION_DISPATCHTAKEPICTUREINTENT = new String[] {"android.permission.CAMERA"};

    private FotosActivityPermissionsDispatcher() {
    }

    static void dispatchTakePictureIntentWithCheck(FotosActivity target) {
        if (PermissionUtils.hasSelfPermissions(target, PERMISSION_DISPATCHTAKEPICTUREINTENT)) {
            target.dispatchTakePictureIntent();
        } else {
            ActivityCompat.requestPermissions(target, PERMISSION_DISPATCHTAKEPICTUREINTENT, REQUEST_DISPATCHTAKEPICTUREINTENT);
        }
    }

    static void onRequestPermissionsResult(FotosActivity target, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_DISPATCHTAKEPICTUREINTENT:
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.dispatchTakePictureIntent();
                }
                break;
            default:
                break;
        }
    }
}
