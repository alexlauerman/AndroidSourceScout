package com.intellij.codeInspection;

import com.intellij.CommonBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;


public class AndroidCodeScannerBundle {

    private static Reference<ResourceBundle> ourBundle;
    private static final String BUNDLE = "com.intellij.codeInspection.AndroidCodeScanner";

    public static String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, @NotNull Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }


    private AndroidCodeScannerBundle() {
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = com.intellij.reference.SoftReference.dereference(ourBundle);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(BUNDLE);
            ourBundle = new SoftReference<>(bundle);
        }
        return bundle;
    }
}
