package com.intellij.codeInspection;


import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiBinaryExpression;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class SQLParametersInjectionInspection extends BaseJavaLocalInspectionTool {

    private static final String NAME = "rawQuery";
    private static final String CONTAINING_CLASS = "android.database.sqlite.SQLiteDatabase";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java7.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAME, CONTAINING_CLASS)) {
                    PsiExpression[] args = expression.getArgumentList().getExpressions();
                    if (args.length > 0 && isConcatenatedString(args[0])) {
                        holder.registerProblem(expression, AndroidCodeScannerBundle.message("java7.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                    }
                }
            }

            private boolean isConcatenatedString(PsiExpression expression) {
                if (expression instanceof PsiBinaryExpression) {
                    PsiBinaryExpression binary = (PsiBinaryExpression) expression;
                    return JavaTokenType.PLUS.equals(binary.getOperationTokenType());
                }
                return false;
            }
        };
    }

}
