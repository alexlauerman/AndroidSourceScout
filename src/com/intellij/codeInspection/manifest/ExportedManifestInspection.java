package com.intellij.codeInspection.manifest;

import com.intellij.codeInspection.AndroidCodeScannerBundle;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


//Rule M3
public class ExportedManifestInspection extends LocalInspectionTool {

    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.manifest.ExportedManifestInspection");

    private static final Set<String> TAG_NAMES = new HashSet<>();

    static {
        TAG_NAMES.add("activity");
        TAG_NAMES.add("provider");
        TAG_NAMES.add("service");
        TAG_NAMES.add("receiver");
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("manifest-check.exported.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                XmlTag tag = attribute.getParent();
                if (isManifest(attribute) && isAttribute(attribute, ATTR_EXPORTED) && tag != null && TAG_NAMES.contains(tag.getName())) {
                    if (VALUE_TRUE.equals(attribute.getValue())) {
                        registerWarning(attribute, AndroidCodeScannerBundle.message("manifest-check.exported.error-msg"));
                    }
                }
                super.visitXmlAttribute(attribute);
            }
        };
    }
}