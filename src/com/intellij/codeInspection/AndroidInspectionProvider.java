package com.intellij.codeInspection;


import com.intellij.codeInspection.manifest.BackupsManifestInspection;
import com.intellij.codeInspection.manifest.ExportedManifestInspection;
import com.intellij.codeInspection.manifest.ExportedIntentFilterManifestInspection;
import com.intellij.codeInspection.manifest.MaxSdkVersionManifestInspection;
import com.intellij.codeInspection.manifest.TargetSdkVersionManifestInspection;

public class AndroidInspectionProvider implements InspectionToolProvider {

    public Class[] getInspectionClasses() {
        return new Class[]{
                BroadcastMethodCallInspection.class,
                ClipboardMethodCallInspection.class,
                ExternalAccessMethodCallInspection.class,
                GeolocationMethodCallInspection.class,
                BackupsManifestInspection.class,
                ExportedManifestInspection.class,
                ExportedIntentFilterManifestInspection.class,
                MaxSdkVersionManifestInspection.class,
                TargetSdkVersionManifestInspection.class,
                SSLVerificationMethodCallInspection.class,
                WebViewJavaScriptInspection.class,
                WorldWriteableInspection.class,
                XXEJAXPInspection.class,
                XXESAXInspection.class,
        };
    }

}
