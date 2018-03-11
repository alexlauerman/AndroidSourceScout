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

//Rule M5
public class BackupsManifestInspection extends LocalInspectionTool {

    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.BackupsManifestInspection");

    private static final String ATTR_ALLOW_BACKUP = "android:allowBackup";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("manifest-check.backups.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                XmlTag tag = attribute.getParent();
                if (isManifest(attribute) && isAttribute(attribute, ATTR_ALLOW_BACKUP) && isTag(tag, TAG_APPLICATION)) {
                    if (!VALUE_FALSE.equals(attribute.getValue())) {
                        registerWarning(attribute, AndroidCodeScannerBundle.message("manifest-check.backups.error-msg"));
                    }
                }
                super.visitXmlAttribute(attribute);
            }
        };
    }
}