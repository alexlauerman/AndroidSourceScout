package com.intellij.codeInspection;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethodCallExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

// Rule 15
public class WebViewJavaScriptInspection extends BaseJavaLocalInspectionTool {
    private static final Logger LOG = Logger.getInstance("#com.intellij.codeInspection.WebViewJavaScriptInspection");

    private static final Set<String> NAMES = new HashSet<>();
    static {
        NAMES.add("setJavaScriptEnabled");
        NAMES.add("addJavascriptInterface");
    }

    private static final String CONTAINING_CLASS = "android.webkit.WebView";


    @Nullable
    @Override
    public String getStaticDescription() {
        return AndroidCodeScannerBundle.message("java15.inspection-desc");
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            @Override
            public void visitMethodCallExpression(@NotNull PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                if (PsiJavaUtil.isMethod(expression, NAMES, CONTAINING_CLASS)) {
                    holder.registerProblem(expression, AndroidCodeScannerBundle.message("java15.error-msg"), ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                }
            }
        };
    }
}