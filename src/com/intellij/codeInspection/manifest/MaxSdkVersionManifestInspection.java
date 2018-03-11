package com.intellij.codeInspection.manifest;

import com.intellij.codeInspection.AndroidCodeScannerBundle;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


//Rule M7
public class MaxSdkVersionManifestInspection extends LocalInspectionTool {

    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.manifest.MaxSdkVersionManifestInspection");

    private static final String TAG_MAX_SDK_VERSION = "android:maxSdkVersion";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("manifest-check.max-sdk-version.inspection-desc"); // todo
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                XmlTag tag = attribute.getParent();
                if (isManifest(attribute) && isAttribute(attribute, TAG_MAX_SDK_VERSION) && isTag(tag, TAG_USES_SDK)) {
                    registerWarning(attribute, AndroidCodeScannerBundle.message("manifest-check.max-sdk-version.error-msg"));
                }
                super.visitXmlAttribute(attribute);
            }
        };
    }

}