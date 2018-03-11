package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Rule 12
public class GeolocationMethodCallInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.GeolocationMethodInspection");

    public static final String NAME = "setWebChromeClient";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java12.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.hasName(expression, NAME)
                        && PsiJavaUtil.getContainingClassName(expression) != null) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java12.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }

}