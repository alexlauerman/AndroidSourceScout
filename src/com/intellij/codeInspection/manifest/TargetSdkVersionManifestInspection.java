package com.intellij.codeInspection.manifest;

import com.intellij.codeInspection.AndroidCodeScannerBundle;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


//Rule M2
public class TargetSdkVersionManifestInspection extends LocalInspectionTool {

    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.manifest.TargetSdkVersionManifestInspection");

    private static final int RECENT_ANDROID_VERSION = 23;

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("manifest.target-sdk-version.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {

        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                if (isManifest(attribute) && isTag(attribute.getParent(), TAG_USES_SDK) && isAttribute(attribute, ATTR_TARGET_SDK_VERSION)) {
                    if (!isRecentTargetSdkVersion(attribute)) {
                        registerWarning(attribute, AndroidCodeScannerBundle.message("manifest.target-sdk-version.error-msg"));
                    }
                }
            }

            private boolean isRecentTargetSdkVersion(XmlAttribute attribute) {
                String text = attribute.getValue();
                if (text == null) {
                    registerProblem(attribute, AndroidCodeScannerBundle.message("missing-attr-value"));
                    return false;
                }

                try {
                    int value = Integer.parseInt(text);
                    return value == RECENT_ANDROID_VERSION;
                } catch (NumberFormatException e) {
                    registerProblem(attribute, AndroidCodeScannerBundle.message("attr-value-not-integer"));
                    return false;
                }
            }
        };
    }

}