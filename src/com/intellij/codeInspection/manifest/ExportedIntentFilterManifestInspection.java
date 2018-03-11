package com.intellij.codeInspection.manifest;

import com.intellij.codeInspection.AndroidCodeScannerBundle;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

//Rule M4
public class ExportedIntentFilterManifestInspection extends LocalInspectionTool {

    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.manifest.ExportedIntentFilterManifestInspection");

    private static final String TAG_INTENT_FILTER = "intent-filter";
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
        return AndroidCodeScannerBundle.message("manifest-check.exported-intent-filter.inspection-desc");
    }

    @Nullable
    @Override
    protected URL getDescriptionUrl() {
        return super.getDescriptionUrl();
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlTag(XmlTag tag) {
                XmlTag parentTag = tag.getParentTag();
                if (isManifest(tag) && isTag(tag, TAG_INTENT_FILTER) && parentTag != null && TAG_NAMES.contains(parentTag.getName())
                    && tag.getAttribute(ATTR_EXPORTED) == null) {
                    registerWarning(tag, AndroidCodeScannerBundle.message("manifest-check.exported-intent-filter.error-msg"));
                }

                super.visitXmlTag(tag);
            }
        };
    }

}