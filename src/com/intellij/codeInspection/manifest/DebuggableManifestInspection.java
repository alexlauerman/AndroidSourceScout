package com.intellij.codeInspection.manifest;


import com.intellij.codeInspection.AndroidCodeScannerBundle;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


// M8
public class DebuggableManifestInspection extends LocalInspectionTool {

    private static final String ATTR_DEBUGGABLE = "android:debuggable";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("manifest-check.debuggable.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                if (isManifest(attribute) && isAttribute(attribute, ATTR_DEBUGGABLE) && isTag(attribute.getParent(), TAG_APPLICATION)) {
                    if (VALUE_TRUE.equals(attribute.getValue())) {
                        registerWarning(attribute, AndroidCodeScannerBundle.message("manifest-check.debuggable.error-msg"));
                    }
                }
                super.visitXmlAttribute(attribute);
            }
        };
    }

}
