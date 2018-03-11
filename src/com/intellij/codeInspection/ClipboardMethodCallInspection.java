package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

//Rule 10
public class ClipboardMethodCallInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.ClipboardMethodInspection");

    private static final Set<String> NAMES = new HashSet<>();
    public static final String CONTAINING_CLASS = "android.content.ClipboardManager";

    static {
        NAMES.add("setPrimaryClip");
        NAMES.add("setText");
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java10.inspection-desc");
    }


    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAMES, CONTAINING_CLASS)) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java10.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }
}