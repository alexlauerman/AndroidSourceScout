package com.intellij.codeInspection;
//Based on https://github.com/JetBrains/intellij-community/blob/master/samples/comparingReferences/source/com/intellij/codeInspection/ComparingReferencesInspection.java

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

//Rule 9
public class BroadcastMethodCallInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.BroadcastMethodCallInspection");

    private static final Set<String> NAMES = new HashSet<>();
    static {
        NAMES.add("sendBroadcast");
        NAMES.add("sendBroadcastAsUser");
        NAMES.add("sendOrderedBroadcast");
        NAMES.add("sendOrderedBroadcastAsUser");
    }

    private static final String CONTAINING_CLASS = "android.content.ContextWrapper";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java9.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAMES, CONTAINING_CLASS)) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java9.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }

}