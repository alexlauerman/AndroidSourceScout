package com.intellij.codeInspection.manifest;


import com.intellij.codeInspection.AndroidCodeScannerBundle;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


//Rule M1
public class ProviderImplicitlyExportedManifestInspection extends LocalInspectionTool {

    private static final int AUTO_EXPORTED_MAX_VERSION = 16;

    private static final Set<String> EXPORTED_ATTR_VALUES = new HashSet<>();
    static {
        EXPORTED_ATTR_VALUES.add(ManifestVisitor.VALUE_TRUE);
        EXPORTED_ATTR_VALUES.add(ManifestVisitor.VALUE_FALSE);
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("manifest.provider-implicitly-exported.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new ManifestVisitor(holder) {

            @Override
            public void visitXmlAttribute(XmlAttribute attribute) {
                if (isManifest(attribute) && isTag(attribute.getParent(), TAG_USES_SDK) && isAttribute(attribute, ATTR_TARGET_SDK_VERSION)) {
                    if (isAutoExportedTargetSdkVersion(attribute)) {
                        XmlTag manifest = getManifestTag(attribute.getParent());
                        if (hasProviderWithoutExportedAttribute(manifest)) {
                            final String msg = AndroidCodeScannerBundle.message("manifest.provider-implicitly-exported.error-msg", AUTO_EXPORTED_MAX_VERSION);
                            registerWarning(attribute, msg);
                        }
                    }
                }
            }

            private boolean isAutoExportedTargetSdkVersion(XmlAttribute attribute) {
                String text = attribute.getValue();
                if (text == null) {
                    return false;
                }

                try {
                    int value = Integer.parseInt(text);
                    return value <= AUTO_EXPORTED_MAX_VERSION;
                } catch (NumberFormatException e) {
                    return false;
                }
            }

            private boolean hasProviderWithoutExportedAttribute(XmlTag manifestTag) {
                if (manifestTag != null) {
                    XmlTag application = manifestTag.findFirstSubTag(TAG_APPLICATION);
                    if (application != null) {
                        for (XmlTag provider: application.findSubTags(TAG_PROVIDER)) {
                            String value = provider.getAttributeValue(ATTR_EXPORTED);
                            if (value == null || !EXPORTED_ATTR_VALUES.contains(value)) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        };
    }

}
