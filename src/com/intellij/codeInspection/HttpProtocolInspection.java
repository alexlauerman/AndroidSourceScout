package com.intellij.codeInspection;


import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLiteralExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class HttpProtocolInspection extends BaseJavaLocalInspectionTool {

    private static final String HTTP_PREFIX = "http:";
    private static final String WS_PREFIX = "ws:";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java-t1.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitLiteralExpression(PsiLiteralExpression expression) {
                super.visitLiteralExpression(expression);
                Object value = expression.getValue();
                if (value instanceof String) {
                    String str = (String) value;
                    str = str.toLowerCase();
                    if (str.startsWith(HTTP_PREFIX)) {
                        holder.registerProblem(expression, AndroidCodeScannerBundle.message("java-t1.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                    }
                    if (str.startsWith(WS_PREFIX)) {
                        holder.registerProblem(expression, AndroidCodeScannerBundle.message("java-t2.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                    }
                }
            }
        };
    }


}
