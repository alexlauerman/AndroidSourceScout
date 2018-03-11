package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

//Rule 17
public class XXESAXInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.XXESAXInspection");

    public static final String NAME = "newSAXParser";
    public static final String CONTAINING_CLASS = "javax.xml.parsers.SAXParserFactory";

    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java17.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAME, CONTAINING_CLASS)) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java17.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }
}
