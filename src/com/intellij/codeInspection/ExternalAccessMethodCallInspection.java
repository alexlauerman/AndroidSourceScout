package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


// Rule 11
public class ExternalAccessMethodCallInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.ExternalAccessMethodCallInspection");

    private static final Set<String> NAMES = new HashSet<>();
    static {
        NAMES.add("getExternalCacheDir");
        NAMES.add("getExternalCacheDirs");
        NAMES.add("getExternalFilesDir");
        NAMES.add("getExternalFilesDirs");
        NAMES.add("getExternalMediaDirs");
        NAMES.add("getExternalStorageDirectory");
        NAMES.add("getExternalStoragePublicDirectory");
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java11.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.hasName(expression, NAMES) && PsiJavaUtil.getContainingClassName(expression) != null) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java11.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }

}