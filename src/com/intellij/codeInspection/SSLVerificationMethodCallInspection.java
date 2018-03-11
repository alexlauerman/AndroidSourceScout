package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Rule 14
public class SSLVerificationMethodCallInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.ClipboardMethodInspection");

    public static final String NAME = "setHostnameVerifier";
    public static final String CONTAINING_CLASS = "org.apache.http.conn.ssl.SSLSocketFactory";


    @Nullable
    @Override
    public String getStaticDescription() {
        //return super.getStaticDescription();
        return AndroidCodeScannerBundle.message("java14.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAME, CONTAINING_CLASS)) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java14.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }
}