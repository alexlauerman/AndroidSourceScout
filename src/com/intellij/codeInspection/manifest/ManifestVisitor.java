package com.intellij.codeInspection.manifest;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.XmlElementVisitor;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlDocument;
import com.intellij.psi.xml.XmlElement;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;
import org.jetbrains.annotations.NotNull;


abstract class ManifestVisitor extends XmlElementVisitor {

    private static final String FILE_ANDROID_MANIFEST = "androidmanifest.xml";

    private static final String TAG_MANIFEST = "manifest";

    protected static final String TAG_APPLICATION = "application";
    protected static final String TAG_PROVIDER = "provider";
    protected static final String TAG_USES_SDK = "uses-sdk";
    protected static final String ATTR_TARGET_SDK_VERSION = "android:targetSdkVersion";
    protected static final String ATTR_EXPORTED = "android:exported";

    protected static final String VALUE_TRUE = "true";
    protected static final String VALUE_FALSE = "false";

    private ProblemsHolder holder;

    protected ManifestVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    protected XmlTag getManifestTag(XmlTag tag) {
        PsiFile containingFile = tag.getContainingFile();
        if (containingFile == null) {
            return null;
        }
        XmlFile file = (XmlFile) containingFile.getOriginalFile();

        XmlDocument document = file.getDocument();
        if (document != null) {
            XmlTag root = document.getRootTag();
            if (root != null && TAG_MANIFEST.equals(root.getName())) {
                return root;
            }
        }
        return null;
    }

    protected boolean isManifest(XmlElement element) {
        PsiFile containingFile = element.getContainingFile();
        if (containingFile == null) {
            return false;
        }
        XmlFile file = (XmlFile) containingFile.getOriginalFile();
        return FILE_ANDROID_MANIFEST.equals(file.getName().toLowerCase());
    }

    protected boolean isTag(XmlTag tag, @NotNull String name) {
        return tag != null && name.equals(tag.getName());
    }

    protected boolean isAttribute(XmlAttribute attribute, @NotNull String name) {
        return attribute != null && name.equals(attribute.getName());
    }

    protected void registerProblem(PsiElement element, String msg) {
        holder.registerProblem(element, msg, ProblemHighlightType.GENERIC_ERROR);
    }

    protected void registerWarning(PsiElement element, String msg) {
        holder.registerProblem(element, msg, ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
    }
}
